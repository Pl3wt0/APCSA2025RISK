package Files;

import java.util.ArrayList;

public class Continent {
    private ArrayList<Territory> territories;
    private int bonusTroops;

    public Continent(ArrayList<Territory> territories, int bonusTroops) {
        this.territories = territories;
        this.bonusTroops = bonusTroops;
    }
    public ArrayList<Territory> getTerritories() { return territories; }
    public int getBonusTroops() { return bonusTroops; }
}