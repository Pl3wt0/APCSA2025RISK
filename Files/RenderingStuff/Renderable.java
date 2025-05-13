package Files.RenderingStuff;
import java.awt.*;

public interface Renderable extends SceneObject, Point3D {
    public void render(Graphics2D g2d);
}
