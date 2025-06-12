package Files;
import Files.RenderingStuff.InteractionHandler;
import Files.RenderingStuff.SceneObjects.*;
import tools.a;;

public class GamePiece extends Cube {
    private Integer playerNum;

    /**
     * Constructs a gamepiece object
     * 
     * @param color color of the gamepiece when rendered
     * @param playerNum number of the player who owns the gamepiece
     */
    public GamePiece(Integer playerNum) {
        super(InteractionHandler.getSceneInfo(), 0, 0, 0, 5);
        
        this.faceColor = InteractionHandler.getColor(playerNum);
        this.playerNum = playerNum;
    }
}
