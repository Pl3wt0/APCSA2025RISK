package Files.RenderingStuff.SceneObjects;

import java.util.ArrayList;

import Files.RenderingStuff.PanelInfo;
import Files.RenderingStuff.Scene;
import Files.RenderingStuff.SceneInfo;
import Files.RenderingStuff.Tools3D;
import tools.*;

public class Graph extends Mesh implements MeshInterface{
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private int xPrecision;
    private int yPrecision;
    private double xSpaceMin;
    private double xSpaceMax;
    private double ySpaceMin;
    private double ySpaceMax;

    private double time = 0;

    private static ArrayList<double[]> points = new ArrayList<double[]>();
    private static ArrayList<int[]> lineNumbers = new ArrayList<int[]>();
    private static ArrayList<int[]> faceNumbers = new ArrayList<int[]>();

    public Graph(double xMin, double xMax, double yMin, double yMax, int xPrecision, int yPrecision, double xSpaceMin, double xSpaceMax, double ySpaceMin, double ySpaceMax) {
        super(0, 0, 0, 1);
        setDoRotate(false);
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.xPrecision = xPrecision;
        this.yPrecision = yPrecision;
        this.xSpaceMin = xSpaceMin;
        this.xSpaceMax = xSpaceMax;
        this.ySpaceMin = ySpaceMin;
        this.ySpaceMax = ySpaceMax;
    }

    public double function(double x, double y, double t) {
        return 0;
    }

    public void updateLists(double time) {
        points.clear();
        lineNumbers.clear();
        faceNumbers.clear();
        
        for (int x = 0; x < xPrecision; x++) {
            for (int y = 0; y < yPrecision; y++) {
                double xSpace = x * (xSpaceMax - xSpaceMin) / xPrecision + xSpaceMin;
                double ySpace = y * (ySpaceMax - ySpaceMin) / yPrecision + ySpaceMin;
                double xInput = x * (xMax - xMin) / xPrecision + xMin;
                double yInput = y * (yMax - yMin) / yPrecision + yMin;
                double[] point = {xSpace, ySpace, function(xInput, yInput, time)};
                points.add(point);

                int num = x * yPrecision + y;

                if (y != yPrecision - 1) {
                    int[] lineNumber1 = {num, num + 1};
                    lineNumbers.add(lineNumber1);
                }

                if (x != xPrecision - 1) {
                    int[] lineNumber2 = {num, num + yPrecision};
                    lineNumbers.add(lineNumber2);
                }

                if (y != yPrecision - 1 && x != xPrecision - 1) {
                    int[] faceNumber1 = {num, num + 1, num + yPrecision};
                    faceNumbers.add(faceNumber1);
                }

                if (y != 0 && x != 0) {
                    int[] faceNumber2 = {num, num - 1, num - yPrecision};
                    faceNumbers.add(faceNumber2);
                }
            }
        }
    }

    public void renderTick(PanelInfo panelInfo, SceneInfo sceneInfo) {
        time += sceneInfo.getLastFrameLength();
        updateLists(time);
    }

    public int[][] getLineNumbers() {
        int[][] returnList = new int[lineNumbers.size()][];
        lineNumbers.toArray(returnList);
        return returnList;
    }
    public int[][] getFaceNumbers() {
        int[][] returnList = new int[faceNumbers.size()][];
        faceNumbers.toArray(returnList);
        return returnList;
    }
    public double[][] getPoints() {
        double[][] returnList = new double[points.size()][];
        points.toArray(returnList);
        return returnList;
    }

}
