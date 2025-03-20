package Files;

import java.util.ArrayList;

public class Player {
    private ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
    private ArrayList<Card> hand = new ArrayList<Card>();
    private String playerName = "";
    private Integer playerNum = null;

    public Player(String name, Integer playerNum){
        this.playerNum = playerNum;
        playerName = name;
    }

    

}
