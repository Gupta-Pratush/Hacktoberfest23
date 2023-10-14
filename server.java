package lab2;

import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) throws IOException {
        int port = 5555;
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("Server waiting for client...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected.");

        InputStream inStream = clientSocket.getInputStream();
        OutputStream outStream = clientSocket.getOutputStream();

        byte[] buffer = new byte[5000];
        int bytesRead;

        while ((bytesRead = inStream.read(buffer)) != -1) {

            FileOutputStream fileOutputStream = new FileOutputStream("received_data.txt", true);
            fileOutputStream.write(buffer, 0, bytesRead);
            fileOutputStream.close();

            outStream.write("ACK".getBytes());
        }

        // ProcessBuilder processBuilder = new ProcessBuilder(vimdiff received_data.txt
        // alice29.txt);

        System.out.println("File transfer complete.");
        serverSocket.close();
    }
}
