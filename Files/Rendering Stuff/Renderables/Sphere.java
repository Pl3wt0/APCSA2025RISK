package Cube.Renderables;
import java.awt.*;
import java.util.ArrayList;

import Cube.Camera;
import Cube.IsKeyPressed;
import Cube.PanelInfo;
import Cube.Renderable;
import Cube.SceneInfo;
import Cube.SceneObject;
import Cube.Tools3D;

import tools.a;;


public class Sphere implements Renderable {
    private double x;
    private double y;
    private double z;
    private double r;

    public Sphere(double x, double y, double z, double r) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
    }

    public Sphere(double[] position, double r) {
        this.x = position[0];
        this.y = position[1];
        this.z = position[2];
        this.r = r;
    }

    public double[] getPosition() {
        double[] point = {x,y,z};
        return point;
    }

    public void render(Graphics2D g2d, PanelInfo panelInfo, SceneInfo sceneInfo) {
        Camera camera = sceneInfo.getCamera();
        Dimension dimension = panelInfo.getDimension();
        
        double[] point = {x,y,z};
        double[] screenPoint = Tools3D.getScreenPoint(point, camera, dimension);

        if (screenPoint != null) {
            double[] cameraPosition = camera.getPosition();
            double screenR = (r / Tools3D.getDistance(cameraPosition[0], point[0], cameraPosition[1], point[1], cameraPosition[2], point[2]));
            double screenSizeX = dimension.getWidth();
            double screenSizeY = dimension.getHeight();
            double min = Math.min(screenSizeX, screenSizeY);
            screenR *=  (min / 2 / camera.scale);
            
            g2d.setColor(new Color(0,0,0));
            g2d.fillOval((int)(screenPoint[0] - screenR), (int)(screenPoint[1] - screenR), (int)(screenR * 2), (int)(screenR * 2));
        }
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
}
