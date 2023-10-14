
// package lab3;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client1 {
    private static final String SERVER_IP = "127.0.0.2";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your name: ");
            String clientName = scanner.nextLine();
            out.println(clientName);

            Thread receiveThread = new Thread(new ReceiveHandler(in));
            receiveThread.start();

            while (true) {
                System.out.print("Enter recipient's name (or 'all' to broadcast): ");
                String recipient = scanner.nextLine();

                System.out.print("Enter message: ");
                String message = scanner.nextLine();

                out.println(recipient + ":" + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ReceiveHandler implements Runnable {
    private BufferedReader in;

    public ReceiveHandler(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
