import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);

        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        System.out.println("Server is running. Waiting for a client to connect...");
        int max = 2;
        int current = 0;
        while (true) {
            Socket clientSocket = serverSocket.accept();
            current++;
            System.out.println(current);
            // Send line busy message to the client if another client is already connected
            if (current > 1) {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("Line Buzy");
                out.close();
                clientSocket.close();
                current = 1;
                System.out.println("! Sever only accepts 1 client only !");
            }

            else {
                try {
                    System.out.println("Connected with client");

                    inputStreamReader = new InputStreamReader(socket.getInputStream());
                    outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                    bufferedReader = new BufferedReader(inputStreamReader);
                    bufferedWriter = new BufferedWriter(outputStreamWriter);

                    while (true) {
                        String msgFromClient = bufferedReader.readLine();
                        System.out.println("Client sent message: " + msgFromClient);
                        String manPage = getManPage(msgFromClient);
                        System.out.println(manPage);

                        if (msgFromClient.equalsIgnoreCase("bye"))
                            break;
                    }

                    serverSocket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getManPage(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("man", "-f", command);
            // ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command,
            // "/?");
            Process process = processBuilder.start();

            // Capture the output of the man command
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            // Wait for the process to complete and check the exit status
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return sb.toString();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
