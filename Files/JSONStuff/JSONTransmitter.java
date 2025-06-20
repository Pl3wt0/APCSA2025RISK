package Files.JSONStuff;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CopyOnWriteArrayList;

public class JSONTransmitter {
    private static final int PORT = 5001;
    private static final CopyOnWriteArrayList<ClientHandler> connectedClients = new CopyOnWriteArrayList<>();
    
    public static void startConnection(String ip) {
        if (ip == null) {
            try {
                startHost();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                startPeer(ip);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Start the host with multithreaded server capability
     */
    private static void startHost() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        System.out.println("Host server listening on port " + PORT + "...");

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, true);
                connectedClients.add(clientHandler);
                
                executor.submit(clientHandler);
            }
        } finally {
            serverSocket.close();
            executor.shutdown();
        }
    }

    private static void startPeer(String hostIp) throws IOException {
        Socket socket = new Socket(hostIp, PORT);
        System.out.println("Connected to host!");

        ClientHandler clientHandler = new ClientHandler(socket, false);
        connectedClients.add(clientHandler);
        
        new Thread(clientHandler).start();
    }

    /**
     * Broadcast a text message to all connected peers
     */
    public static void broadcastTextMessage(String message) {
        System.out.println("Broadcasting text message: " + message);
        broadcastMessage("TEXT:" + message, null);
    }

    /**
     * Send GameState.json file to all connected clients
     */
    public static void sendGameState() {
        System.out.println("Sending GameState to all clients...");
        for (ClientHandler client : connectedClients) {
            if (client.isConnected()) {
                try {
                    client.sendGameStateFile();
                } catch (IOException e) {
                    System.out.println("Error sending game state: " + e);
                }
            }
        }
    }

    /**
     * Broadcasts a message to all connected clients except the sender
     */
    private static void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : connectedClients) {
            if (client != sender && client.isConnected()) {
                client.sendMessage(message);
            }
        }
    }

    // Interface for handling received messages
    public interface MessageHandler {
        void onTextReceived(String text);
        void onGameStateReceived(String fileName);
    }
    
    private static MessageHandler messageHandler;
    
    /**
     * Set the message handler to process received messages
     */
    public static void setMessageHandler(MessageHandler handler) {
        messageHandler = handler;
    }

    /**
     * Client handler for processing individual connections
     */
    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private boolean connected = true;
        private boolean isHost;
        
        public ClientHandler(Socket socket, boolean isHost) {
            this.clientSocket = socket;
            this.isHost = isHost;
        }
        
        public boolean isConnected() {
            return connected && !clientSocket.isClosed();
        }
        
        public void sendMessage(String message) {
            if (out != null && isConnected()) {
                try {
                    synchronized(out) {  // Synchronize access to output stream
                        out.println(message);
                        out.flush();
                        if (out.checkError()) {
                            disconnect();
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error sending message to client: " + e.getMessage());
                    disconnect();
                }
            }
        }
        
        public void sendGameStateFile() throws IOException {
            File file = new File("Files/JSONStuff/JSONGameStates/GameState.json");
            if (!file.exists()) {
                System.out.println("GameState.json file not found!");
                return;
            }

            synchronized(out) {  // Synchronize to prevent text messages from interfering
                // Send file transfer header
                out.println("GAMESTATE_TRANSFER:" + file.getName() + ":" + file.length());
                out.flush();

                // Read file content and send as Base64 to avoid binary data issues
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] fileBytes = new byte[(int) file.length()];
                fileInputStream.read(fileBytes);
                fileInputStream.close();

                // Convert to Base64 and send line by line
                String base64Content = java.util.Base64.getEncoder().encodeToString(fileBytes);
                out.println("FILE_DATA:" + base64Content);
                out.println("FILE_END");
                out.flush();
            }

            System.out.println("GameState.json sent successfully to " + clientSocket.getInetAddress());
        }
        
        private void receiveGameStateFile(String fileName, long fileSize) throws IOException {
            System.out.println("Receiving GameState file: " + fileName + " (size: " + fileSize + " bytes)");

            // Read the Base64 encoded file data
            String dataLine = in.readLine();
            if (!dataLine.startsWith("FILE_DATA:")) {
                throw new IOException("Expected FILE_DATA but got: " + dataLine);
            }
            
            String base64Content = dataLine.substring("FILE_DATA:".length());
            
            // Read FILE_END marker
            String endMarker = in.readLine();
            if (!"FILE_END".equals(endMarker)) {
                throw new IOException("Expected FILE_END but got: " + endMarker);
            }

            // Decode and save file
           
          
            byte[] fileBytes = java.util.Base64.getDecoder().decode(base64Content);
            File file = new File("Files/JSONStuff/JSONGameStates/received_" + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(fileBytes);
            fos.close();
            System.out.println("GameState file received and saved as: " + file.getAbsolutePath());
           
            

            
        }
        
        private void disconnect() {
            connected = false;
            connectedClients.remove(this);
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client resources: " + e.getMessage());
            }
            System.out.println("Client disconnected: " + clientSocket.getInetAddress());
        }
        
        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                // Initial handshake - send GameState.json if host
                if (isHost) {
                    Thread.sleep(100); // Small delay to ensure connection is stable
                    sendGameStateFile();
                }
                
                String message;
                while ((message = in.readLine()) != null && connected) {
                    System.out.println("Received message: " + message);
                    
                    if (message.startsWith("TEXT:")) {
                        // Process text locally and forward
                        String text = message.substring("TEXT:".length());
                        System.out.println("Processing text message: " + text);
                        
                        if (messageHandler != null) {
                            messageHandler.onTextReceived(text);
                        }
                        
                        broadcastMessage("TEXT:" + text, this);
                        
                    } else if (message.startsWith("GAMESTATE_TRANSFER:")) {
                        // Handle GameState file transfer
                        String[] parts = message.substring("GAMESTATE_TRANSFER:".length()).split(":");
                        String fileName = parts[0];
                        long fileSize = Long.parseLong(parts[1]);
                        
                        System.out.println("Starting GameState file transfer: " + fileName);
                        receiveGameStateFile(fileName, fileSize);
                        
                        if (messageHandler != null) {
                            messageHandler.onGameStateReceived(fileName);
                        }
                        
                    } else {
                        System.out.println("Unknown message type: " + message);
                    }
                }
                
            } catch (IOException e) {
                System.err.println("Error handling client " + clientSocket.getInetAddress() + ": " + e.getMessage());
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
            } finally {
                disconnect();
            }
        }
    }
}