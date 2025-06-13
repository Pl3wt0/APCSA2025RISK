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
            JSONTransmitter.startConnection(ip);

            while (BoardManager.winner() == -1) {
                
                //assign troops
                //attacking
                //free move
            }
            InteractionHandler.displayMessage(BoardManager.winner() + " won!", 100);
            //allow connections
            //ask if ready to start
        } else {
            JSONTransmitter.startConnection(ip);
            //peer
        }
        
    }
}
