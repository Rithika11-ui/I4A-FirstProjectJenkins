import java.net.*;
import java.io.*;

public class DateClient {
    public static void main(String[] args) {
        try {
            // Connect to server on localhost, port 6013
            Socket socket = new Socket("127.0.0.1", 6013);

            // Read response from server
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            String serverResponse = in.readLine();
            System.out.println("Server says: " + serverResponse);

            // Close connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
