package Files.JSONStuff;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MessageTransmitter {
    static final int port = 5000;

    public static void sendMessage(String hostIP){
        try{
            Socket socket = new Socket(hostIP,port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            
            out.println("Test");


            socket.close();
        }catch(IOException e){
            System.out.println("Message Not Sent");
        }
    }

    public static void startMultiThreadedServer(int numOfPlayers) {
        ServerSocket serverSocket = null;
        ExecutorService executor = Executors.newFixedThreadPool(numOfPlayers);
        
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Multithreaded server listening on port " + port);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                
                // Submit client handling task to thread pool
                executor.submit(new ClientHandler(clientSocket));
            }
            
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing server socket: " + e.getMessage());
                }
            }
            executor.shutdown();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        @Override
        public void run() {
            BufferedReader in = null;
            PrintWriter out = null;
            
            try {
                System.out.println("Handling client: " + clientSocket.getInetAddress());
                
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received from " + clientSocket.getInetAddress() + ": " + message);
                    
                    // Send acknowledgment back to client
                    out.println("ACK: " + message);
                    
                    // Exit condition
                    if ("QUIT".equalsIgnoreCase(message.trim())) {
                        System.out.println("Client " + clientSocket.getInetAddress() + " requested to quit");
                        break;
                    }
                }
                
            } catch (IOException e) {
                System.err.println("Error handling client " + clientSocket.getInetAddress() + ": " + e.getMessage());
            } finally {
                try {
                    if (in != null) in.close();
                    if (out != null) out.close();
                    if (clientSocket != null) clientSocket.close();
                    System.out.println("Client disconnected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    System.err.println("Error closing client resources: " + e.getMessage());
                }
            }
        }
    }

}

