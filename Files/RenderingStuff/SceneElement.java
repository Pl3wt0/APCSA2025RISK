package Files.RenderingStuff;

public abstract class SceneElement {
    protected SceneInfo sceneInfo;
    protected PanelInfo panelInfo;

    public SceneElement(SceneInfo sceneInfo) {
        this.sceneInfo = sceneInfo;
        this.panelInfo = sceneInfo.getPanelInfo();
    }
}
