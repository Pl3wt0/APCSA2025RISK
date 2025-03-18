package Cube.SceneObjects;

import Cube.SceneObject;

public class Cube extends Mesh implements MeshInterface {
    private static double[][] points = {{-1,-1,-1},{-1,-1,1},{-1,1,-1},{-1,1,1},{1,-1,-1},{1,-1,1},{1,1,-1},{1,1,1}};
    private static int[][] lineNumbers = {{0,1},{0,2},{0,4},{1,3},{2,3},{1,5},{2,6},{3,7},{4,5},{4,6},{5,7},{6,7}};
    private static int[][] faceNumbers = {{0,1,3,2},{0,2,6,4},{0,1,5,4},{1,3,7,5},{2,3,7,6},{4,5,7,6}};
    public Cube(double x, double y, double z, double scale, double theta, double phi) {
        super(x, y, z, scale, theta, phi);
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
        return points;
    }


}
