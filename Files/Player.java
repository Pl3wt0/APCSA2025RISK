package Files;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand = new ArrayList<Card>();
    private String playerName = "";
    private Integer playerNum = null;

    /**
     * Constructor for Player object
     * 
     * @param String name of the player
     * @param Integer number to identify the player
     */
    public Player(Integer playerNum){
        this.playerNum = playerNum;
    }

    /**
     * Sets the player's name to the parameter passed in
     * 
     * @param name String that you wish to set the player's name to
     */
    public void setName(String name){
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

    public ArrayList<Card> getHand() {
        return hand;
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

    public void addCard(Card card) {
        hand.add(card);
    }
    

}
