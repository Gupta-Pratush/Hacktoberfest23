public class osiv {
    public static void main(String[] args) {
        // Input application message
        String applicationMessage = "Hello";
        System.out.println(applicationMessage.length());

        // Start the message flow from the top layer
        applicationLayer(applicationMessage, applicationMessage.length());
    }

    public static void applicationLayer(String message, int size) {
        String header = "APP_HEADER - ";
        processLayer(header, message, size, "Application Layer");
    }

    public static void transportLayer(String message, int size) {
        String header = "TRANSPORT_HEADER - ";
        processLayer(header, message, size, "Transport Layer");
    }

    public static void networkLayer(String message, int size) {
        String header = "NETWORK_HEADER - ";
        processLayer(header, message, size, "Network Layer");
    }

    public static void dataLinkLayer(String message, int size) {
        String header = "DATALINK_HEADER - ";
        processLayer(header, message, size, "Data Link Layer");
    }

    public static void physicalLayer(String message, int size) {
        String header = "PHYSICAL_HEADER - ";
        processLayer(header, message, size, "Physical Layer");
    }

    public static void processLayer(String header, String message, int size, String layerName) {
        if (header.length() + size - 5 <= 64) {
            String newMessage = header + message;
            System.out.println(layerName + ": " + newMessage + "(" + (newMessage.length() - 5) + ")");
            int newSize = newMessage.length();

            if (!layerName.equals("Physical Layer")) {
                invokeLowerLayer(layerName, newMessage, newSize);
            }
        } else {
            System.out.println(layerName + ": Error - Header too large for message.");
        }
    }

    public static void invokeLowerLayer(String currentLayer, String message, int size) {
        switch (currentLayer) {
            case "Application Layer":
                transportLayer(message, size);
                break;
            case "Transport Layer":
                networkLayer(message, size);
                break;
            case "Network Layer":
                dataLinkLayer(message, size);
                break;
            case "Data Link Layer":
                physicalLayer(message, size);
                break;
            default:
                System.out.println("Invalid layer name.");
                break;
        }
    }
}
