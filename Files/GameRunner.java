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
            jsonThread.start();
            ArrayList<String> answers = new ArrayList<>(Arrays.asList("2", "3", "4", "5"));
            int numOfPlayers = InteractionHandler.askPlayer("How many players?", answers) + 2;
            BoardManager.setUp(numOfPlayers);
            InteractionHandler.player = BoardManager.getPlayers().get(0);
            InteractionHandler.sleep(15000);
            InteractionHandler.parseMessage("UPT:Alaska.2.0.display message");
            JSONTransmitter.broadcastTextMessage("UPT:Alaska.2.0.display message");
            

            //connection

            while (BoardManager.winner() == -1) {
                //JSONTransmitter.broadcastTextMessage("");
                //assign troops
                //attacking
                //free move
            }
            InteractionHandler.displayMessage(BoardManager.winner() + " won!", 100);
            //allow connections
            //ask if ready to start
        } else {
            JSONTransmitter.startConnection(ip);
            JSONThread jsonThread = new JSONThread();
            jsonThread.start();
        }
        
    }
}
