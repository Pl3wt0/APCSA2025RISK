package Files;
import Files.RenderingStuff.InteractionHandler;
import Files.RenderingStuff.SceneObjects.*;;

public class GamePiece extends Cube {
    private String color;
    private Integer playerNum;

    /**
     * Constructs a gamepiece object
     * 
     * @param color color of the gamepiece when rendered
     * @param playerNum number of the player who owns the gamepiece
     */
    public GamePiece(String color, Integer playerNum) {
        super(0, 0, 0, 10);
        
        this.faceColor = InteractionHandler.getColor(playerNum);
        this.color = color;
        this.playerNum = playerNum;
    }
}
