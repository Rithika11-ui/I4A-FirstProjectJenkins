package com.example.demo.service;

import com.example.demo.model.Profile;
import com.example.demo.model.Template;
import com.example.demo.util.BarcodeGenerator;
import com.itextpdf.html2pdf.HtmlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final TemplateEngine templateEngine;
    private final PhotoService photoService;

    /**
     * Renders the ID card HTML template for a profile.
     */
    public String renderHtml(Profile profile) {
        Context context = new Context();

        Template template = profile.getTemplate();
        if (template == null) {
            template = Template.builder()
                    .name("Default")
                    .code("DEFAULT")
                    .organizationName("ID CARD MANAGER")
                    .layout("VERTICAL")
                    .primaryColor("#1d4ed8")
                    .secondaryColor("#e0e7ff")
                    .textColor("#111827")
                    .tagline("Verification System")
                    .build();
        }

        context.setVariable("profile", profile);
        context.setVariable("template", template);

        // Generate QR code
        String verificationUrl = "http://localhost:8080/api/profiles/" + (profile.getId() != null ? profile.getId() : "preview");
        String qrCodeBase64 = BarcodeGenerator.generateQRCodeBase64(verificationUrl, 200, 200);
        context.setVariable("qrCodeBase64", qrCodeBase64);

        // Generate Barcode
        String barcodeText = profile.getRegistrationNumber() != null ? profile.getRegistrationNumber() : "000000000000";
        String barcodeBase64 = BarcodeGenerator.generateBarcodeBase64(
                barcodeText,
                profile.getBarcodeType() != null ? profile.getBarcodeType() : com.example.demo.model.BarcodeType.CODE_128,
                300, 70
        );
        context.setVariable("barcodeBase64", barcodeBase64);

        // Photo loading
        String photoBase64 = null;
        if (profile.hasPhoto()) {
            try {
                byte[] photoBytes = photoService.readPhoto(profile.getPhotoFileName());
                photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
            } catch (IOException e) {
                // Keep null if reading fails, fallback handled in UI
            }
        }
        context.setVariable("photoBase64", photoBase64);

        return templateEngine.process("idcard", context);
    }

    /**
     * Converts the rendered HTML of a profile into a PDF byte array.
     */
    public byte[] generatePdf(Profile profile) {
        String html = renderHtml(profile);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(html, out);
        return out.toByteArray();
    }

    /**
     * Converts a batch of profiles into a single PDF byte array with page breaks.
     */
    public byte[] generateBatchPdf(List<Profile> profiles) {
        if (profiles == null || profiles.isEmpty()) {
            return new byte[0];
        }

        StringBuilder combinedHtml = new StringBuilder();
        
        // Extract style block from the first rendered profile to preserve card styling
        String firstRender = renderHtml(profiles.get(0));
        String styleBlock = "";
        int styleStart = firstRender.indexOf("<style>");
        int styleEnd = firstRender.indexOf("</style>");
        if (styleStart != -1 && styleEnd != -1) {
            styleBlock = firstRender.substring(styleStart, styleEnd + 8);
        }

        combinedHtml.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\">")
                .append(styleBlock)
                .append("<style>")
                .append("body { margin: 0; padding: 0; background: none; } ")
                .append(".card-wrapper { overflow: hidden; page-break-inside: avoid; } ")
                .append(".page-break-after { page-break-after: always; } ")
                .append("</style></head><body>");

        for (int i = 0; i < profiles.size(); i++) {
            Profile profile = profiles.get(i);
            String cardHtml = renderHtml(profile);
            
            // Extract only the inner content of <body> to avoid nested html/body structures in iText
            int bodyStart = cardHtml.indexOf("<body>");
            int bodyEnd = cardHtml.indexOf("</body>");
            String bodyContent = cardHtml;
            if (bodyStart != -1 && bodyEnd != -1) {
                bodyContent = cardHtml.substring(bodyStart + 6, bodyEnd);
            }
            
            String extraClass = (i < profiles.size() - 1) ? " page-break-after" : "";
            combinedHtml.append("<div class=\"card-wrapper").append(extraClass).append("\">")
                    .append(bodyContent)
                    .append("</div>");
        }
        combinedHtml.append("</body></html>");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(combinedHtml.toString(), out);
        return out.toByteArray();
    }
}
