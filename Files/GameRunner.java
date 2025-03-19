package Files;

import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import tools.a;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

import Files.RenderingStuff.InteractionHandler;
import Files.RenderingStuff.SceneObjects.*;

public class GameRunner extends Thread {
    public void run() {
        //This is basically the main method for the game
        //Call InteractionHandler static methods to interact w/ engine for animations and player decisions




        InteractionHandler.sleep(5000);
        InteractionHandler.doSomething();
    }
}
