package Files.RenderingStuff;

import Files.RenderingStuff.SceneObjects.*;
import Files.GamePiece;
import Files.GameRunner;
import Files.RenderingStuff.GUIElements.CustomButton;
import Files.RenderingStuff.GUIElements.FileButton;
import Files.RenderingStuff.Renderables.*;

import java.util.*;
import tools.a;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Scene {
    private ArrayList<SceneObject> sceneObjects = new ArrayList<SceneObject>();
    private ArrayList<GUIElement> guiElements = new ArrayList<GUIElement>();
    private Camera camera;
    private IsKeyPressed isKeyPressed;
    private SceneInfo sceneInfo;
    private Panel3D containingPanel;
    private PanelInfo panelInfo;
    private long lastFrameTime = System.currentTimeMillis();
    private double lastFrameLength = 0.01;

    public void setScene() {
        camera.setValues(-386.4161352963328, 409.89117845434197, 351.6666666666672, -(Math.PI / 2), 2.85, 1);
        camera.setControllable(false);
        camera.setActive();
/*         Player player = new Player(sceneInfo);
        player.setValues(-386.4161352963328, 409.89117845434197, 351.6666666666672, -(Math.PI / 2), 2.85);
        camera.setPlayer(player);
 */

 
        double[] point = {0,0,0};
        sceneObjects.add(new Image3D(sceneInfo, point, 800, 497, "RiskBoard.PNG") {
            @Override
            public double[] getPosition() {
                double[] location = {0, -10000000, 0};
                return location;
            }
        });
                
    }

    public Scene(Panel3D containingPanel) {
        this.containingPanel = containingPanel;
        this.panelInfo = containingPanel.getPanelInfo();
        baseSetup();
        setScene();

        
    }

    public void baseSetup() {
        sceneInfo = new SceneInfo(this);
        try {
            camera = new Camera(containingPanel.getSize(), containingPanel.getPanelInfo(), sceneInfo);
        } catch (AWTException e) {
            throw new Error("Camera failed");
        }    
        isKeyPressed = new IsKeyPressed();

        GameRunner gameThread = new GameRunner(sceneInfo);
        gameThread.start();
    }

    public void tickScene(int fps, int tick) {
        camera.tick();

        ArrayList<SceneObject> sceneObjectsCopy = (ArrayList<SceneObject>)sceneObjects.clone();
        for (SceneObject sceneObject : sceneObjectsCopy) {
            sceneObject.tick();
        }

        ArrayList<GUIElement> guiElementsCopy = (ArrayList<GUIElement>)guiElements.clone();
        for (GUIElement guiElement : guiElementsCopy) {
            guiElement.tick();
        }
    }

    public void renderScene(Graphics2D g2d, Dimension dimension) {
        PanelInfo panelInfo = containingPanel.getPanelInfo();
        ArrayList<Renderable> toRender = new ArrayList<Renderable>();
        for (SceneObject sceneObject : sceneObjects) {
            toRender.addAll(sceneObject.getRenderables());
        }

        toRender.sort(Comparator.comparing((renderable) -> Tools3D.getDistance(renderable, camera)));
        Collections.reverse(toRender);

        for (Renderable renderable : toRender) {
            renderable.render(g2d);
        }


        for (GUIElement guiElement : guiElements) {
            guiElement.render(g2d);
        }
    }

    public SceneInfo getSceneInfo() {
        return sceneInfo;
    }

    public void gainFocus() {
        camera.setActive();
    }

    public void loseFocus() {
        camera.setInactive();
    }

    public ArrayList<SceneObject> getSceneObjects() {
        return sceneObjects;
    }

    public ArrayList<GUIElement> getGuiElements() {
        return guiElements;
    }

    public Camera getCamera() {
        return camera;
    }

    public IsKeyPressed getIsKeyPressed() {
        return isKeyPressed;
    }

    public Panel3D getContainingPanel() {
        return containingPanel;
    }

    public PanelInfo getPanelInfo() {
        return panelInfo;
    }

    public double getLastFrameLength() {
        return lastFrameLength;
    }
}
