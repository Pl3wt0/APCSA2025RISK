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
     * @param continent what continetn the territory is located on
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

    }
}
