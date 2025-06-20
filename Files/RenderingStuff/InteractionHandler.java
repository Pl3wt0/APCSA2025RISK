package Files.RenderingStuff;

import java.awt.Color;
import java.awt.Dimension;

import Files.BoardManager;
import Files.GamePiece;
import Files.Territory;
import Files.RenderingStuff.GUIElements.CustomButton;
import Files.RenderingStuff.GUIElements.InputButton;
import Files.RenderingStuff.GUIElements.TextButton;
import Files.RenderingStuff.SceneObjects.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.JPanel;
import tools.a;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.RuleBasedCollator;
import java.util.*;
import tools.a;
import javax.swing.*;

import tools.*;

public class InteractionHandler {
    public static SceneInfo sceneInfo;
    public static PanelInfo panelInfo;
    public static Files.Player player;

    private static TextButton waitingButton = null;

    public static void setSceneInfo(SceneInfo sceneInfo, PanelInfo panelInfo) {
        InteractionHandler.sceneInfo = sceneInfo;
        InteractionHandler.panelInfo = panelInfo;
    }

    public static void setPlayer(Files.Player player) {
        InteractionHandler.player = player;
    }

    public static Files.Player getPlayer() {
        return player;
    }

    /**
     * 
     * @param time Time to sleep in millliseconds
     */
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 
     * @param mesh     Mesh to move
     * @param location Location to move to
     * @param time     Time to take moving in seconds
     */
    public static void moveObject(Mesh mesh, double[] location, double time) {
        double[] meshLocation = mesh.getPosition();
        mesh.setVelocities((location[0] - meshLocation[0]) / time, (location[1] - meshLocation[1]) / time,
                (location[2] - meshLocation[2]) / time);
        sleep((int) (time * 1000));
        mesh.setPosition(location[0], location[1], location[2]);
        mesh.setVelocities(0, 0, 0);
    }

    public static void doSomething() {
        sceneInfo.getCamera().rotate(1, 0);
    }

    public static Color getColor(int playerNum) {
        switch (playerNum) {
            case 0:
                return new Color(255, 17, 0);
            case 1:
                return new Color(0, 0, 255);
            case 2:
                return new Color(255, 238, 0);
            case 3:
                return new Color(26, 255, 0);
            case 4:
                return new Color(255, 0, 234);
        }
        return new Color(0, 0, 0);

    }

    /**
     * Method to check if player wants to host or connect to another player
     * 
     * @return null if player is hosting returns ip if player is a peer
     */
    public static String getPlayerConnection() {
        ArrayList<String> answers = new ArrayList<String>();
        answers.add("host");
        answers.add("peer");
        if (askPlayer("host or peer???", answers) == 0) {
            return null;
        } else {
            double[] location = { 0.3, 0.3 };
            InputButton inputButton = new InputButton(sceneInfo, location, 0.4, 0.2, null, 18);

            sceneInfo.getGuiElements().add(inputButton);

            while (!inputButton.isDone()) {
                sleep(100);
            }

            sceneInfo.getGuiElements().remove(inputButton);

            return inputButton.getText();
        }
    }

    /**
     * Ask player to choose between set of options
     * 
     * @param question Question to be asked in top display
     * @param answers  ArrayList of answers to be selected from
     * 
     * @return Index of the option chosen
     */
    public static int askPlayer(String question, ArrayList<String> answers) {

        ArrayList<GUIElement> guiElements = sceneInfo.getGuiElements();
        Dimension dimension = panelInfo.getDimension();
        double width = dimension.getWidth();
        double height = dimension.getHeight();
        double[] point = { 0.3, 0.1 };
        double[] hitBox1 = { 0.4, 0.2 };
        CustomButton questionButton = new TextButton(sceneInfo, point, 0.4, 0.3, hitBox1, question, 30);
        guiElements.add(questionButton);

        Integer buttonClicked = -1;

        ArrayList<CustomButton> answerButtons = new ArrayList<CustomButton>();
        for (int i = 0; i < answers.size(); i++) {
            String answer = answers.get(i);
            double[] location = { 0.025 + 1.0 / answers.size() * i, 0.6 };
            double[] hitBox2 = { 1.0 / answers.size() - 0.1, 0.1 };

            final int j = i;
            answerButtons
                    .add(new TextButton(sceneInfo, location, 1.0 / answers.size() - 0.05, 0.25, hitBox2, answer, 20) {
                        private int i = j;

                        @Override
                        public void whenClicked(MouseEvent e) {
                            // TODO Auto-generated method stub
                            super.whenClicked(e);
                            returnValue = Integer.valueOf(i);
                        }
                    });
        }

        for (CustomButton answerButton : answerButtons) {
            guiElements.add(answerButton);
        }

        int returnValue = -1;
        while (returnValue == -1) {
            sleep(100);
            for (CustomButton answerButton : answerButtons) {
                if (answerButton.getReturnValue() != null) {
                    returnValue = (Integer) answerButton.getReturnValue();
                }
            }
        }

        questionButton.remove();
        for (CustomButton answerButton : answerButtons) {
            answerButton.remove();
        }

        return returnValue;
    }

