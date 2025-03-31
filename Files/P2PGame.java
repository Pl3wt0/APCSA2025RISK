package Files;
import java.io.*;
import java.net.*;

public class P2PGame {
    public static void main(String[] args) throws IOException {
        if (true) {
            startServer();
        } else {
            connectToPeer("localhost", 5000);
        }
    }

    public static void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(5001);
        System.out.println("Waiting for a connection...");
        Socket socket = serverSocket.accept();
        System.out.println("Player connected!");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println("Welcome to the game!");

        String msg;
        while ((msg = in.readLine()) != null) {
            System.out.println("Opponent: " + msg);
            out.println("Echo: " + msg);
        }

        socket.close();
        serverSocket.close();
    }

    public static void connectToPeer(String ip, int port) throws IOException {
        Socket socket = new Socket(ip, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Connected to peer.");
        System.out.println("Server says: " + in.readLine());

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String msg;
        while ((msg = userInput.readLine()) != null) {
            out.println(msg);
            System.out.println("Server: " + in.readLine());
        }

        socket.close();
    }
}
