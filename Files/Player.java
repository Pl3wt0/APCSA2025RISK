package Files;

import java.util.ArrayList;

public class Player {
    private ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
    private ArrayList<Card> hand = new ArrayList<Card>();
    private String playerName = "";
    private Integer playerNum = null;

    /**
     * Constructor for Player object
     * 
     * @param String name of the player
     * @param Integer number to identify the player
     */
    public Player(String name, Integer playerNum){
        this.playerNum = playerNum;
        playerName = name;
    }


    /**
     * Accesses the number associated with the Player object
     * 
     * @return Integer Player Number
     */
    public Integer getNum(){
        return playerNum;
    }

    /**
     * Accesses the name of the player object
     * 
     * @return Integer Player Name
     */
    public String getName(){
        return playerName;
    }


     /** 
     * Adds a Card to hand 
     * Calls drawCard() from deck to get a random card
     * Only adds a Card if hand has less than 5 Card objects
     * 
     * @param deck Deck object that card is drawn from
     * @return boolean if card is added to the deck then return true; else return false.
     */
    public boolean addCard(Deck deck){
        if(hand.size()<5){
            hand.add(deck.drawRandom());
            return true;
        } else{
            return false;
        }
    }

    

}
