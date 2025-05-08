package Files.RenderingStuff;

import java.awt.Color;
import java.awt.Dimension;

import Files.BoardManager;
import Files.Territory;
import Files.RenderingStuff.GUIElements.CustomButton;
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
    private static SceneInfo sceneInfo;
    private static PanelInfo panelInfo;
    private static int playerNum = 0;

    public static void setSceneInfo(SceneInfo sceneInfo, PanelInfo panelInfo) {
        InteractionHandler.sceneInfo = sceneInfo;
        InteractionHandler.panelInfo = panelInfo;
    }

    /**
     * 
     * @param time Time to sleep in millliseconds
     */
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {}
    }

    /**
     * 
     * @param mesh Mesh to move
     * @param location Location to move to
     * @param time Time to take moving in seconds
     */
    public static void moveObject(Mesh mesh, double[] location, double time) {
        double[] meshLocation = mesh.getPosition();
        mesh.setVelocities((location[0] - meshLocation[0]) / time, (location[1] - meshLocation[1]) / time, (location[2] - meshLocation[2]) / time);
        sleep((int)(time * 1000));
        mesh.setPosition(location[0], location[1], location[2]);
        mesh.setVelocities(0, 0, 0);
    }

    public static void doSomething() {
        sceneInfo.getCamera().rotate(1,0);
    }

    public static Color getColor(int playerNum) {
        return new Color(0, 100, 200);
    }
    

    /**
     * Method to check if player wants to host or connect to another player
     * 
     * @return null if player is hosting returns ip if player is a peer
     */
    public static String getPlayerConnection(){
        return "";
    }

    /** 
     * Ask player to choose between set of options
     * 
     * @param question Question to be asked in top display
     * @param answers ArrayList of answers to be selected from
     * 
     * @return Index of the option chosen
     */
    public static int askPlayer(String question, ArrayList<String> answers) {
        
        ArrayList<GUIElement> guiElements = sceneInfo.getGuiElements();
        Dimension dimension = panelInfo.getDimension();
        double width = dimension.getWidth();
        double height = dimension.getHeight();
        double[] point = {0.3, 0.1};
        double[] hitBox1 = {0.4, 0.2};
        CustomButton questionButton = new TextButton(point, 0.4,  0.3, hitBox1, question, 30, panelInfo, sceneInfo);
        guiElements.add(questionButton);

        Integer buttonClicked = -1;

        ArrayList<CustomButton> answerButtons = new ArrayList<CustomButton>();
        for (int i = 0; i < answers.size(); i++) {
            String answer = answers.get(i);
            double[] location = {0.025 + 1.0 / answers.size() * i, 0.6};
            double[] hitBox2 = {1.0 / answers.size() - 0.1, 0.1};
            
            final int j = i;
            answerButtons.add(new TextButton(location, 1.0 / answers.size() - 0.05, 0.25, hitBox2, answer, 20, panelInfo, sceneInfo) {
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
                    returnValue = (Integer)answerButton.getReturnValue();
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
     * @param length Length of time in seconds before display disappears
     */
    public static void displayMessage(String message, double length) {
        double[] location = {0.05, 0.05};
        TextButton button = new TextButton(location, 0.3, 0.1, null, message, 20, panelInfo, sceneInfo);
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
        double[] location = {0.5, 0.05};
        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                double[] pointOnMap = Tools3D.getFloorPoint(sceneInfo.getCamera(), e.getX(), e.getY(), panelInfo.getDimension(), 0);
                a.prl(pointOnMap[0] + ", " + pointOnMap[1]);
                ArrayList<Territory> territories = BoardManager.getTerritories();
                territories.sort(Comparator.comparing((territory) -> Tools3D.getDistance((Point3D)territory, new Point3D() {
                    public double[] getPosition() {
                        double[] returnPoint = {pointOnMap[0], pointOnMap[1], 0};
                        return returnPoint;
                    };
                })));
                setAskForTerritoryReturnValue(territories.get(0));
                
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
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
        return playerNum;
    }
}

