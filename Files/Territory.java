package Files;

import java.util.ArrayList;

public class Territory {

    private ArrayList<GamePiece> pieces = new ArrayList<>();
    private ArrayList<Territory> neighbors = new ArrayList<>();

    private Integer playerOwner;
    private String continent = "";

    /**
     * Constructs a Territory object with the continent
     * 
     * @param continent what continent the territory is located on
     * 
     */
    public Territory(String continent) {
        this.continent = continent;
    }

    /**
     * Sets the owner of the territory object
     * 
     * @param playerNum the number of the player that is being set as the owner
     */
    public void setOwner(Integer playerNum){
        playerOwner = playerNum;
    }

    /**
     * Accessor method for the contient instance variable
     * 
     * @return String contient that territory is located on
     */
    public String getContinent(){
        return continent;
    }

    /**
     * Accessor method for playerOwner insance variable
     * 
     * @return Integer the number associated with the player who owns the territory
     */
    public Integer getOwner(){
        return playerOwner;
    }
}
