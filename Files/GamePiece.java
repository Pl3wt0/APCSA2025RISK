package Files;
import Files.RenderingStuff.InteractionHandler;
import Files.RenderingStuff.SceneObjects.*;
import tools.a;;

public class GamePiece extends Cube {
    private Integer playerNum;
    private Territory location;
    private int numAtTerritory = 0;

    /**
     * Constructs a gamepiece object
     * 
     * @param color color of the gamepiece when rendered
     * @param playerNum number of the player who owns the gamepiece
     */
    public GamePiece(Integer playerNum, Territory location) {
        super(InteractionHandler.getSceneInfo(), 0, 0, 0, 5);
        this.location = location;
        numAtTerritory = location.getPieces().size();

        InteractionHandler.sceneInfo.getSceneObjects().add(this);
        
        this.faceColor = InteractionHandler.getColor(playerNum);
        this.playerNum = playerNum;
    }

    public void removeFromScene() {
        InteractionHandler.sceneInfo.getSceneObjects().remove(this);

    }

    @Override
    public void tick() {
        double[] position = location.position;
        x = position[0];
        y = position[1];
        z = position[2] + 5 + 10 * numAtTerritory; 
        a.prl("GamePiece: " + (InteractionHandler.getSceneInfo().getSceneObjects().indexOf(this) != -1));
    }
}
