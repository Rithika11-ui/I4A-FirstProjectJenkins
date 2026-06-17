package com.example.demo.util;

import com.example.demo.model.BarcodeType;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.EAN13Writer;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class BarcodeGenerator {

    /**
     * Generates a QR Code as a Base64-encoded PNG image string.
     */
    public static String generateQRCodeBase64(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(pngData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate QR Code: " + e.getMessage(), e);
        }
    }

    /**
     * Generates a Barcode (Code-128 or EAN-13) as a Base64-encoded PNG image string.
     */
    public static String generateBarcodeBase64(String text, BarcodeType type, int width, int height) {
        try {
            BitMatrix bitMatrix;
            if (type == BarcodeType.EAN_13) {
                String eanDigits = convertToEan13Digits(text);
                EAN13Writer ean13Writer = new EAN13Writer();
                bitMatrix = ean13Writer.encode(eanDigits, BarcodeFormat.EAN_13, width, height);
            } else {
                Code128Writer code128Writer = new Code128Writer();
                bitMatrix = code128Writer.encode(text, BarcodeFormat.CODE_128, width, height);
            }

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(pngData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Barcode (" + type + "): " + e.getMessage(), e);
        }
    }

    /**
     * Converts any alphanumeric string into a valid 13-digit EAN-13 number (12 digits + 1 checksum).
     * Extracts digits, pads/truncates to 12 digits, and computes the 13th checksum digit.
     */
    public static String convertToEan13Digits(String text) {
        if (text == null) {
            text = "";
        }
        
        // Extract all numeric digits
        StringBuilder digits = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                digits.append(c);
            }
        }

        // If not enough digits, pad with zero or fallback to a default numeric sequence
        if (digits.length() == 0) {
            digits.append("000000000000");
        } else if (digits.length() < 12) {
            while (digits.length() < 12) {
                digits.insert(0, '0');
            }
        } else if (digits.length() > 12) {
            digits.setLength(12);
        }

        String first12 = digits.toString();
        int checksum = calculateEan13Checksum(first12);
        return first12 + checksum;
    }

    private static int calculateEan13Checksum(String code) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(code.charAt(i));
            if (i % 2 == 0) {
                sum += digit; // Odd position (0-indexed 0, 2, 4...)
            } else {
                sum += digit * 3; // Even position (0-indexed 1, 3, 5...)
            }
        }
        int mod = sum % 10;
        return (mod == 0) ? 0 : 10 - mod;
    }
}
