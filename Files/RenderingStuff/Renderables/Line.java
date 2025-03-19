package Files.RenderingStuff.Renderables;

import java.awt.*;
import java.util.ArrayList;

import Files.RenderingStuff.Camera;
import Files.RenderingStuff.IsKeyPressed;
import Files.RenderingStuff.PanelInfo;
import Files.RenderingStuff.Renderable;
import Files.RenderingStuff.SceneInfo;
import Files.RenderingStuff.SceneObject;
import Files.RenderingStuff.Tools3D;


public class Line implements Renderable {
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private double z1;
    private double z2;
    private Color color;

    public Line(double x1, double x2, double y1, double y2, double z1, double z2, Color color) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
        this.color = color;
    }

    public Line(double[] point1, double[] point2, Color color) {
        this.x1 = point1[0];
        this.x2 = point2[0];
        this.y1 = point1[1];
        this.y2 = point2[1];
        this.z1 = point1[2];
        this.z2 = point2[2];
        this.color = color;
    }
    
    public double[] getPosition() {
        double[] position = {(x1 + x2) / 2, (y1 + y2) / 2, (z1 + z2) / 2};
        return position;
    }

    public void tick(PanelInfo panelInfo, SceneInfo sceneInfo) {

    }

    public void renderTick(PanelInfo panelInfo, SceneInfo sceneInfo) {
        
    }

    public ArrayList<Renderable> getRenderables() {
        ArrayList<Renderable> self = new ArrayList<Renderable>();
        self.add(this);
        return self;
    }

    public void render(Graphics2D g2d, PanelInfo panelInfo, SceneInfo sceneInfo) {
        Camera camera = sceneInfo.getCamera();
        Dimension dimension = panelInfo.getDimension();
        g2d.setColor(color);
        double[] point1 = {x1,y1,z1};
        double[] point2 = {x2,y2,z2};
        double[] screenPoint1 = Tools3D.getScreenPoint(point1, camera, dimension);
        double[] screenPoint2 = Tools3D.getScreenPoint(point2, camera, dimension);

        if (screenPoint1 != Tools3D.nullScreenPoint() && screenPoint2 != Tools3D.nullScreenPoint()) {
            g2d.drawLine((int)screenPoint1[0], (int)screenPoint1[1], (int)screenPoint2[0], (int)screenPoint2[1]);
        }
    }

}
