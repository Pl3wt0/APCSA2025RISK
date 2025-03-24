package Files;
import java.util.ArrayList;

public class BoardManager {
    private ArrayList <Player> players = new ArrayList<Player>();
    ArrayList <Territory> territories = new ArrayList<Territory>();
    
    /**
     * Constructs the BoardManager object
     * Instantiates the individual Player objects in players
     * 
     * @param numOfPlayers number of players in the game
     */
    public BoardManager(Integer numOfPlayers){
        for(int i =0;i<numOfPlayers;i++){
            players.add(new Player(i+1));
        }
    }


}
