package Files.RenderingStuff;

import Files.RenderingStuff.SceneObjects.*;

public class InteractionHandler {
    private static SceneInfo sceneInfo;

    public static void setSceneInfo(SceneInfo sceneInfo) {
        InteractionHandler.sceneInfo = sceneInfo;
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {}
    }

    public static void moveObject(Mesh mesh, double[] location, double time) {
        double[] meshLocation = mesh.getPosition();
        mesh.setVelocities((location[0] - meshLocation[0]) / time, (location[1] - meshLocation[1]) / time, (location[2] - meshLocation[2]) / time);
        sleep((int)(time * 1000));
        mesh.setPosition(location[0], location[1], location[2]);
    }

    public static void doSomething() {
        sceneInfo.getCamera().rotate(1,0);
    }

    /**
     * Method to check if player wants to host or connect to another player
     * 
     * @return null if player is hosting returns ip if player is a peer
     */
    public static String getPlayerConnection(){

    }
}