    /**
     * Displays message to user
     * 
     * @param message Message to be displayed
     * @param length  Length of time in seconds before display disappears
     */
    public static void displayMessage(String message, double length) {
        double[] location = { 0.05, 0.05 };
        TextButton button = new TextButton(sceneInfo, location, 0.3, 0.1, null, message, 20);
        javax.swing.Timer timer = new javax.swing.Timer((int) (length * 1000), (new ActionListener() {
            TextButton originButton = button;

            @Override
            public void actionPerformed(ActionEvent e) {
                originButton.remove();
            }
        }));
        timer.start();

        sceneInfo.getGuiElements().add(button);
    }

    private static Territory askForTerritoryReturnTerritory;

    public static Territory askForTerritory(String displayMessage) {
        double[] location = { 0.05, 0.05 };
        TextButton button = new TextButton(sceneInfo, location, 0.3, 0.1, null, displayMessage, 20);
        sceneInfo.getGuiElements().add(button);

        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                double[] pointOnMap = Tools3D.getFloorPoint(sceneInfo.getCamera(), e.getX(), e.getY(),
                        panelInfo.getDimension(), 0);
                ArrayList<Territory> territories = BoardManager.getTerritories();
                territories.sort(
                        Comparator.comparing((territory) -> Tools3D.getDistance((Point3D) territory, new Point3D() {
                            public double[] getPosition() {
                                double[] returnPoint = { pointOnMap[0], pointOnMap[1], 0 };
                                return returnPoint;
                            };
                        })));
                setAskForTerritoryReturnValue(territories.get(0));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        };
        panelInfo.addMouseListener(mouseListener);

        setAskForTerritoryReturnValue(null);
        while (true) {
            sleep(50);
            if (askForTerritoryReturnTerritory != null) {
                break;
            }
        }

        panelInfo.removeMouseListener(mouseListener);
        sceneInfo.getGuiElements().remove(button);

        Territory returnTerritory = askForTerritoryReturnTerritory;

        setAskForTerritoryReturnValue(null);

