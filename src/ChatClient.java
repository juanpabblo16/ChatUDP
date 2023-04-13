import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");
        int serverPort = 5000;

        BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));
        byte[] sendBuffer = new byte[1024];
        byte[] receiveBuffer = new byte[1024];

        System.out.println("Client is running...");
        boolean switchToServer = false; // Flag to switch to server chat mode
        while (true) {
            System.out.print("Enter a message (or 'q' to quit, 'switch' to switch chat mode): ");
            String clientMessage = fromUser.readLine();
            if (clientMessage.equalsIgnoreCase("q")) {
                break;
            } else if (clientMessage.equalsIgnoreCase("switch")) {
                switchToServer = !switchToServer; // Toggle switch flag to switch chat mode
                System.out.println("Switched to " + (switchToServer ? "server" : "client") + " chat mode.");
                continue; // Skip sending message and receive loop for now
            }

            sendBuffer = clientMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, serverPort);
            clientSocket.send(sendPacket); // Send message to server

            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(receivePacket); // Receive response from server

            String serverMessage;
            if (switchToServer) {
                serverMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received from Server (Server Chat Mode): " + serverMessage);
            } else {
                serverMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received from Server (Client Chat Mode): " + serverMessage);
            }
        }
        clientSocket.close();
    }
}
