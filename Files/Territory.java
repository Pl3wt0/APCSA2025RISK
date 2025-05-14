package Files;

import java.util.ArrayList;

import Files.RenderingStuff.Point3D;

public class Territory implements Point3D {

    public ArrayList<GamePiece> pieces = new ArrayList<>();
    public ArrayList<Territory> neighbors = new ArrayList<>();
    public Integer playerOwner;
    public String territoryName;
    public double[] position;

    /**
     * Constructs a Territory object with the continent
     * 
     * @param name name of territory
     * 
     */
    public Territory(String name, double x, double y) {
        territoryName = name;
        double[] position = {x, y, 0};
        this.position = position;
    }

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
     * Sets the owner of the territory object
     * 
     * @param playerNum the number of the player that is being set as the owner
     */
    public void setOwner(Integer playerNum){
        playerOwner = playerNum;
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
    
    public double[] getPosition() {
        double[] returnPoint = {position[0], position[1], 5};
        return returnPoint;
    }
}
