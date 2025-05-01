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
    
    public <T> ArrayList<T> getSceneObjects(Class<T> clas) {
        ArrayList<SceneObject> sceneObjects = getSceneObjects();
        ArrayList<T> returnList = new ArrayList<T>();
        for (SceneObject sceneObject : sceneObjects) {
            if (clas.isInstance(sceneObject)) {
                returnList.add((T)sceneObject);
            }
        }

        return returnList;
    }

    public ArrayList<GUIElement> getGuiElements() {
        return scene.getGuiElements();
    }

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
