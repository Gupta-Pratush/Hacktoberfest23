package lab2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {
        int port = 5555;
        Socket clientSocket = new Socket("localhost", port);

        InputStream inStream = new FileInputStream("alice29.txt");
        OutputStream outStream = clientSocket.getOutputStream();

        byte[] buffer = new byte[5000];
        int bytesRead;
        Scanner scanner = new Scanner(System.in);

        while ((bytesRead = inStream.read(buffer)) != -1) {

            System.out.print("Do you Want to transfer 5000 bytes to server(yes/no)");
            String inputString = scanner.nextLine();
            if (inputString.compareTo("yes") == 0) {

                outStream.write(buffer, 0, bytesRead);

                byte[] ackBuffer = new byte[3];
                InputStream ackInputStream = clientSocket.getInputStream();
                ackInputStream.read(ackBuffer);

                String ack = new String(ackBuffer);
                if (ack.equals("ACK")) {

                    System.out.println("Received ACK. Sending next data chunk.");
                }

            }

            else {
                break;
            }
        }

        System.out.println("File transfer complete.");
        clientSocket.close();
    }
}
