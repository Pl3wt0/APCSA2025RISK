package Cube.SceneObjects;

import Cube.SceneObject;
import tools.a;

public class Vector3D extends Mesh implements MeshInterface {    
    private static int[][] lineNumbers = {{0,1}};
    private static int[][] faceNumbers = {};

    private double[] vector;
    private double vectorScale;
    private double headSize;
    
    public Vector3D(double x, double y, double z, double x2, double y2, double z2, double vectorScale, double headSize) {
        super(x, y, z, 1, 0, 0);
        double[] vector = {x2, y2, z2};
        this.vector = vector;
        this.vectorScale = vectorScale;
        this.headSize = headSize;
        setDoRotate(false);
    }
    
    public String toString() {
        return ("Cube2: " + getX() + ", " + getY() + ", " + getZ() + "; scale: " + getScale() + "; rotation: " + getTheta() + " " + getPhi());
    }

    public int[][] getLineNumbers() {
        return lineNumbers;
    }
    public int[][] getFaceNumbers() {
        return faceNumbers;
    }
    public double[][] getPoints() {
        double[] point1 = {0, 0, 0};
        double[] point2 = {vector[0] * vectorScale, vector[1] * vectorScale, vector[2] * vectorScale};
        double[][] points = {point1, point2};
        return points;
    }

    public void setValues(double x, double y, double z, double[] vector) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vector = vector;
    }


}
