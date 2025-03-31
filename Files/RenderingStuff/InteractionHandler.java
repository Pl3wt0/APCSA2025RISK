package Files.RenderingStuff;

import java.awt.Color;

import Files.RenderingStuff.SceneObjects.*;

public class InteractionHandler {
    private static SceneInfo sceneInfo;

    public static void setSceneInfo(SceneInfo sceneInfo) {
        InteractionHandler.sceneInfo = sceneInfo;
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
    }

    public static void doSomething() {
        sceneInfo.getCamera().rotate(1,0);
    }

    public static Color getColor(int playerNum) {
        return new Color(0, 100, 200);
    }
    
}