        return returnTerritory;
    }

    public static void setAskForTerritoryReturnValue(Territory territory) {
        askForTerritoryReturnTerritory = territory;
    }

    public static void addSceneObject(SceneObject sceneObject) {
        sceneInfo.getSceneObjects().add(sceneObject);
    }

    public static void addGUIElement(GUIElement guiElement) {
        sceneInfo.getGuiElements().add(guiElement);
    }

    public static SceneInfo getSceneInfo() {
        return sceneInfo;
    }

    public static PanelInfo getPanelInfo() {
        return panelInfo;
    }

    public static int getPlayerNum() {
        return player.getNum();
    }

    public static void animateGameSetUp() {

    }

    public static void startWaiting(String message) {
        double[] location = { 0.05, 0.05 };
        waitingButton = new TextButton(sceneInfo, location, 0.3, 0.1, null, message, 20);
        sceneInfo.getGuiElements().add(waitingButton);
    }

    public static void stopWaiting() {
        sceneInfo.getGuiElements().remove(waitingButton);
        waitingButton = null;
    }

    public static void askTroopAssignment(int numTroops) {
        while (true) {
            Territory pickedTerritory = askForTerritory("Pick troop assignment");
            placeGamePiece(pickedTerritory, player);
            // send message to host
            numTroops--;
            if (numTroops == 0) {
                break;
            }
        }
    }

    public static void animateTroopGain() {

    }

    public static void placeGamePiece(Territory t, Files.Player p) {
        GamePiece gamePiece = new GamePiece(0, t);
        t.pieces.add(gamePiece);
        gamePiece.setPosition(t.getPosition()[0], t.getPosition()[1], t.getPosition()[2] + t.getPieces().size() * 10);
        sceneInfo.getSceneObjects().add(gamePiece);
    }

    public static void updateTerritory(String territory, int owner, int numTroops) {
        Territory t = null;
        for (Territory t2 : BoardManager.getTerritories()) {
            if (t2.territoryName.equals(territory)) {
                t = t2;
            }
        }
        if (t == null) {
            a.prl("Territory name is wrong in updateTerritory");
            return;
        }
        t.setOwner(owner);
        for (GamePiece gamePiece : t.getPieces()) {
            gamePiece.removeFromScene();
        }
        t.getPieces().clear();
        for (int i = 0; i < numTroops; i++) {
            t.getPieces().add(new GamePiece(owner, t));
        }
    }

    public static void parseMessage(String message) {
        if (message.substring(0, 3).equals("ATK")) {
            message = message.substring(4);
            Territory territory1 = BoardManager.getTerritory(message.substring(0, message.indexOf(".")));
            message = message.substring(message.indexOf(".") + 1);
            Territory territory2 = BoardManager.getTerritory(message.substring(0, message.indexOf(".")));
            message = message.substring(message.indexOf(".") + 1);
            if (territory1 == null || territory2 == null) {
                a.prl("parsemessage ATK error");
            }
            int numTroops = Integer.valueOf(message.substring(0, message.indexOf(".")));
            String displayMessage = message;
            a.prl("ATK of " + territory1.getTerritoryName() + ", " + territory2.getTerritoryName() + ", " + numTroops
                    + ". " + displayMessage);

        } else if (message.substring(0, 3).equals("TRN")) {
            message = message.substring(4);
            int playerNum = Integer.valueOf(message.substring(0, message.indexOf(".")));
            message = message.substring(message.indexOf(".") + 1);
            int numTroops = Integer.valueOf(message.substring(0, message.indexOf(".")));
            message = message.substring(message.indexOf(".") + 1);
            String displayMessage = message;
            a.prl("TRN of " + playerNum + ", " + numTroops + ". " + displayMessage);

        } else if (message.substring(0, 3).equals("UPT")) {
            message = message.substring(4);
            Territory territory = BoardManager.getTerritory(message.substring(0, message.indexOf(".")));
            if (territory == null) {
                a.prl("parsemessage UPT error");
            }
            message = message.substring(message.indexOf(".") + 1);
            int numTroops = Integer.valueOf(message.substring(0, message.indexOf(".")));
            message = message.substring(message.indexOf(".") + 1);
            int playerNum = Integer.valueOf(message.substring(0, message.indexOf(".")));
            message = message.substring(message.indexOf(".") + 1);
            String displayMessage = message;
            updateTerritory(territory.getTerritoryName(), playerNum, numTroops);
            displayMessage(displayMessage, 5);
            a.prl(territory.getTerritoryName() + numTroops + playerNum);

        } else if (message.substring(0, 3).equals("DEF")) {
            message = message.substring(4);
            int numTroops = Integer.valueOf(message.substring(0, message.indexOf(".")));
            String displayMessage = message;
            a.prl("DEF of " + numTroops + ". " + displayMessage);

        }
    }

    public static void assignTroops() {
        int territoryCount = 0;
        for (Territory territory : BoardManager.getTerritories()) {
            if (territory.getOwner() == player.getNum()) {
                territoryCount++;
            }
        }
        int numTroops = Math.max(3, territoryCount / 3);
        askTroopAssignment(numTroops);
    }

    public static void renderBoardManager() {
        for (Territory t : BoardManager.getTerritories()) {
            updateTerritory(t.territoryName, t.getOwner(), t.getPieces().size());
            for (GamePiece gamePiece : t.getPieces()) {
                InteractionHandler.getSceneInfo().getSceneObjects().add(gamePiece);
            }
        }
        for (SceneElement sceneElement : InteractionHandler.getSceneInfo().getSceneObjects(GamePiece.class)) {
            InteractionHandler.getSceneInfo().getSceneObjects().remove(sceneElement);
        }
        for (Files.Territory t : BoardManager.getTerritories()) {
            InteractionHandler.getSceneInfo().getSceneObjects().addAll(t.getPieces());
        }

    }

}
