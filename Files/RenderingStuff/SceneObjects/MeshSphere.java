package Files.RenderingStuff.SceneObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.awt.*;

import Files.RenderingStuff.IsKeyPressed;
import Files.RenderingStuff.PanelInfo;
import Files.RenderingStuff.Renderable;
import Files.RenderingStuff.SceneInfo;
import Files.RenderingStuff.SceneObject;
import Files.RenderingStuff.Renderables.Face;
import Files.RenderingStuff.Renderables.Line;
import Files.RenderingStuff.Tools3D;
import tools.a;


public class MeshSphere extends Mesh implements MeshInterface {
    private int precision;

    private static ArrayList<double[]> points = new ArrayList<double[]>();
    private static ArrayList<int[]> lineNumbers = new ArrayList<int[]>();
    private static ArrayList<int[]> faceNumbers = new ArrayList<int[]>();

    private Color faceColor = new Color(100,100,100);
    private Color lineColor = new Color(0,0,0);

    public MeshSphere(double x, double y, double z, double scale, int precision) {
        super(x, y, z, scale, 0, 0);
        setDoRotate(false);
        this.precision = precision;
    }
  
    public void tick(PanelInfo panelInfo, SceneInfo sceneInfo) {        

        super.tick(panelInfo, sceneInfo);
    }

    public void renderTick(PanelInfo panelInfo, SceneInfo sceneInfo) {
        updateLists();
    }

    public void updateLists() {
        points.clear();
        lineNumbers.clear();
        faceNumbers.clear();
        for (int i = 0; i < 2 * precision; i++) {
            for (int j = 0; j < precision; j++) {
                double[] point = Tools3D.calculatePoint(Math.PI / (precision - 1) * i, Math.PI / (precision - 1) * j, 1);
                points.add(point);
            }
        }

        for (int i = 0; i < 2 * precision; i++) {
            for (int j = 0; j < precision; j++) {
                int[] faceNumber1 = {(i * precision + j), (i + 1) * precision + j, i * precision + (j + 1)};
                int[] faceNumber2 = {(i * precision + j), (i - 1) * precision + j, i * precision + (j - 1)};
                faceNumbers.add(faceNumber1);
                faceNumbers.add(faceNumber2);    
            }
        }
        
        for (int i = 0; i < 2 * precision; i++) {
            for (int j = 0; j < precision - 1; j++) {
                int[] lineNumber1 = {(i * precision + j), (i + 1) * precision + j};
                int[] lineNumber2 = {(i * precision + j), i * precision + (j + 1)};
                lineNumbers.add(lineNumber1);
                lineNumbers.add(lineNumber2);    
            }
        }

    }

    public String toString() {
        return ("MeshSphere: " + getX() + ", " + getY() + ", " + getZ() + "; scale: " + getScale() + "; rotation: " + getTheta() + " " + getPhi());
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
