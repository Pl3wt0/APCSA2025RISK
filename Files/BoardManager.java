package Files;

import Files.Continent;
import Files.Player;
import Files.Territory;
import Files.RenderingStuff.GUIElement;
import Files.RenderingStuff.InteractionHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardManager {
    private static ArrayList<Player> players = new ArrayList<Player>();

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
                new Territory("Alaska", -52, 98),
                new Territory("Northwest Territory", -128, 100),
                new Territory("Alberta", -130, 140),
                new Territory("Western United States", -135, 186),
                new Territory("Eastern United States", -199, 195),
                new Territory("Central America", -148, 241),
                new Territory("Ontario", -189, 143),
                new Territory("Quebec", -245, 145),
                new Territory("Greenland", -313, 67))), 5);
        continents.add(northAmerica);
        asia = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Afghanistan", -512, 196),
                new Territory("Middle East", -468, 255),
                new Territory("India", -551, 275),
                new Territory("Siam", -616, 286),
                new Territory("China", -604, 225),
                new Territory("Japan", -708, 203),
                new Territory("Mongolia", -651, 182),
                new Territory("Irkutsk", -639, 144),
                new Territory("Yakutsk", -662, 95),
                new Territory("Siberia", -587, 125),
                new Territory("Kamchatka", -735, 124),
                new Territory("Ural", -544, 142))), 7);
        continents.add(asia);
        europe = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Iceland", -338, 114),
                new Territory("Great Britain", -339, 159),
                new Territory("Northern Europe", -384, 167),
                new Territory("Western Europe", -341, 211),
                new Territory("Southern Europe", -393, 206),
                new Territory("Scandinavia", -392, 108),
                new Territory("Ukraine", -458, 149))), 5);
        continents.add(europe);
        africa = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Egypt", -400, 271),
                new Territory("North Africa", -348, 305),
                new Territory("East Africa", -443, 335),
                new Territory("Congo", -393, 374),
                new Territory("South Africa", -395, 435),
                new Territory("Madagascar", -460, 431))), 3);
        continents.add(africa);
        southAmerica = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Brazil", -227, 348),
                new Territory("Peru", -172, 352),
                new Territory("Argentina", -199, 419),
                new Territory("Venezuela", -182, 292))), 2);
        continents.add(southAmerica);
        australia = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Indonesia", -629, 347),
                new Territory("New Guinea", -721, 352),
                new Territory("Western Australia", -698, 418),
                new Territory("Eastern Australia", -628, 417))), 2);
        continents.add(australia);

        territories = new ArrayList<Territory>();
        for (Continent continent : continents) {
            territories.addAll(continent.getTerritories());
        }

        Card card1 = new Card(true, false, false, players.get(0));
        Card card2 = new Card(true, true, true, players.get(0));

        InteractionHandler.getSceneInfo().getGuiElements().add(card1);
        InteractionHandler.getSceneInfo().getGuiElements().add(card2);

        players.get(0).addCard(card1);
        players.get(0).addCard(card2);

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

    public static ArrayList<Continent> getContinents() {
        return continents;
    }

    public static ArrayList<Territory> getTerritories() {
        return territories;
    }


    public static ArrayList<Player> getPlayers() {
        return players;
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