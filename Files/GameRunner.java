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
import Files.RenderingStuff.GUIElements.InputButton;
import Files.JSONStuff.*;

public class GameRunner extends Thread {
    private SceneInfo sceneInfo;

    public GameRunner(SceneInfo sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    public void run() {
        InteractionHandler.setSceneInfo(sceneInfo, sceneInfo.getPanelInfo());
        JSONTransmitter.startConnection(null);
        String ip = InteractionHandler.getPlayerConnection();
        if (ip == null) {
            //host
        } else {
            //peer
        }

        BoardManager.setUp(5);

        


        
    }

}
