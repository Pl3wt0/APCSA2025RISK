package Files;

public class GamePiece {
    private String color;
    private Integer playerNum;

    /**
     * Constructs a gamepiece object
     * 
     * @param color color of the gamepiece when rendered
     * @param playerNum number of the player who owns the gamepiece
     */
    public GamePiece(String color, Integer playerNum){
        this.color = color;
        this.playerNum = playerNum;
    }
}
