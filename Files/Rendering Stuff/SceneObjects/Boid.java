package Cube.SceneObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.awt.*;

import Cube.IsKeyPressed;
import Cube.PanelInfo;
import Cube.Renderable;
import Cube.SceneInfo;
import Cube.SceneObject;
import Cube.Renderables.Face;
import Cube.Renderables.Line;
import Cube.Tools3D;
import tools.a;

public class Boid extends Mesh implements MeshInterface{

    private double velocity;

    private static double[][] points = {{0,0,3}, {1,1,-1}, {-1,1,-1}, {-1,-1,-1}, {1,-1,-1}};
    private static int[][] lineNumbers = {{0,1},{0,2},{0,3},{0,4},{4,1},{1,2},{2,3},{3,4}};
    private static int[][] faceNumbers = {{0,1,2},{0,2,2},{0,3,4},{0,4,1},{1,2,3,4}};

    private Color faceColor = new Color(100,100,100);
    private Color lineColor = new Color(0,0,0);

    public Boid(double x, double y, double z, double scale, double theta, double phi) {
        super(x, y, z, scale, theta, phi);
    }
    public void tick(PanelInfo panelInfo, SceneInfo sceneInfo) {
        super.tick(panelInfo, sceneInfo);
        int tps = panelInfo.getTps();
        velocity = 40;

/*         double thetaSum = 0.0;
        double phiSum = 0.0;
        double xSum = 0.0;
        double ySum = 0.0;
        double zSum = 0.0;
        int count = 0;
 */        
        ArrayList<Boid> birdList = (ArrayList<Boid>)sceneInfo.getSceneObjects().clone();
        for (SceneObject sceneObject : sceneInfo.getSceneObjects()) {
            if (!(sceneObject instanceof Boid) || sceneObject == this) {
                birdList.remove(sceneObject);
            }
        }
        
        birdList.sort(Comparator.comparing(bird -> this.distanceTo(bird)));

        double[] positionSums = {0,0,0};
        int count = 0;
        for (int i = 0; i < birdList.size(); i++) {
            Boid bird = (Boid)(birdList.get(i));
            positionSums[0] += bird.getX();
            positionSums[1] += bird.getY();
            positionSums[2] += bird.getZ();
            count++;
            if (this.distanceTo(bird) < 2) {
                partialRotate(bird.getX(), bird.getY(), bird.getZ(), -0.01);
            }
            partialRotate(bird.getTheta(), bird.getPhi(), 0.01 / (count + 2));
        }
        partialRotate(positionSums[0] / count, positionSums[1] / count, positionSums[2] / count, 0.001);
        partialRotate(0,0,0, distanceTo(0,0,0) * 0.00001);
        setRotation(getTheta() % (Math.PI * 2), (getPhi() % (Math.PI * 2)));
        if (getPhi() > Math.PI) {
            setRotation(getTheta() - Math.PI, Math.PI * 2 - getPhi());
        }
        setRotation(getTheta() % (Math.PI * 2), (getPhi() % (Math.PI * 2)));

        double phi = getPhi();
        double theta = getTheta();
        move(velocity / tps * (Math.sin(phi) * Math.cos(theta)), velocity / tps * (Math.sin(phi) * Math.sin(theta)), velocity / tps * (Math.cos(phi)));
    }

    public void partialRotate(double x, double y, double z, double amount) {
        double thisX = getX();
        double thisY = getY();
        double thisZ = getZ();
        double thetaDirection = Tools3D.calculateTheta(x - thisX, y - thisY, z - thisZ);
        double phiDirection = Tools3D.calculatePhi(x - thisX, y - thisY, z - thisZ);
        double thetaChange = (((thetaDirection - (getTheta() % (Math.PI * 2))) + Math.PI) % (Math.PI * 2)) - Math.PI;
        double phiChange = phiDirection - (getPhi() % (Math.PI * 2));

        rotate(thetaChange * amount, phiChange * amount * 2);
    }

    public void partialRotate(double thetaDirection, double phiDirection, double amount) {
        double thetaChange = (((thetaDirection - (getTheta() % (Math.PI * 2))) + Math.PI) % (Math.PI * 2)) - Math.PI;
        double phiChange = (((phiDirection - (getPhi() % (Math.PI * 2))) + Math.PI) % (Math.PI * 2)) - Math.PI;

        rotate(thetaChange * amount, phiChange * amount * 2);
    }

    public String toString() {
        return ("Bird: " + getX() + ", " + getY() + ", " + getZ() + "; scale: " + getScale() + "; rotation: " + getTheta() + " " + getPhi());
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
