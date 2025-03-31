package Files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class JSONTransmitter {
    public static void main(String[] args) {
        Card test = new Card(true, false, false);
        Gson gson = new Gson();

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("JSONGameStates/test.json"))) {
                writer.write(gson.toJson(test));
                writer.newLine();
                writer.write(gson.toJson(test));
            
            
            System.out.println("JSON file created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

