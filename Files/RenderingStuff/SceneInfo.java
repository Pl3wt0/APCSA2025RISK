package Files.RenderingStuff;

import java.util.*;
import tools.a;
import java.awt.*;
import javax.swing.*;

public class SceneInfo {
    private Scene scene;

    public SceneInfo(Scene scene) {
        this.scene = scene;
    }

    public ArrayList<SceneObject> getSceneObjects() {
        return scene.getSceneObjects();
    }

    public ArrayList<GUIElement> getGuiElements() {
        return scene.getGuiElements();
    }



/*      public ArrayList<clas> getObj(ArrayList<SceneObject> sceneObjects, Class clas) {
        
    }
 */
    public Camera getCamera() {
        return scene.getCamera();
    }

    public IsKeyPressed getIsKeyPressed() {
        return scene.getIsKeyPressed();
    }

    public double getLastFrameLength() {
        return scene.getLastFrameLength();
    }

}
