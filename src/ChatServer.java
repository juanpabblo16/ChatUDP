import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(5000); // Create a DatagramSocket on port 5000
        byte[] receiveBuffer = new byte[1024];
        byte[] sendBuffer = new byte[1024];

        System.out.println("Server is running...");
        boolean switchToClient = false; // Flag to switch to client chat mode
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            serverSocket.receive(receivePacket); // Receive message from client

            String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received from Client: " + clientMessage);

            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            String serverMessage;
            if (switchToClient) {
                serverMessage = "Server (to Client): " + clientMessage.toUpperCase(); // Process message
                sendBuffer = serverMessage.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                serverSocket.send(sendPacket); // Send response to client
            } else {
                serverMessage = "Server (to Server): " + clientMessage.toUpperCase(); // Process message
                sendBuffer = serverMessage.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                serverSocket.send(sendPacket); // Send response to server
            }

            if (clientMessage.equalsIgnoreCase("switch")) {
                switchToClient = !switchToClient; // Toggle switch flag to switch chat mode
            } else if (clientMessage.equalsIgnoreCase("quit or die")) {
                break; // Break the loop to close the server
            }
        }
        serverSocket.close();
    }
}
