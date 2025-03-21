package Files;
import java.util.ArrayList;
public class Deck {
    ArrayList<Card> deck = new ArrayList<Card>();


    /**
     *Creates deck object with 14 of each type of card and 2 jokers
     */
    public Deck(){
        for(int i = 0; i<44; i++){
            if(i<14){
                deck.add(new Card(true,false,false));
            }
            if(i>=14 && i<28){
                deck.add(new Card(false,true,false));
            }
            if(i>=28 && i<42){
                deck.add(new Card(false,false,true));
            }
            if(i>=42){
                deck.add(new Card(true,true,true));
            }
        }
    }

    /**
     * Draws a random Card from the deck and removes it
     * 
     * @return      the card that is drawn from the deck
     * 
     */

    // i think this is about what you want? - blake
    public Card drawRandom(){
        int random = (int) (Math.random() * deck.size());
        Card drawn = deck.get(random);
        deck.remove(random);
        return drawn;
    }
}
