package Files;

import java.util.ArrayList;

public class Territory {

    private ArrayList<GamePiece> pieces = new ArrayList<>();
    private ArrayList<Territory> neighbors = new ArrayList<>();

    /**
     * Accessor method for the neighbors instance variable
     * 
     * @return ArrayList<Territory> the list of neighboring territories
     */
    public ArrayList<Territory> getNeighbors() {
        return neighbors;
    }

    /**
     * Adds a neighbor to the neighbors list
     * 
     * @param neighbor the neighboring territory to add
     */
    public void addNeighbor(Territory neighbor) {
        neighbors.add(neighbor);
    }

    private Integer playerOwner;
    private String continent = "";
    private String territoryName = "";

    /**
     * Sets the name of the territory
     * 
     * @param name the name to assign to the territory
     */
    public void setTerritoryName(String name) {
        this.territoryName = name;
    }

    /**
     * Accessor method for the territoryName instance variable
     * 
     * @return String the name of the territory
     */
    public String getTerritoryName() {
        return territoryName;
    }
    /**
     * Constructs a Territory object with the continent
     * 
     * @param continent what continent the territory is located on
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
        playerOwner = playerNum;
    }

    /**
     * Accessor method for the contient instance variable
     * 
     * @return String contient that territory is located on
     */
    public String getContinent(){
        return continent;
    }

    /**
     * Accessor method for playerOwner insance variable
     * 
     * @return Integer the number associated with the player who owns the territory
     */
    public Integer getOwner(){
        return playerOwner;
    }

    /**
     * Accessor method for the pieces instance variable
     * 
     * @return ArrayList<GamePiece> the list of game pieces in the territory
     */
    public ArrayList<GamePiece> getPieces() {
        return pieces;
    }

    public Integer determineNumDice(boolean attack, String territoryName){
        if(attack){
          if(this.getPieces().size() > 3){
            return 3;
          }else{
            return this.getPieces().size();
          }
          }
          else{
            if(this.getPieces().size() > 2){
                return 2;
            }else{
                return this.getPieces().size();
            }
            }
      }  
    
}
