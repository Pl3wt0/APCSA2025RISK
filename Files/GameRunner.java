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

public class GameRunner extends Thread {
    private SceneInfo sceneInfo;
    public GameRunner(SceneInfo sceneInfo) {
        this.sceneInfo = sceneInfo;
    }
    public void run() {
        //This is basically the main method for the game
        //Call InteractionHandler static methods to interact w/ engine for animations and player decisions




        InteractionHandler.sleep(5000);
        double[] point = {100, 100, 0};
        InteractionHandler.moveObject((Cube)(sceneInfo.getSceneObjects().get(2)), point, 10);
    }
}
