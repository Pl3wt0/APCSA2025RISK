package Files;

import java.util.ArrayList;

public class Territory {

    ArrayList<GamePiece> pieces = new ArrayList<>();
    ArrayList<Territory> neighbors = new ArrayList<>();

    String playerOwner = "";
    String continent = "";

    public Territory(String continent, String playerOwner) {
        this.continent = continent;
        this.playerOwner = playerOwner;
    }
}
