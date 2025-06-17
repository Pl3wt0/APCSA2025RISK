package Files.JSONStuff;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CopyOnWriteArrayList;

import Files.Player;
import tools.ColorAdapter;
import tools.InetAddressAdapter;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.lang.reflect.Type;

public class JSONTransmitter {
    private static final int PORT = 5001;
    private static ArrayList<InetAddress> ipAddresses = new ArrayList<>();
    private static final CopyOnWriteArrayList<ClientHandler> connectedClients = new CopyOnWriteArrayList<>();
    
    private static Gson gson = new GsonBuilder()
        .registerTypeAdapter(Color.class, new ColorAdapter())
        .registerTypeAdapter(InetAddress.class, new InetAddressAdapter())
        .registerTypeAdapter(BufferedImage.class, new tools.BufferedImageAdapter())
        .registerTypeAdapter(Cursor.class, new tools.CursorAdapter())
        .registerTypeAdapter(Robot.class, new tools.RobotTypeAdapter())
        .registerTypeAdapterFactory(new tools.JComponentTypeAdapterFactory())
        .registerTypeAdapterFactory(new tools.AWTTypeAdapterFactory())
        .excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE)
        .setPrettyPrinting()
        .create();

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
        ExecutorService executor = Executors.newFixedThreadPool(10); // Max 10 concurrent connections
        System.out.println("Host server listening on port " + PORT + "...");

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Create client handler and add to connected clients list
                ClientHandler clientHandler = new ClientHandler(clientSocket, true);
                connectedClients.add(clientHandler);
                
                // Submit client handling task to thread pool
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

        // Create a client handler for this peer connection
        ClientHandler clientHandler = new ClientHandler(socket, false);
        connectedClients.add(clientHandler);
        
        // Start handling in a separate thread
        new Thread(clientHandler).start();
    }

    /**
     * Send a JSON message to a specific peer
     */
    public static void sendJSONMessage(String hostIP, Object objectToSend) {
        try {
            Socket socket = new Socket(hostIP, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            String jsonMessage = gson.toJson(objectToSend);
            out.println("JSON_MESSAGE:" + jsonMessage);
            
            socket.close();
        } catch (IOException e) {
            System.out.println("JSON Message Not Sent: " + e.getMessage());
        }
    }

    

    /**
     * Broadcast a JSON object to all connected peers
     */
    public static void broadcastJSON(Object objectToSend) {
        String jsonMessage = gson.toJson(objectToSend);
        broadcastMessage("JSON_MESSAGE:" + jsonMessage, null);
    }

    /**
     * Send raw value directly to all connected peers
     */
    public static void sendValueToAll(Object value) {
        String valueString = value.toString();
        broadcastMessage("VALUE:" + valueString, null);
    }

    /**
     * Send JSON data directly to all connected peers
     */
    public static void sendJSONToAll(Object objectToSend) {
        String jsonMessage = gson.toJson(objectToSend);
        broadcastMessage("JSON_DATA:" + jsonMessage, null);
    }

    /**
     * Broadcast a regular text message to all connected peers
     */
    public static void broadcastTextMessage(String message) {
        broadcastMessage("TEXT:" + message, null);
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

    private static void sendJsonFile(Socket socket, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found!");
            return;
        }

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println("FILE_TRANSFER:" + file.getName());

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

    private static void receiveJsonFile(Socket socket, String fileName) throws IOException {
        System.out.println("Receiving file: " + fileName);

        File file = new File("Files/JSONStuff/JSONGameStates/received_" + fileName);
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

    public static void sendGameState(){
        for(ClientHandler client : connectedClients){
            if(client.isConnected()){
                try{
                    sendJsonFile(client.clientSocket,"Files/JSONStuff/JSONGameStates/GameState.json" );
                }catch(IOException e){
                    System.out.println(e);
                }
            }
            
        }
       
    }

    // Interface for handling received messages
    public interface MessageHandler {
        void onValueReceived(String value);
        void onJSONReceived(String jsonData);
        void onTextReceived(String text);
        void onFileReceived(String fileName);
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
                
                // Initial handshake - send initial game state if host
                if (isHost) {
                    String filePath = "Files/JSONStuff/JSONGameStates/GameState.json";
                    sendJsonFile(clientSocket, filePath);
                }
                
                String message;
                while ((message = in.readLine()) != null && connected) {
                    
                    if (message.startsWith("JSON_MESSAGE:")) {
                        // Extract JSON data and process locally, then forward
                        String jsonData = message.substring("JSON_MESSAGE:".length());
                        
                        // Process locally if handler exists
                        if (messageHandler != null) {
                            messageHandler.onJSONReceived(jsonData);
                        }
                        
                        // Forward to other clients
                        broadcastMessage("JSON_MESSAGE:" + jsonData, this);
                        
                    } else if (message.startsWith("JSON_DATA:")) {
                        // Process JSON data locally and forward
                        String jsonData = message.substring("JSON_DATA:".length());
                        
                        // Process locally if handler exists
                        if (messageHandler != null) {
                            messageHandler.onJSONReceived(jsonData);
                        }
                        
                        // Forward to other clients
                        broadcastMessage("JSON_DATA:" + jsonData, this);
                        
                    } else if (message.startsWith("VALUE:")) {
                        // Process value locally and forward
                        String value = message.substring("VALUE:".length());
                        
                        // Process locally if handler exists
                        if (messageHandler != null) {
                            messageHandler.onValueReceived(value);
                        }
                        
                        // Forward to other clients
                        broadcastMessage("VALUE:" + value, this);
                        
                    } else if (message.startsWith("TEXT:")) {
                        // Process text locally and forward
                        String text = message.substring("TEXT:".length());
                        
                        // Process locally if handler exists
                        if (messageHandler != null) {
                            messageHandler.onTextReceived(text);
                        }
                        
                        // Forward to other clients
                        broadcastMessage("TEXT:" + text, this);
                        
                    } else if (message.startsWith("FILE_TRANSFER:")) {
                        // Handle file transfer
                        String fileName = message.substring("FILE_TRANSFER:".length());
                        receiveJsonFile(clientSocket, fileName);
                        
                        // Process locally if handler exists
                        if (messageHandler != null) {
                            messageHandler.onFileReceived(fileName);
                        }
                        
                        // Notify other clients about file reception
                        broadcastMessage("FILE_RECEIVED:" + fileName, this);
                        
                    } else {
                        // Forward any other message directly
                        broadcastMessage(message, this);
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