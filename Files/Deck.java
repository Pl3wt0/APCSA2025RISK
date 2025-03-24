package Files;
import java.util.ArrayList;
public class Deck {
    ArrayList<Card> deck = new ArrayList<Card>();
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
}
