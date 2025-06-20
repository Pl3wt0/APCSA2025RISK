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
        broadcastMessage("TEXT:" + message, null);
    }

    /**
     * Send GameState.json file to all connected clients
     */
    public static void sendGameState() {
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
                    out.println(message);
                    if (out.checkError()) {
                        disconnect();
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

            out.println("GAMESTATE_TRANSFER:" + file.getName());

            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) > 0) {
                bos.write(buffer, 0, bytesRead);
            }

            bos.flush();
            fileInputStream.close();
            System.out.println("GameState.json sent successfully!");
        }
        
        private void receiveGameStateFile(String fileName) throws IOException {
            System.out.println("Receiving GameState file: " + fileName);

            File file = new File("Files/JSONStuff/JSONGameStates/received_" + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(clientSocket.getInputStream());

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) > 0) {
                fos.write(buffer, 0, bytesRead);
            }

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
        }
        
        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                // Initial handshake - send GameState.json if host
                if (isHost) {
                    sendGameStateFile();
                }
                
                String message;
                while ((message = in.readLine()) != null && connected) {
                    
                    if (message.startsWith("TEXT:")) {
                        // Process text locally and forward
                        String text = message.substring("TEXT:".length());
                        
                        if (messageHandler != null) {
                            messageHandler.onTextReceived(text);
                        }
                        
                        broadcastMessage("TEXT:" + text, this);
                        
                    } else if (message.startsWith("GAMESTATE_TRANSFER:")) {
                        // Handle GameState file transfer
                        String fileName = message.substring("GAMESTATE_TRANSFER:".length());
                        receiveGameStateFile(fileName);
                        
                        if (messageHandler != null) {
                            messageHandler.onGameStateReceived(fileName);
                        }
                        
                        // Notify other clients about GameState reception
                        broadcastMessage("GAMESTATE_RECEIVED:" + fileName, this);
                    }
                }
                
            } catch (IOException e) {
                System.err.println("Error handling client " + clientSocket.getInetAddress() + ": " + e.getMessage());
            } finally {
                disconnect();
            }
        }
    }
}