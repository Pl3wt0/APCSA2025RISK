package Files;
import java.util.ArrayList;
public class Deck {
    ArrayList<Card> deck = new ArrayList<Card>();
    public Deck(){
        for(int i = 0; i<44; i++){
            if(i<14){
                deck.add(Card(horse));
            }
            if(i>=14 && i<28){
                deck.add(Card(troop));
            }
            if(i>=28 && i<42){
                deck.add(Card(cannon));
            }
            if(i>=42){
                deck.add(Card(Joker));
            }
        }
    }
}
