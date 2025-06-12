package Files.RenderingStuff.SceneObjects;

import java.util.ArrayList;
import java.util.Arrays;

import javax.tools.Tool;

import java.awt.*;

import Files.RenderingStuff.*;
import Files.RenderingStuff.Renderables.*;
import tools.a;

public abstract class Mesh extends SceneElement implements SceneObject {
    protected double x;
    protected double y;
    protected double z;
    protected double scale;
    protected double[] rotation1;
    protected double[] rotation2;
    protected boolean doRotate = true;

    protected double xVelocity;
    protected double yVelocity;
    protected double zVelocity;

    protected Color lineColor = new Color(0, 0, 0);
    protected Color faceColor = new Color(100, 100, 100);

    public Mesh(SceneInfo sceneInfo, double x, double y, double z, double scale) {
        super(sceneInfo);
        this.x = x;
        this.y = y;
        this.z = z;
        this.scale = scale;
        double[] point1 = {1, 0, 0};
        double[] point2 = {0, 1, 0};
        this.rotation1 = point1;
        this.rotation2 = point2;
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
                    double[] newPoint = Tools3D.adjustPoint(point, scale, rotation1, rotation2, x, y, z);
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
                    renderables.add(new Face(sceneInfo, facePoints, faceColor));    
                } catch (IndexOutOfBoundsException e) {
                    //a.prl("Mesh face IndexOutOfBoundsException");
                }
            }

            for (int[] lineNumber : lineNumbers) {
                try {
/*                     a.prl(lineNumber[0]);
                    a.prl(newPoints[lineNumber[0]]);
                    a.prl(newPoints[lineNumber[1]]);
 */                    renderables.add(new Line(sceneInfo, newPoints[lineNumber[0]], newPoints[lineNumber[1]], lineColor));
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
                    double[] newPoint = Tools3D.adjustPoint(point, scale, rotation1, rotation2, x, y, z);
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
                    faceList.add(new Face(sceneInfo, facePoints, new Color(100, 100, 100)));    
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
                    double[] newPoint = Tools3D.adjustPoint(point, scale, rotation1, rotation2, x, y, z);
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

    public double[] getRotation1() {
        return rotation1;
    }

    public double[] getRotation2() {
        return rotation2;
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

    private double sum = 0;
    public void rotate(double[] rotationVector, double time) {
        double[] v1 = Tools3D.crossProduct(rotationVector, rotation1);
        double[] change1 = Tools3D.scalarMult(v1, time);
        double[] cross1 = Tools3D.crossProduct(Tools3D.crossProduct(rotationVector, rotation1), rotationVector);
        double[] a1 = Tools3D.scalarMult(cross1, -Tools3D.square(v1) / Tools3D.magnitude(cross1));
        //double[] change2 = Tools3D.scalarMult(a1, (0.5 * time * time));
        double [] change2 = {0,0,0};

        sum += Tools3D.magnitude(v1);
        rotation1 = Tools3D.vectorSum(Tools3D.vectorSum(rotation1, change1), change2);


        double[] v2 = Tools3D.crossProduct(rotationVector, rotation2);
        double[] change3 = Tools3D.scalarMult(v2, time);
        double[] cross2 = Tools3D.crossProduct(Tools3D.crossProduct(rotationVector, rotation2), rotationVector);
        double[] a2 = Tools3D.scalarMult(cross2, -Tools3D.square(v2) / Tools3D.magnitude(cross2));
        //double[] change4 = Tools3D.scalarMult(a2, (0.5 * time * time));
        double[] change4 = {0, 0, 0};

        rotation2 = Tools3D.vectorSum(Tools3D.vectorSum(rotation2, change3), change4);

/*         if (Tools3D.dotProduct(rotation1, rotation1) > 0.01) {
            rotation1 = Tools3D.normalize(rotation1);
        }

        if (Tools3D.dotProduct(rotation2, rotation2) > 0.01) {
            rotation2 = Tools3D.normalize(rotation2);
        }
 */
        a.prl(sum);
        a.prl(Tools3D.magnitude(rotation1));
    }

    public void setRotation(double[] rotation1, double[] rotation2) {
        this.rotation1 = rotation1;
        this.rotation2 = rotation2;
    }

    public void setRotation(double theta, double phi) {
        double[] v1 = {Math.sin(phi) * Math.cos(theta), Math.sin(phi) * Math.sin(theta), Math.cos(phi)};
        double[] v2 = {-Math.sin(theta),Math.cos(theta),0};

        rotation1 = v1;
        rotation2 = v2;
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

    public void tick() {
        x += xVelocity * sceneInfo.getLastFrameLength();
        y += yVelocity * sceneInfo.getLastFrameLength();
        z += zVelocity * sceneInfo.getLastFrameLength();
    }



}
