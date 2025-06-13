package Files;

import Files.JSONStuff.*;
import Files.RenderingStuff.*;
import java.util.*;

public class GameRunner extends Thread {
    private SceneInfo sceneInfo;

    public GameRunner(SceneInfo sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    public void run() {
        InteractionHandler.setSceneInfo(sceneInfo, sceneInfo.getPanelInfo());
        JSONTransmitter.startConnection("10.1.33.158");

        try {
            Thread.sleep(1000); // Wait 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONTransmitter.broadcastJSON("Ian is dumb");
        String ip = InteractionHandler.getPlayerConnection();
        if (ip == null) {
            ArrayList<String> answers = new ArrayList<>();
            answers.add("2");
            answers.add("3");
            answers.add("4");
            answers.add("5");
            int numOfPlayers = InteractionHandler.askPlayer("How many players?", answers) + 2;
            BoardManager.setUp(numOfPlayers);
            //connection

            while (BoardManager.winner() == -1) {
                //assign troops
                //attacking
                //free move
            }
            InteractionHandler.displayMessage(BoardManager.winner() + " won!", 100);
            //allow connections
            //ask if ready to start
        } else {
            //peer
        }
        BoardManager.setUp(5);
    }
}
