package Files;

import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import tools.a;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

import Files.RenderingStuff.SceneObjects.*;
import Files.RenderingStuff.*;

import Files.JSONStuff.*;

public class GameRunner extends Thread {
    private SceneInfo sceneInfo;
    private BoardManager boardManager;

    public GameRunner(SceneInfo sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    public void run() {
        BoardManager.setUp(5);
        JSONManager.writeJSONGameState();
        JSONTransmitter.startConnection(null);

         for (Territory territory : BoardManager.getTerritories()) {
            GamePiece gamePiece = new GamePiece(0);
            InteractionHandler.addSceneObject(gamePiece);
            InteractionHandler.moveObject(gamePiece, territory.getPosition(), 0.1);
        }
 
        a.prl(InteractionHandler.askForTerritory("hi").getTerritoryName());
         
    }
}
