package Files.RenderingStuff.Renderables;

import java.util.ArrayList;

import Files.RenderingStuff.Camera;
import Files.RenderingStuff.IsKeyPressed;
import Files.RenderingStuff.PanelInfo;
import Files.RenderingStuff.Renderable;
import Files.RenderingStuff.SceneElement;
import Files.RenderingStuff.SceneInfo;
import Files.RenderingStuff.SceneObject;
import Files.RenderingStuff.Tools3D;
import Files.RenderingStuff.SceneObjects.*;
import tools.a;

import java.awt.*;

public class Face extends SceneElement implements Renderable {

    private ArrayList<double[]> points;
    private int numSides;
    private Color color;

    private Vector3D normalVector;

    public Face(SceneInfo sceneInfo, ArrayList<double[]> points, Color color) {
        super(sceneInfo);
        this.points = points;
        this.numSides = points.size();
        this.color = color;
    }

    public double[] getPosition() {
        double sumX = 0;
        double sumY = 0;
        double sumZ = 0;
        double n = 0;

        for (double[] point : points) {
            sumX += point[0];
            sumY += point[1];
            sumZ += point[2];
            n++;
        }
        double[] point = { sumX / n, sumY / n, sumZ / n };
        return point;
    }

    public void render(Graphics2D g2d) {
        if (normalVector == null) {
            double[] doubleNormalVector = Tools3D.normalize(getNormalVector());
            double[] center = new double[3];
            for (double[] point : points) {
                center[0] += point[0];
                center[1] += point[1];
                center[2] += point[2];
            }
            center[0] /= points.size();
            center[1] /= points.size();
            center[2] /= points.size();
            normalVector = new Vector3D(sceneInfo, center[0], center[1], center[2], doubleNormalVector[0],
                    doubleNormalVector[1], doubleNormalVector[2], 1, 0.1);
        }

        Camera camera = sceneInfo.getCamera();
        Dimension dimension = panelInfo.getDimension();

        g2d.setColor(color);
        int[] xPoints = new int[numSides];
        int[] yPoints = new int[numSides];
        boolean onScreen = true;

        for (int i = 0; i < numSides; i++) {
            double[] point = points.get(i);
            double[] screenPoint = Tools3D.getScreenPoint(point, camera, dimension);
            if (screenPoint == Tools3D.nullScreenPoint()) {
                onScreen = false;
                break;
            } else {
                xPoints[i] = (int) screenPoint[0];
                yPoints[i] = (int) screenPoint[1];
            }
        }
        if (onScreen) {
            Polygon p = new Polygon(xPoints, yPoints, numSides);
            g2d.fillPolygon(p);

        }

    }

    public void tick() {

    }

    public ArrayList<Renderable> getRenderables() {
        ArrayList<Renderable> self = new ArrayList<Renderable>();
        self.add(this);
        return self;
    }

    public double[] getNormalVector() {
        double[] v1 = { points.get(0)[0] - points.get(1)[0], points.get(0)[1] - points.get(1)[1],
                points.get(0)[2] - points.get(1)[2] };
        double[] v2 = { points.get(0)[0] - points.get(2)[0], points.get(0)[1] - points.get(2)[1],
                points.get(0)[2] - points.get(2)[2] };
        return Tools3D.crossProduct(v1, v2);
    }

    public boolean whichSide(double[] p1) {
        double[] center = new double[3];
        for (double[] point : points) {
            center[0] += point[0];
            center[1] += point[1];
            center[2] += point[2];
        }
        center[0] /= points.size();
        center[1] /= points.size();
        center[2] /= points.size();

        return (Tools3D.dotProduct(Tools3D.vectorDifference(p1, center), getNormalVector()) >= 0);
    }
}