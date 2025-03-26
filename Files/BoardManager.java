package Files;
import java.util.ArrayList;

public class BoardManager {
    private ArrayList <Player> players = new ArrayList<Player>();
    private ArrayList <Territory> territories = new ArrayList<Territory>();
    
    /**
     * Constructs the BoardManager object
     * Instantiates the individual Player objects in players
     * Starts number for players at 0
     * 
     * @param numOfPlayers number of players in the game
     */
    public BoardManager(Integer numOfPlayers){
        for(int i =0;i<numOfPlayers;i++){
            players.add(new Player(i));
        }
    }

    /**
     * Evenly divides all the territories between the players.
     * If the amount of territories does not evenly divide by the amount of players
     * then the extra territories are added one by one in turn order
     */
    public void initalizeTerritoryOwners(){
        Integer territoriesPerPlayer = territories.size()/players.size();
        Integer excessTerritories = territories.size()%territoriesPerPlayer;
        Integer playerNum = -1;

        for(int i=0;i<territories.size()-excessTerritories;i++){
            if(i%players.size() == 0){
                playerNum++;
            }
            territories.get(i).setOwner(playerNum);
        }
        for(int i =territories.size()-excessTerritories;i<territories.size();i++){
            territories.get(i).setOwner(i-territories.size());
        }

    }

    /**
     * Determines amount of troops given to player at the start of turn
     * 
     * @return Integer amount of troops given at start of turn
     */
    public Integer determineTroopAmt(Integer playerNum){
        
    }


    /**
     * 
     */
    public Integer determineNumDice(boolean attack, String territoryName){
        
    }
}
