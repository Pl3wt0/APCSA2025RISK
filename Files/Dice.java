package Files;

public class Dice {
    private static Integer sides = 0;

    /**
     * Constructs dice object with specific number of sides
     * 
     * @param sides number of sides for the dice object
     */
    public static void setSides(Integer sides) {
        Dice.sides = sides;
    }

    /**
     * Method to roll the dice with the specific number of sides
     * 
     * @return Integer result of the dice roll
     */
    public static Integer rollDice() {
        return (int) (Math.random() * sides) + 1;
    }
}