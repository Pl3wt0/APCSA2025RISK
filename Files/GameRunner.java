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
        Player player1 = new Player(1));
        Player player2 = new Player(2));
        player1.setName("Player 1");
        player2.setName("Player 2");
        BoardManager board = new BoardManager(2);
        board.initalizeTerritoryOwners();
        board.determineTroopAmnt(player1);
        board.determineTroopAmnt(player2);
        //This is where you would call the methods to start the game
        //For example, you could call a method to start the game loop
        //and handle player turns, etc.
        //For now, we'll just simulate a turn for each player
        //Simulate player 1's turn
        InteractionHandler.displayMessage(player1.getName() + "'s turn!");


        InteractionHandler.sleep(5000);
    }
}
