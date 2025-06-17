package Files;

import Files.JSONStuff.JSONManager;
import Files.JSONStuff.JSONTransmitter;
import Files.RenderingStuff.InteractionHandler;

public class JSONThread extends Thread {
    @Override
    public void run() {
        JSONTransmitter.setMessageHandler(new JSONTransmitter.MessageHandler() {
                @Override
                public void onValueReceived(String value){
                    System.out.println(value);
                }

                @Override
                public void onJSONReceived(String jsonData) {
                }

                @Override
                public void onTextReceived(String text) {
                    InteractionHandler.parseMessage(text);
                }

                @Override
                public void onFileReceived(String fileName) {
                    JSONManager.syncGameStates();
                    
                }
            });

            JSONTransmitter.startConnection(GameRunner.ip);

    }
}
