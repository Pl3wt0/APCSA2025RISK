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
        JSONTransmitter.setMessageHandler(new JSONTransmitter.MessageHandler() {
                @Override
                public void onValueReceived(String value){
                    System.out.println(value);
                }

                @Override
                public void onJSONReceived(String jsonData) {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'onJSONReceived'");
                }

                @Override
                public void onTextReceived(String text) {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'onTextReceived'");
                }

                @Override
                public void onFileReceived(String fileName) {
                    // TODO Auto-generated method stub
                    JSONManager.syncGameStates();
                    ;
                }

            });

        InteractionHandler.setSceneInfo(sceneInfo, sceneInfo.getPanelInfo());
        String ip = InteractionHandler.getPlayerConnection();
        if (ip == null) {
            ArrayList<String> answers = new ArrayList<>(Arrays.asList("2", "3", "4", "5"));
            int numOfPlayers = InteractionHandler.askPlayer("How many players?", answers) + 2;
            BoardManager.setUp(numOfPlayers);
            InteractionHandler.player = BoardManager.getPlayers().get(0);
            InteractionHandler.sleep(5000);
            InteractionHandler.parseMessage("UPT:Alaska.2.0.display message");
            InteractionHandler.sleep(5000);
            InteractionHandler.assignTroops();

            //connection
            //JSONTransmitter.startConnection(ip);

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
        }
        
    }
}
