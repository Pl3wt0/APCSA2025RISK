public class Continent {
    private ArrayList<String> territories;
    private int bonusTroops;

    public Continent(ArrayList<String> territories, int bonusTroops) {
        this.territories = territories;
        this.bonusTroops = bonusTroops;
    }

    public int getBonusTroops() { return bonusTroops; }
}