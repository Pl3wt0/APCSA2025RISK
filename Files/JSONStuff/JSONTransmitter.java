package Files.JSONStuff;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

import Files.Player;
import tools.ColorAdapter;
import tools.InetAddressAdapter;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.awt.image.BufferedImage;
import java.awt.*;

import java.lang.reflect.Type;


public class JSONTransmitter {
    private static final int PORT = 5000;
    private static ArrayList<InetAddress> ipAddresses = new ArrayList<>();
    private static Gson gson = new GsonBuilder()
    .registerTypeAdapter(Color.class, new ColorAdapter()) // Register custom adapter
    .registerTypeAdapter(InetAddress.class, new InetAddressAdapter())
    .registerTypeAdapter(BufferedImage.class, new tools.BufferedImageAdapter())
    .registerTypeAdapter(Cursor.class, new tools.CursorAdapter())
    .registerTypeAdapter(Robot.class, new tools.RobotTypeAdapter())
    .registerTypeAdapterFactory(new tools.JComponentTypeAdapterFactory())
    .registerTypeAdapterFactory(new tools.AWTTypeAdapterFactory())
    .excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE)
    .setPrettyPrinting()
    .create();


    public static void startConnection(String ip){
        if(ip == null){
            try{
                startHost();
            }catch(IOException e){}
        }else{
            try{
                startPeer(ip);
            } catch(IOException e){}
        }
    }


     private static void startHost() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Waiting for connection on port " + PORT + "...");

        Socket socket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String input = in.readLine(); // wait for the player number
        if (input != null) {
            InetAddress peerIp = socket.getInetAddress();

            ipAddresses.add(peerIp);

            System.out.println("Player connected from " + peerIp.getHostAddress());
        }
        writeIPJSON();
        
        



        handleConnection(socket, true); // Host sends JSON
        serverSocket.close();
        startHost();
    }

    private static void startPeer(String hostIp) throws IOException {
        Socket socket = new Socket(hostIp, PORT);
        System.out.println("Connected to host!");

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
        reInitializeIPs();
        String fileName = "Files\\JSONStuff\\JSONGameStates\\IPAddresses.json";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(gson.toJson(ipAddresses));
        System.out.println("JSON file created successfully!");
        
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(FileReader reader = new FileReader(fileName)){

        }catch(IOException e){

        }
    }

    private static void reInitializeIPs() {
        Gson gson = new GsonBuilder()
        .registerTypeAdapter(InetAddress.class, new InetAddressAdapter())
        .create();

        try (FileReader reader = new FileReader("Files/JSONStuff/JSONGameStates/IPAddresses.json")) {
            Type listType = new TypeToken<ArrayList<InetAddress>>() {}.getType();
            ArrayList<InetAddress> loadedIPs = gson.fromJson(reader, listType);

            ipAddresses.addAll(loadedIPs); // assuming ipAddresses is already initialized

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}

   

