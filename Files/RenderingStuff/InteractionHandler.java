package Files.RenderingStuff;

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

    public static void doSomething() {
        sceneInfo.getCamera().rotate(1,0);
    }
}

