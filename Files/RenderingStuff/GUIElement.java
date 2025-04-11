package Files.RenderingStuff;
import Files.RenderingStuff.*;
import tools.a;
import java.awt.*;


public interface GUIElement {
    public void render(Graphics2D g2d, PanelInfo panelInfo, SceneInfo sceneInfo);
    public void tick(PanelInfo panelInfo, SceneInfo sceneInfo);
}
