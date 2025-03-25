package Files;

import java.util.ArrayList;

public class Territory {

    private ArrayList<GamePiece> pieces = new ArrayList<>();
    private ArrayList<Territory> neighbors = new ArrayList<>();

    private String playerOwner = "";
    private String continent = "";

    /**
     * Constructs a Territory object with the continent
     * and the player who owns the territory
     * 
     * @param continent what continetn the territory is located on
     * @param playerOwner the player who owns the continent
     */
    public Territory(String continent, String playerOwner) {
        this.continent = continent;
        this.playerOwner = playerOwner;
    }
}
