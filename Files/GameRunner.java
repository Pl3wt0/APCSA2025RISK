package Files;

import Files.JSONStuff.*;
import Files.RenderingStuff.*;
import java.util.*;

public class GameRunner extends Thread {
    private SceneInfo sceneInfo;
    public static String ip;

    public GameRunner(SceneInfo sceneInfo) {
        this.sceneInfo = sceneInfo;
    }


    public void run() {
        InteractionHandler.setSceneInfo(sceneInfo, sceneInfo.getPanelInfo());
        ip = InteractionHandler.getPlayerConnection();
        if (ip == null) {
            JSONThread jsonThread = new JSONThread();
            ArrayList<String> answers = new ArrayList<>(Arrays.asList("2", "3", "4", "5"));
            int numOfPlayers = InteractionHandler.askPlayer("How many players?", answers) + 2;
            BoardManager.setUp(numOfPlayers);
            JSONManager.writeJSONGameState();
            InteractionHandler.player = BoardManager.getPlayers().get(0);
            jsonThread.start();
            
            
            

            //connection

            int num = 0;
            while (BoardManager.winner() == -1) {
                JSONTransmitter.broadcastTextMessage("UPT:" + BoardManager.getTerritories().get(num).territoryName + ".0.2.hiiii");
                num++;
                InteractionHandler.sleep(1000);
                //JSONTransmitter.broadcastTextMessage("");
                //assign troops
                //attacking
                //free move
            }
            InteractionHandler.displayMessage(BoardManager.winner() + " won!", 100);
            //allow connections
            //ask if ready to start
        } else {
            BoardManager.setUp(2);
            JSONTransmitter.startConnection(ip);
            JSONThread jsonThread = new JSONThread();
            jsonThread.start();
        }
        
    }
}
