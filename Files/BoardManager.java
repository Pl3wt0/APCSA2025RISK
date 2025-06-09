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
    public static void setUp(Integer numOfPlayers) {
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player(i));
        }
        
        continents = new ArrayList<Continent>();
        northAmerica = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Alaska", -47.16954494708539, 79.1495208478801),
                new Territory("Northwest Territory", -125.1701244053132, 83.36468447164326),
                new Territory("Alberta", -117.26808409831632, 124.57959524550874),
                new Territory("Western United States", -166.91249128652166, 131.40190761322975),
                new Territory("Eastern United States", -116.62982608336678, 181.34062808431923),
                new Territory("Central America", -166.04109044711365, 191.48811045288477),
                new Territory("Ontario", -122.52533665787648, 240.23053157246906),
                new Territory("Quebec", -223.67352412533268, 132.04016562817947),
                new Territory("Greenland", -276.0544747486142, 52.174915149491284))), 5);
        continents.add(northAmerica);
        asia = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Afghanistan", -544.3032966580399, 182.3757126104266),
                new Territory("Middle East", -497.7688595561624, 246.69774570476514),
                new Territory("India", -589.8312043274859, 255.1128366198565),
                new Territory("Siam", -648.6195069392144, 277.2749145516315),
                new Territory("China", -642.5539113489031, 224.05224086288067),
                new Territory("Japan", -738.4012986589385, 182.1270361491958),
                new Territory("Mongolia", -653.2516425365014, 175.49525609590722),
                new Territory("Irkutsk", -634.0886979687248, 134.20356470751082),
                new Territory("Yakutsk", -657.7171225780106, 65.38393091796144),
                new Territory("Siberia", -601.4617622007714, 86.51318883058161),
                new Territory("Kamchatka", -728.0031030450713, 78.98102434942712),
                new Territory("Ural", -550.3053951903319, 112.54145344306804))), 7);
        continents.add(asia);
        europe = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Iceland", -338, 100),
                new Territory("Great Britain", -339, 159),
                new Territory("Northern Europe", -404, 167),
                new Territory("Western Europe", -346, 211),
                new Territory("Southern Europe", -413, 206),
                new Territory("Scandinavia", -392, 108),
                new Territory("Ukraine", -458, 149))), 5);
        continents.add(europe);
        africa = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Egypt", -435, 285),
                new Territory("North Africa", -368, 305),
                new Territory("East Africa", -463, 335),
                new Territory("Congo", -423, 374),
                new Territory("South Africa", -430, 435),
                new Territory("Madagascar", -510, 431))), 3);
        continents.add(africa);
        southAmerica = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Brazil", -245, 325),
                new Territory("Peru", -190, 345),
                new Territory("Argentina", -199, 395),
                new Territory("Venezuela", -182, 275))), 2);
        continents.add(southAmerica);
        australia = new Continent(new ArrayList<>(Arrays.asList(
                new Territory("Indonesia", -660, 364),
                new Territory("New Guinea", -730, 340),
                new Territory("Western Australia", -695, 425),
                new Territory("Eastern Australia", -750, 405))), 2);
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

    public static void setAsia(Continent a){
        asia = a;
    }

    public static void setAfrica(Continent a){
        africa = a;
    }

    public static void setAustralia(Continent a){
        australia = a;
    }

    public static void setEurope(Continent e){
        europe = e;
    }

    public static void setNorthAmerica(Continent na){
        northAmerica = na;
    }

    public static void setSouthAmerica(Continent sa){
        southAmerica = sa;
    }

    public static void setPlayers(ArrayList<Player> p){
        players = p;
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