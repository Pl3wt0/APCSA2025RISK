package Files.RenderingStuff.SceneObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;

import Files.RenderingStuff.*;
import Files.RenderingStuff.Renderables.*;
import tools.a;

public abstract class Mesh implements SceneObject {
    protected double x;
    protected double y;
    protected double z;
    protected double scale;
    protected double theta;
    protected double phi;
    protected boolean doRotate = true;

    protected double xVelocity;
    protected double yVelocity;
    protected double zVelocity;

    protected Color lineColor = new Color(0, 0, 0);
    protected Color faceColor = new Color(100, 100, 100);

    public Mesh(double x, double y, double z, double scale, double theta, double phi) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.scale = scale;
        this.theta = theta;
        this.phi = phi;
    }

    public ArrayList<Renderable> getRenderables() {
        if (this instanceof MeshInterface) {
            MeshInterface thisObject = (MeshInterface)(this);
            int[][] faceNumbers = thisObject.getFaceNumbers();
            int[][] lineNumbers = thisObject.getLineNumbers();
            double[][] points = thisObject.getPoints();

            ArrayList<Renderable> renderables = new ArrayList<Renderable>();

            double[][] newPoints = new double[points.length][3];
            if (doRotate) {
                for (int i = 0; i < points.length; i++) {
                    double[] point = points[i];
                    double[] newPoint = Tools3D.adjustPoint(point, scale, theta, phi, x, y, z);
                    newPoints[i][0] = newPoint[0];
                    newPoints[i][1] = newPoint[1];
                    newPoints[i][2] = newPoint[2];
                }    
            } else {
                for (int i = 0; i < points.length; i++) {
                    double[] point = points[i];
                    newPoints[i][0] = point[0] * scale + x;
                    newPoints[i][1] = point[1] * scale + y;
                    newPoints[i][2] = point[2] * scale + z;
                }    
            }

            for (int[] faceNumber : faceNumbers) {
                try {
                    ArrayList<double[]> facePoints = new ArrayList<double[]>();
                    for (int i : faceNumber) {
                        facePoints.add(newPoints[i]);
                    }
                    renderables.add(new Face(facePoints, new Color(100, 100, 100)));    
                } catch (IndexOutOfBoundsException e) {
                    //a.prl("Mesh face IndexOutOfBoundsException");
                }
            }

            for (int[] lineNumber : lineNumbers) {
                try {
/*                     a.prl(lineNumber[0]);
                    a.prl(newPoints[lineNumber[0]]);
                    a.prl(newPoints[lineNumber[1]]);
 */                    renderables.add(new Line(newPoints[lineNumber[0]], newPoints[lineNumber[1]], new Color(0, 0, 0)));
                } catch (IndexOutOfBoundsException e) {
                }
            }
            return renderables;
        } else {
            throw new Error("Class " + this.getClass().getName() + " implements Mesh but not MeshInterface");
        }
    }

    public ArrayList<Face> getFaces() {
        if (this instanceof MeshInterface) {
            MeshInterface thisObject = (MeshInterface)(this);
            int[][] faceNumbers = thisObject.getFaceNumbers();
            double[][] points = thisObject.getPoints();

            ArrayList<Face> faceList = new ArrayList<Face>();

            double[][] newPoints = new double[points.length][3];
            if (doRotate) {
                for (int i = 0; i < points.length; i++) {
                    double[] point = points[i];
                    double[] newPoint = Tools3D.adjustPoint(point, scale, theta, phi, x, y, z);
                    newPoints[i][0] = newPoint[0];
                    newPoints[i][1] = newPoint[1];
                    newPoints[i][2] = newPoint[2];
                }    
            } else {
                for (int i = 0; i < points.length; i++) {
                    double[] point = points[i];
                    newPoints[i][0] = point[0] * scale + x;
                    newPoints[i][1] = point[1] * scale + y;
                    newPoints[i][2] = point[2] * scale + z;
                }    
            }

            for (int[] faceNumber : faceNumbers) {
                try {
                    ArrayList<double[]> facePoints = new ArrayList<double[]>();
                    for (int i : faceNumber) {
                        facePoints.add(newPoints[i]);
                    }
                    faceList.add(new Face(facePoints, new Color(100, 100, 100)));    
                } catch (IndexOutOfBoundsException e) {
                    //a.prl("Mesh face IndexOutOfBoundsException");
                }
            }

            return faceList;
        } else {
            throw new Error("Class " + this.getClass().getName() + " implements Mesh but not MeshInterface");
        }

    }

    public ArrayList<double[]> getNewPoints() {
        if (this instanceof MeshInterface) {
            MeshInterface thisObject = (MeshInterface)(this);
            int[][] faceNumbers = thisObject.getFaceNumbers();
            double[][] points = thisObject.getPoints();

            ArrayList<Face> faceList = new ArrayList<Face>();

            double[][] newPoints = new double[points.length][3];
            if (doRotate) {
                for (int i = 0; i < points.length; i++) {
                    double[] point = points[i];
                    double[] newPoint = Tools3D.adjustPoint(point, scale, theta, phi, x, y, z);
                    newPoints[i][0] = newPoint[0];
                    newPoints[i][1] = newPoint[1];
                    newPoints[i][2] = newPoint[2];
                }    
            } else {
                for (int i = 0; i < points.length; i++) {
                    double[] point = points[i];
                    newPoints[i][0] = point[0] * scale + x;
                    newPoints[i][1] = point[1] * scale + y;
                    newPoints[i][2] = point[2] * scale + z;
                }    
            }
            ArrayList<double[]> returnList = new ArrayList<double[]>(Arrays.asList(newPoints));
            return returnList;
        } else {
            throw new Error("Class " + this.getClass().getName() + " implements Mesh but not MeshInterface");
        }
    }

    public double[] getPosition() {
        double[] position = {x,y,z};
        return position;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getScale() {
        return scale;
    }

    public double getTheta() {
        return theta;
    }

    public double getPhi() {
        return phi;
    }

    public boolean getDoRotate() {
        return doRotate;
    }

    public void setDoRotate(boolean value) {
        doRotate = value;
    }

    public void move(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void setPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void rotate(double theta, double phi) {
        this.theta += theta;
        this.phi += phi;
        rectifyAngles();
    }

    public void setRotation(double theta, double phi) {
        this.theta = theta;
        this.phi = phi;
        rectifyAngles();
    }

    public void setVelocities(double xVelocity, double yVelocity, double zVelocity) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.zVelocity = zVelocity;
    }

    public double distanceTo(Mesh other) {
        double[] point1 = this.getPosition();
        double[] point2 = other.getPosition();
        return Math.sqrt((point1[0] - point2[0]) * (point1[0] - point2[0]) + (point1[1] - point2[1]) * (point1[1] - point2[1]) + (point1[2] - point2[2]) * (point1[2] - point2[2]));
    }

    public double distanceTo(double x, double y, double z) {
        double[] point1 = this.getPosition();
        double[] point2 = {x,y,z};
        return Math.sqrt((point1[0] - point2[0]) * (point1[0] - point2[0]) + (point1[1] - point2[1]) * (point1[1] - point2[1]) + (point1[2] - point2[2]) * (point1[2] - point2[2]));
    }

    private void rectifyAngles() {
        theta %= Math.PI * 2;
        phi %= Math.PI * 2;
    }

    public void tick(PanelInfo panelInfo, SceneInfo sceneInfo) {
        x += xVelocity * sceneInfo.getLastFrameLength();
        y += yVelocity * sceneInfo.getLastFrameLength();
        z += zVelocity * sceneInfo.getLastFrameLength();

        rectifyAngles();
    }

    public void renderTick(PanelInfo panelInfo, SceneInfo sceneInfo) {

    }


}
