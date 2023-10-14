import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost";
        int serverPort = 12345;

        Socket socket = new Socket(serverAddress, serverPort);

        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        // Read the "line busy" message from the server
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = in.readLine();

        if (response.equals("Line Buzy")) {
            System.out.println("Server says: " + response);

            in.close();
            socket.close();
        }

        else {

            try {
                System.out.println("Connected with server");
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                Scanner scanner = new Scanner(System.in);

                while (true) {
                    System.out.print("Please enter the message to the server: ");

                    String msgTosend = scanner.nextLine();

                    bufferedWriter.write(msgTosend);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    System.out.println("Server: " + bufferedReader.readLine());

                    if (msgTosend.equalsIgnoreCase("bye"))
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
