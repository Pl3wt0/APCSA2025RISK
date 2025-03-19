package Files.RenderingStuff;

import Files.RenderingStuff.SceneObjects.*;
import Files.GameRunner;
import Files.RenderingStuff.Renderables.*;

import java.util.*;
import tools.a;
import java.awt.*;
import javax.swing.*;

public class Scene {
    private ArrayList<SceneObject> sceneObjects = new ArrayList<SceneObject>();
    private Camera camera;
    private IsKeyPressed isKeyPressed;
    private SceneInfo sceneInfo;
    private Panel3D containingPanel;
    private long lastFrameTime = System.currentTimeMillis();
    private double lastFrameLength = 0.01;

    public void setScene() {
        camera.setValues(-5, 0, 0, 0, (Math.PI / 2), 1);
        Player player = new Player(camera);
        sceneObjects.add(player);
        camera.setPlayer(player);
        
        
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
         Graph graph = new Graph(-10, 10, -10, 10, 70, 70, -50, 50, -50, 50){
            public double function(double x, double y, double t) {
                return 4 * (Math.sin(x + t) + Math.sin(y + t));
            }
        };
        sceneObjects.add(graph);


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
            camera = new Camera();
        } catch (AWTException e) {
            throw new Error("Camera failed");
        }    
        isKeyPressed = new IsKeyPressed();

        InteractionHandler.setSceneInfo(sceneInfo);
        GameRunner gameThread = new GameRunner();
        gameThread.start();
    }

    public SceneInfo getSceneInfo() {
        return sceneInfo;
    }

    public void tickScene(int tps, int tick) {
        PanelInfo panelInfo = containingPanel.getPanelInfo();
        camera.tick(tps, tick, sceneObjects, isKeyPressed);
        ArrayList<SceneObject> sceneObjectsCopy = (ArrayList<SceneObject>)sceneObjects.clone();
        for (SceneObject sceneObject : sceneObjectsCopy) {
            sceneObject.tick(panelInfo, sceneInfo);
        }
    }

    public void renderTickScene(int fps, int tick, Dimension dimension) {
        lastFrameLength = (System.currentTimeMillis() - lastFrameTime) / 1000.0;
        lastFrameTime = System.currentTimeMillis();

        PanelInfo panelInfo = containingPanel.getPanelInfo();
        camera.renderTick(fps, tick, sceneObjects, isKeyPressed, dimension);
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
