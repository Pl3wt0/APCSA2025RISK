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

        panelInfo.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                a.prl(camera.getPosition());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
            
        });
        

        sceneObjects.add(new Image3D(sceneInfo, point, 800, 497, "RiskBoard.PNG") {
            @Override
            public double[] getPosition() {
                double[] location = {0, -10000000, 0};
                return location;
            }
        });
                
/*      for (int i = 0; i < 10; i += 1) {
            for (int j = 0; j < 10; j += 1) {
                for (int k = 0; k < 10; k++) {
                    sceneObjects.add(new Cube(i, j, k, 0.5, 0, 0));
                }
            }
        }
        */         
        //sceneObjects.add(new Particle(0,0,-10,3,6,"/Users/ian/Downloads/Dune.jpg"));

/*           for (int i = 0; i < 30; i++) {
            sceneObjects.add(new Boid(Math.random() * 100 - 50,Math.random() * 100 - 50,Math.random() * 100 - 50,0.5,Math.random() * Math.PI * 2,Math.random() * Math.PI));

        }
 */        
/*          Graph graph = new Graph(-10, 10, -10, 10, 70, 70, -50, 50, -50, 50){
            public double function(double x, double y, double t) {
                return 4 * (Math.sin(x + t) + Math.sin(y + t));
            }
        };
        sceneObjects.add(graph);
 */

 /*         sceneObjects.add(new MeshSphere(0, 0, 100, 1, 10) {
            private double xVelocity = 0;
            private double yVelocity = 0;
            private double zVelocity = 0;

            public void tick(PanelInfo panelInfo, SceneInfo sceneInfo) {        
                zVelocity -= 1.0 * sceneInfo.getLastFrameLength();
                setPosition(getX() + xVelocity, getY() + yVelocity, getZ() + zVelocity);
                super.tick(panelInfo, sceneInfo);
            }               
        });
 */
        //sceneObjects.add(new MeshSphere(0, 0, 20, 1, 10));

        //sceneObjects.add(new RecursiveCube(0, 10, 0, 3, 4, sceneObjects, -1));

/*          for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                sceneObjects.add(new Cube(i - 5, j - 5, 0, 0.5, 0, 0));
            }
        }
 */     }

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
        InteractionHandler.setSceneInfo(sceneInfo, containingPanel.getPanelInfo());
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
