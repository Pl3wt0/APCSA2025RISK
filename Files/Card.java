package Files;
public class Card {
    private boolean horse;
    private boolean cannon;
    private boolean troop; 

    /**
     * Constructs a card object
     * 
     * @param horse determines whether or not the car has a horse on it
     * @param cannon determines whether or not the card has a cannon on it
     * @param troop determines whether or not the card has a troop on it
     */
    public Card(boolean horse, boolean cannon, boolean troop){
        this.horse = horse;
        this.cannon = cannon;
        this.troop = troop;
    }

    /**
     * Accessor method to get the value of the horse instance variable
     * 
     * @return boolean value of horse instance variable
     */
    public boolean getHorse(){
        return horse;
    }

    /**
     * Accessor method to get the value of the cannon instance variable
     * 
     * @return boolean value of the cannon instance variable
     */
    public boolean getCannon(){
        return cannon;
    }

    /**
     * Accessor method to get the value of the troop instance variable
     * 
     * @return boolean value of the cannon instance variable
     */
    public boolean getTroop(){
        return troop;
    }

    /**
     * toString method for the card object
     * 
     * @return String type of card: Horse, Cannon, Troop, or Joker
     */
    @Override
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
