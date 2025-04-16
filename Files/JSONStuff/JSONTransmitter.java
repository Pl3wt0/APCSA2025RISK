package Files.JSONStuff;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

import Files.Player;
import tools.ColorAdapter;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.Color;


public class JSONTransmitter {
    private static final int PORT = 5000;
    private static Map<Integer,InetAddress> ipAdresses = new LinkedHashMap<>();
    private static Gson gson = new GsonBuilder()
    .registerTypeAdapter(Color.class, new ColorAdapter()) // Register custom adapter
    .setPrettyPrinting()
    .create();

    public static void main(String[] args) {

       /*try{
            if(hostIP.equals(null)){
                startHost();
            }else{
                startPeer(hostIP);
            }
        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        */
        try{
            startHost();
        }catch(IOException e){};

        
        
    }

     private static void startHost() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Waiting for connection on port " + PORT + "...");

        Socket socket = serverSocket.accept();
        ipAdresses.put((Integer)0,socket.getInetAddress());
        writeIPJSON();
        
        System.out.println("Peer connected!");

        handleConnection(socket, true); // Host sends JSON
        serverSocket.close();
        startHost();
    }

    private static void startPeer(String hostIp) throws IOException {
        Socket socket = new Socket(hostIp, PORT);
        System.out.println("Connected to host!");
        System.out.println(socket.getInetAddress());

        handleConnection(socket, false); // Peer receives JSON
    }
    
    private static void handleConnection(Socket socket, boolean isHost) throws IOException {   
        if (isHost) {
            // Host: Send JSON file
            String filePath = "Files\\JSONStuff\\JSONGameStates\\GameState.json";
            sendJsonFile(socket, filePath);
        } else {
            // Peer: Receive JSON file
            receiveJsonFile(socket);
        }
        socket.close();
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
       

        // Receive file name
        String fileName = "GameState.json";
        System.out.println("Receiving file: " + fileName);

        File file = new File("Files\\JSONStuff\\JSONGameStates\\received_" + fileName);
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

    private static void writeIPJSON(){
    String fileName = "Files\\JSONStuff\\JSONGameStates\\IPAdresses.json";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        writer.write(gson.toJson(ipAdresses));
    System.out.println("JSON file created successfully!");
        
    } catch (IOException e) {
        e.printStackTrace();
    }

    try(FileReader reader = new FileReader(fileName)){

    }catch(IOException e){

    }
    }
}

   

