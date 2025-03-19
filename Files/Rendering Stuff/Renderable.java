package Cube;
import java.awt.*;

public interface Renderable extends SceneObject {
    public double[] getPosition();
    public void render(Graphics2D g2d, PanelInfo panelInfo, SceneInfo sceneInfo);
}
