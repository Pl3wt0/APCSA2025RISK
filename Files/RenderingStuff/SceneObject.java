package Files.RenderingStuff;

import java.util.ArrayList;

public interface SceneObject {
    public ArrayList<Renderable> getRenderables();
    public void tick(PanelInfo panelInfo, SceneInfo sceneInfo);
    public void renderTick(PanelInfo panelInfo, SceneInfo sceneInfo);
}
