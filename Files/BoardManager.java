package Files;

import Files.Continent;
import Files.Player;
import Files.Territory;
import java.util.ArrayList;
import java.util.Arrays;

public class BoardManager {
    private ArrayList<Player> players = new ArrayList<Player>();

    private static Continent northAmerica;
    private static Continent asia;
    private static Continent europe;
    private static Continent africa;
    private static Continent southAmerica;
    private static Continent australia;
    private static ArrayList<Continent> continents;

    private static ArrayList<Territory> territories = new ArrayList<Territory>();

    /**
     * Constructs the BoardManager object
     * Instantiates the individual Player objects in players
     * Starts number for players at 0
     * 
     * @param numOfPlayers number of players in the game
     */
    public BoardManager(Integer numOfPlayers) {
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player(i));
        }
        
        continents = new ArrayList<Continent>();
        northAmerica = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Alaska"),
                new Territory("Northwest Territory"),
                new Territory("Alberta"),
                new Territory("Western United States"),
                new Territory("Eastern United States"),
                new Territory("Central America"),
                new Territory("Ontario"),
                new Territory("Quebec"),
                new Territory("Greenland"))), 5);
        continents.add(northAmerica);
        asia = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Afghanistan"),
                new Territory("Middle East"),
                new Territory("India"),
                new Territory("Siam"),
                new Territory("China"),
                new Territory("Japan"),
                new Territory("Mongolia"),
                new Territory("Irkutsk"),
                new Territory("Yakutsk"),
                new Territory("Siberia"),
                new Territory("Kamchatka"),
                new Territory("Ural"))), 7);
        continents.add(asia);
        europe = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Iceland"),
                new Territory("Great Britain"),
                new Territory("Northern Europe"),
                new Territory("Western Europe"),
                new Territory("Southern Europe"),
                new Territory("Scandinavia"),
                new Territory("Ukraine"))), 5);
        continents.add(europe);
        africa = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Egypt"),
                new Territory("North Africa"),
                new Territory("East Africa"),
                new Territory("Congo"),
                new Territory("South Africa"),
                new Territory("Madagascar"))), 3);
        continents.add(africa);
        southAmerica = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Brazil"),
                new Territory("Peru"),
                new Territory("Argentina"),
                new Territory("Venezuela"))), 2);
        continents.add(southAmerica);
        australia = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Indonesia"),
                new Territory("New Guinea"),
                new Territory("Western Australia"),
                new Territory("Eastern Australia"))), 2);
        continents.add(australia);

        territories = new ArrayList<Territory>();
        for (Continent continent : continents) {
            territories.addAll(continent.getTerritories());
        }

    }

    public static Continent getNorthAmerica() {
        return northAmerica;
    }

    public static Continent getAsia() {
        return asia;
    }

    public static Continent getEurope() {
        return europe;
    }

    public static Continent getAfrica() {
        return africa;
    }

    public static Continent getSouthAmerica() {
        return southAmerica;
    }

    public static Continent getAustralia() {
        return australia;
    }

    /**
     * Evenly divides all the territories between the players.
     * If the amount of territories does not evenly divide by the amount of players
     * then the extra territories are added one by one in turn order
     */
    public void initalizeTerritoryOwners() {
        Integer territoriesPerPlayer = territories.size() / players.size();
        Integer excessTerritories = territories.size() % territoriesPerPlayer;
        Integer playerNum = -1;

        for (int i = 0; i < territories.size() - excessTerritories; i++) {
            if (i % players.size() == 0) {
                playerNum++;
            }
            territories.get(i).setOwner(playerNum);
        }
        for (int i = territories.size() - excessTerritories; i < territories.size(); i++) {
            territories.get(i).setOwner(i - territories.size());
        }

    }

    /**
     * Determines amount of troops given to player at the start of turn
     * * @param playerObject
     * 
     * @return Integer amount of troops given at start of turn
     */
    public Integer determineTroopAmnt(Player temp) {
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
        /*
         * for (Continent c : continents) {
         * boolean ownsAll = true;
         * for (Territory t : c.getTerritories()) {
         * if (t.getOwner() != temp.getNum()) {
         * ownsAll = false;
         * break;
         * }
         * }
         * if (ownsAll) {
         * continentBonus += c.getBonusTroops();
         * }
         * }
         */
        return -1;
    }
}