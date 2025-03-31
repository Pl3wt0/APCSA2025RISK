package Files;

import com.google.gson.Gson;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class P2PGameTCP {
    private static final int PORT = 5000;
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter mode (host/peer): ");
        String mode = scanner.nextLine();

        try {
            if (mode.equalsIgnoreCase("host")) {
                startHost();
            } else {
                System.out.print("Enter host IP: ");
                String hostIp = scanner.nextLine();
                startPeer(hostIp);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static void startHost() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Waiting for connection on port " + PORT + "...");

        Socket socket = serverSocket.accept();
        System.out.println("Peer connected!");

        handleConnection(socket, true); // Host sends JSON
        serverSocket.close();
    }

    public static void startPeer(String hostIp) throws IOException {
        Socket socket = new Socket(hostIp, PORT);
        System.out.println("Connected to host!");

        handleConnection(socket, false); // Peer receives JSON
    }

    public static void handleConnection(Socket socket, boolean isHost) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        if (isHost) {
            // Host: Send JSON file
            System.out.print("Enter JSON file path to send: ");
            String filePath = scanner.nextLine();
            sendJsonFile(socket, filePath);
        } else {
            // Peer: Receive JSON file
            receiveJsonFile(socket);
        }

        // Start a separate thread for messages
        Thread readThread = new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("\nPeer: " + message);
                    System.out.print("You: ");
                }
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        });

        readThread.start();

        while (true) {
            System.out.print("You: ");
            String message = scanner.nextLine();
            out.println(message);
            if (message.equalsIgnoreCase("exit")) {
                break;
            }
        }

        socket.close();
        scanner.close();
    }

    private static void sendJsonFile(Socket socket, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found!");
            return;
        }

        // Send file metadata first
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(file.getName()); // Send filename

        // Send JSON file
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) > 0) {
            bos.write(buffer, 0, bytesRead);
        }

        bos.flush();
        fileInputStream.close();
        System.out.println("JSON file sent successfully!");
    }

    private static void receiveJsonFile(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Receive file name
        String fileName = in.readLine();
        System.out.println("Receiving file: " + fileName);

        File file = new File("received_" + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = bis.read(buffer)) > 0) {
            fos.write(buffer, 0, bytesRead);
        }

        fos.close();
        System.out.println("File received and saved as: " + file.getAbsolutePath());
    }
}
