package Files;
import java.util.ArrayList;

public class BoardManager {
    private ArrayList <Player> players = new ArrayList<Player>();
    private ArrayList <Territory> territories = new ArrayList<Territory>();
    Continent northAmerica = new Continent(Arraylist<String>("Alaska", "Northwest Territory", "Alberta", "Western United States", "Eastern United States", "Central America", "Ontario", "Quebec", "Greenland"), 5);
    Continent asia = new Continent(ArrayList<String>("Afghanistan", "Middle East", "India", "Siam", "China", "Japan", "Mongolia", "Irkutsk", "Yakutsk", "Siberia", "Kamchatka", "Ural"), 7);
    Continent europe = new Continent(ArrayList<String>("Iceland", "Great Britain", "Northern Europe", "Western Europe", "Southern Europe", "Scandinavia", "Ukraine"), 5);
    Continent africa = new Continent(ArrayList<String>("Egypt", "North Africa", "East Africa", "Congo", "South Africa", "Madagascar"), 3);
    Continent southAmerica = new Continent(ArrayList<String>("Brazil", "Peru", "Argentina", "Venezuela"), 2);
    Continent australia = new Continent(ArrayList<String>("Indonesia", "New Guinea", "Western Austraila", "Eastern Australia"), 2);
    
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
     *   *  @param playerObject
     * @return Integer amount of troops given at start of turn
     */
    public Integer determineTroopAmnt( Player temp){
        int numTerritory = 0;
        int continentBonus = 0;

        // Count number of territories the player owns
        for (Territory t : territories) {
            if (temp.getNum() == t.getOwner()) {
                numTerritory++;
            }
        }

        // Minimum base troops is 3
        int baseTroops = Math.max(numTerritory / 3, 3);

        // Check for full continent control
        for (Continent c : continents) {
            boolean ownsAll = true;
            for (Territory t : c.getTerritories()) {
                if (t.getOwner() != temp.getNum()) {
                    ownsAll = false;
                    break;
                }
            }
            if (ownsAll) {
                continentBonus += c.getBonusTroops();
            }
        }

        return baseTroops + continentBonus;
    }


        /**
     * Determines amoutn fo troops given to player at the end
     *  @param playerObject
     *@return Interger of troop number
     */
    public Integer determineNumDice(boolean attack, String territoryName){
        if(attack){
          if(territories.getTerritoryByName(territoryName).getPieces().size() > 3){
            return 3;
          }else{
            return territories.getTerritoryName(territoryName).getPieces().size();
          }
        }  
        
    }
}
