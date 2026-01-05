import java.net.*;
import java.io.*;
import java.util.Date;

public class DateServer {
    public static void main(String[] args) {
        try {
            // Create server socket on port 6013
            ServerSocket serverSocket = new ServerSocket(6013);
            System.out.println("DateServer running on port 6013...");

            while (true) {
                // Accept client connection
                Socket client = serverSocket.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                // Send current date to client
                out.println(new Date().toString());

                // Close connection
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
