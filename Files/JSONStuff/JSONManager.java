package Files.JSONStuff;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.Color;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Files.BoardManager;
import Files.Player;
import tools.ColorAdapter;

public class JSONManager {
    private static Gson gson = new GsonBuilder()
    .registerTypeAdapter(Color.class, new ColorAdapter()) // Register custom adapter
    .setPrettyPrinting()
    .create(); 

     public static String writeJSONGameState(ArrayList<Player> players){
        String fileName = "JSONGameStates/GameState.json";

        Map<String, Object> gameState = new LinkedHashMap<>();
        gameState.put("Asia", BoardManager.getAsia().getTerritories());
        gameState.put("Africa", BoardManager.getAfrica().getTerritories());
        gameState.put("Australia", BoardManager.getAustralia().getTerritories());
        gameState.put("Europe", BoardManager.getEurope().getTerritories());
        gameState.put("NorthAmerica", BoardManager.getNorthAmerica().getTerritories());
        gameState.put("SouthAmerica", BoardManager.getSouthAmerica().getTerritories());
        for(int i=0;i<players.size();i++){
            gameState.put("Player", players);
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(gson.toJson(gameState));
        System.out.println("JSON file created successfully!");
        
    } catch (IOException e) {
        e.printStackTrace();
    }
    return fileName;
    }
    

    public static boolean validateGameStates(boolean isSender){
        String fileName;
        if(isSender){
            fileName = "Files\\JSONStuff\\JSONGameStates\\GameState.json";
        } else{
            fileName = "Files\\JSONStuff\\JSONGameStates\\recieved_GameState.json";
        }
    }
}

