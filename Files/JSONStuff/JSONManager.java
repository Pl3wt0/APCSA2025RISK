package Files.JSONStuff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import java.net.InetAddress;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.image.BufferedImage;
import java.awt.*;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Files.BoardManager;
import Files.Continent;
import Files.GamePiece;
import Files.Player;
import Files.RenderingStuff.InteractionHandler;
import Files.RenderingStuff.SceneElement;
import tools.ColorAdapter;
import tools.InetAddressAdapter;

import java.lang.reflect.Type;

public class JSONManager {
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

     public static String writeJSONGameState(){
        String fileName = "Files/JSONStuff/JSONGameStates/GameState.json";

        Map<String, Object> gameState = new LinkedHashMap<>();
        gameState.put("Asia", BoardManager.getAsia().getTerritories());
        gameState.put("Africa", BoardManager.getAfrica().getTerritories());
        gameState.put("Australia", BoardManager.getAustralia().getTerritories());
        gameState.put("Europe", BoardManager.getEurope().getTerritories());
        gameState.put("NorthAmerica", BoardManager.getNorthAmerica().getTerritories());
        gameState.put("SouthAmerica", BoardManager.getSouthAmerica().getTerritories());
        gameState.put("Players", BoardManager.getPlayers());


        try{
            File file = new File(fileName);
            
            if (file.createNewFile()) {
                System.out.println("File created at: " + file.getAbsolutePath());
            } else {
                System.out.println("File already exists at: " + file.getAbsolutePath());
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(gson.toJson(gameState));
            System.out.println("JSON file created successfully!");

            writer.close();
        } catch (IOException e) {
            System.out.println("IAn is dumb");
            e.printStackTrace();
        }
            return fileName;
        }
    

    private static boolean validateGameStates(){
        String recivedFileExt = "JSONGameStates/recieved_GameState.json";
        String nativeFileExt = "JSONGameStates/GameState.json";
        boolean isValid = true;

        try(BufferedReader recievedFile = new BufferedReader(new FileReader(recivedFileExt)); BufferedReader nativeFile = new BufferedReader(new FileReader(nativeFileExt))  ) {
            String recivedLine = recievedFile.readLine();
            String nativeLine = nativeFile.readLine();
            while(recivedLine != null && nativeLine != null){
                if(!(recivedLine.equals(nativeLine))){
                    isValid = false;
                }
            }
                
        }catch(IOException e){
            System.out.println(e);
        }
        return isValid;
    }

    public static void syncGameStates(){
        if(validateGameStates()){
            return;
        }

        try(FileReader reader = new FileReader("JSONGameStates/recieved_GameState.json")){
            Type listType = new TypeToken<Map<String,Object>>() {}.getType();
            Map<String,Object> recievedState = gson.fromJson(reader, listType);

            BoardManager.setAsia((Continent)recievedState.get("Asia"));
            BoardManager.setAfrica((Continent)recievedState.get("Africa"));
            BoardManager.setAustralia((Continent)recievedState.get("Australia"));
            BoardManager.setEurope((Continent)recievedState.get("Europe"));
            BoardManager.setNorthAmerica((Continent)recievedState.get("NorthAmerica"));
            BoardManager.setSouthAmerica((Continent)recievedState.get("SouthAmerica"));
            BoardManager.setPlayers((ArrayList<Player>)recievedState.get("Players"));

            for (SceneElement sceneElement : InteractionHandler.getSceneInfo().getSceneObjects(GamePiece.class)) {
                InteractionHandler.getSceneInfo().getSceneObjects().remove(sceneElement);
            }
            for (Files.Territory t : BoardManager.getTerritories()) {
                InteractionHandler.getSceneInfo().getSceneObjects().addAll(t.getPieces());
            }

        }catch(IOException e){

        }

    }
    
}

