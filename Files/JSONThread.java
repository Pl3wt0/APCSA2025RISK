package Files;

import Files.JSONStuff.JSONManager;
import Files.JSONStuff.JSONTransmitter;
import Files.RenderingStuff.InteractionHandler;

public class JSONThread extends Thread {
    @Override
    public void run() {
        JSONTransmitter.setMessageHandler(new JSONTransmitter.MessageHandler() {
                
                @Override
                public void onTextReceived(String text) {
                    InteractionHandler.parseMessage(text);
                }

                @Override
                public void onGameStateReceived(String fileName) {
                   
                }
            });

            
            JSONTransmitter.startConnection(GameRunner.ip);
            InteractionHandler.sleep(15000);
            JSONTransmitter.broadcastTextMessage("UPT:Alaska.2.0.display message");

    }
}
