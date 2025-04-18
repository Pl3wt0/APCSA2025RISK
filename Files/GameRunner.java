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

        ArrayList<String> answers = new ArrayList<String>();
        answers.add("answer 1");
        answers.add("answer 2");
        answers.add("answer 3");
        answers.add("answer 4");

        a.prl(InteractionHandler.askPlayer("question", answers));
        InteractionHandler.displayMessage("hi", 5.0);

    }
}
