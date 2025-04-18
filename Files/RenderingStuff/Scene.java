package Files.RenderingStuff;

import Files.RenderingStuff.SceneObjects.*;
import Files.GameRunner;
import Files.RenderingStuff.GUIElements.CustomButton;
import Files.RenderingStuff.Renderables.*;

import java.util.*;
import tools.a;
import java.awt.*;
import javax.swing.*;

public class Scene {
    private ArrayList<SceneObject> sceneObjects = new ArrayList<SceneObject>();
    private ArrayList<GUIElement> guiElements = new ArrayList<GUIElement>();
    private Camera camera;
    private IsKeyPressed isKeyPressed;
    private SceneInfo sceneInfo;
    private Panel3D containingPanel;
    private long lastFrameTime = System.currentTimeMillis();
    private double lastFrameLength = 0.01;

    public void setScene() {
        camera.setValues(-386.4161352963328, 409.89117845434197, 351.6666666666672, -1.57, 2.85, 1);
        camera.setControllable(false);

        double[] point = {0,0,0};
        double[] hitbox = {0, 0, 800, 497};

        sceneObjects.add(new Image3D(point, 800, 497, "Risk.PNG"));
        //guiElements.add(new CustomButton(point, 800, 497, hitbox, "Risk.PNG", containingPanel.getPanelInfo()));

        sceneObjects.add(new Cube(0, 0, 0, 10));
        
        
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
        baseSetup();
        setScene();

        
    }

    public void baseSetup() {
        sceneInfo = new SceneInfo(this);
        try {
            camera = new Camera(containingPanel.getSize());
        } catch (AWTException e) {
            throw new Error("Camera failed");
        }    
        isKeyPressed = new IsKeyPressed();

        InteractionHandler.setSceneInfo(sceneInfo, containingPanel.getPanelInfo());
        GameRunner gameThread = new GameRunner(sceneInfo);
        gameThread.start();
    }

    public void tickScene(int fps, int tick) {
        PanelInfo panelInfo = containingPanel.getPanelInfo();
        camera.tick(panelInfo, sceneInfo);
        ArrayList<SceneObject> sceneObjectsCopy = (ArrayList<SceneObject>)sceneObjects.clone();
        for (SceneObject sceneObject : sceneObjectsCopy) {
            sceneObject.tick(panelInfo, sceneInfo);
        }
        ArrayList<GUIElement> guiElementsCopy = (ArrayList<GUIElement>)guiElements.clone();
        for (GUIElement guiElement : guiElementsCopy) {
            guiElement.tick(panelInfo, sceneInfo);
        }
    }

    public void renderTickScene(int fps, int tick, Dimension dimension) {
        lastFrameLength = (System.currentTimeMillis() - lastFrameTime) / 1000.0;
        lastFrameTime = System.currentTimeMillis();

        PanelInfo panelInfo = containingPanel.getPanelInfo();
        camera.renderTick(panelInfo, sceneInfo);
        ArrayList<SceneObject> sceneObjectsCopy = (ArrayList<SceneObject>)sceneObjects.clone();
        for (SceneObject sceneObject : sceneObjectsCopy) {
            sceneObject.renderTick(panelInfo, sceneInfo);
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
            renderable.render(g2d, panelInfo, sceneInfo);
        }

        for (GUIElement guiElement : guiElements) {
            guiElement.render(g2d, panelInfo, sceneInfo);
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

    public double getLastFrameLength() {
        return lastFrameLength;
    }
}
