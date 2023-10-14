package lab3;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static Map<String, PrintWriter> clients = new HashMap<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running and listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static synchronized void addClient(String clientName, PrintWriter out) {
        clients.put(clientName, out);
    }

    static synchronized void removeClient(String clientName) {
        clients.remove(clientName);
    }

    static synchronized void broadcastMessage(String sender, String message) {
        for (PrintWriter out : clients.values()) {
            out.println(sender + ": " + message);
        }
    }

    static synchronized void directMessage(String sender, String recipient, String message) {
        PrintWriter out = clients.get(recipient);
        if (out != null) {
            out.println("\n" + sender + " (private): " + message);
        }
    }

    static synchronized void discardedMessage(String sender, String recipient, String message) {
        for (String i : clients.keySet()) {
            if (i != recipient) {
                if (i != sender) {
                    PrintWriter out = clients.get(i);
                    if (out != null) {
                        out.println("\n(discarded message from " + sender + "): " + message);
                    }
                }
            }
        }

    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Get client's name
            clientName = in.readLine();
            Server.addClient(clientName, out);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(clientName + ": " + message);

                // Check if the message is intended for a specific client
                if (message.startsWith("all:")) {
                    Server.broadcastMessage(clientName, message.substring(4));
                } else {
                    String[] parts = message.split(":", 2);
                    if (parts.length == 2) {
                        String recipient = parts[0];
                        String content = parts[1];
                        Server.directMessage(clientName, recipient, content);
                        Server.discardedMessage(clientName, recipient, content);
                    }
                }
            }

            Server.removeClient(clientName);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
