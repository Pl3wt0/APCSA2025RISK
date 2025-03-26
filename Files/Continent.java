public class Continent {
    private List<Territory> territories;
    private int bonusTroops;

    public Continent(List<Territory> territories, int bonusTroops) {
        this.territories = territories;
        this.bonusTroops = bonusTroops;
    }

    public List<Territory> getTerritories() { return territories; }
    public int getBonusTroops() { return bonusTroops; }
}