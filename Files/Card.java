package Files;
public class Card {
    private boolean horse;
    private boolean cannon;
    private boolean troop; 

    public Card(boolean horse, boolean cannon, boolean troop){
        this.horse = horse;
        this.cannon = cannon;
        this.troop = troop;
    }

    public boolean getHorse(){
        return horse;
    }

    public boolean getCannon(){
        return cannon;
    }

    public boolean getTroop(){
        return troop;
    }

    public String toString(){
        if (horse && cannon && troop){
            return "Joker";
        }

        if(horse){
            return "Horse";
        }
        if(cannon){
            return "Cannon";
        }
        if(troop){
            return "Troop";
        }
        return "-1";
    }
    // logic
    // three horse
    // three cannon
    // three troop
    // one of each kind
    // any two cards and a joker
    // any one card and two jokers
}
