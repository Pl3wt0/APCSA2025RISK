package Files;

public class Dice {
    private Integer sides=0;

    /**
     * Constructs dice object with specific number of sides
     * 
     * @param sides number of sides for the dice object
     */
    public Dice(Integer sides){
        this.sides = sides;
    }

    /**
     * Method to roll the dice with the specifc number of sides
     * 
     * @return Integer result of the dice roll
     */
    public Integer rollDice(){
        return (int)(Math.random()*sides)+1;
    }
}
