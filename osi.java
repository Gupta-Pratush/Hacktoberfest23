public class osi {
    public static void main(String[] args) {
        String message = "Hello, world!";
        applicationLayer(message);
    }

    // Application Layer
    static void applicationLayer(String message) {
        String header = "App Header: ";
        String messageWithHeader = header + message;
        System.out.println("Application Layer: " + messageWithHeader + "(" + message.length() + ")");
        presentationLayer(messageWithHeader);
    }

    // Presentation Layer
    static void presentationLayer(String message) {
        String header = "Pres Header: ";
        String messageWithHeader = header + message;
        System.out.println("Presentation Layer: " + messageWithHeader + "(" + message.length() + ")");
        sessionLayer(messageWithHeader);
    }

    // Session Layer
    static void sessionLayer(String message) {
        String header = "Session Header: ";
        String messageWithHeader = header + message;
        System.out.println("Session Layer: " + messageWithHeader + "(" + message.length() + ")");
        transportLayer(messageWithHeader);
    }

    // Transport Layer
    static void transportLayer(String message) {
        String header = "Transport Header: ";
        String messageWithHeader = header + message;
        System.out.println("Transport Layer: " + messageWithHeader + "(" + message.length() + ")");
        networkLayer(messageWithHeader);
    }

    // Network Layer
    static void networkLayer(String message) {
        String header = "Network Header: ";
        String messageWithHeader = header + message;
        System.out.println("Network Layer: " + messageWithHeader + "(" + message.length() + ")");
        dataLinkLayer(messageWithHeader);
    }

    // Data Link Layer
    static void dataLinkLayer(String message) {
        String header = "Data Link Header: ";
        String messageWithHeader = header + message;
        System.out.println("Data Link Layer: " + messageWithHeader + "(" + message.length() + ")");
        physicalLayer(messageWithHeader);
    }

    // Physical Layer
    static void physicalLayer(String message) {
        String header = "Physical Header: ";
        String messageWithHeader = header + message;
        System.out.println("Physical Layer: " + messageWithHeader + "(" + message.length() + ")");
    }

}