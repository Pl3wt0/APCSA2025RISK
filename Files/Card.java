package Files;
import java.awt.Graphics2D;

import javax.naming.InterruptedNamingException;

import Files.RenderingStuff.InteractionHandler;
import Files.RenderingStuff.PanelInfo;
import Files.RenderingStuff.SceneInfo;
import Files.RenderingStuff.GUIElements.*;
public class Card extends TextButton {
    private boolean horse;
    private boolean cannon;
    private boolean troop;

    private Player owner;

    /**
     * Constructs a card object
     * 
     * @param horse determines whether or not the car has a horse on it
     * @param cannon determines whether or not the card has a cannon on it
     * @param troop determines whether or not the card has a troop on it
     * @param player Owning player. If not owned by player, it is null
     */
    public Card(boolean horse, boolean cannon, boolean troop, Player player){
        super(InteractionHandler.getSceneInfo(), null, 0.1, 0.1, null, "", 10);
        double[] location = {0, 0};
        setLocation(location);

        this.horse = horse;
        this.cannon = cannon;
        this.troop = troop;

        if ((horse && cannon) && troop) {
            setText("Joker");
        } else if (horse) {
            setText("Horse");
        } else if (cannon) {
            setText("Cannon");
        } else {
            setText("Troop");
        }
        super.updateImage();
        owner = BoardManager.getPlayers().get(0);
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

    public void updateLocation() {
        int cardNum = owner.getHand().indexOf(this);
        double[] location = {0.03 + 0.13 * cardNum, 0.03};
        setLocation(location);
    }

    @Override
    public void render(Graphics2D g2d) {
        updateLocation();
        if (owner == BoardManager.getPlayers().get(InteractionHandler.getPlayerNum())) {
            super.render(g2d);
        }
    }
}