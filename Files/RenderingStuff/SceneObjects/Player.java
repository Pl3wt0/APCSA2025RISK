package Files.RenderingStuff.SceneObjects;

import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Files.RenderingStuff.*;
import tools.a;

public class Player extends SceneElement implements SceneObject, KeyListener {
    private double x;
    private double y;
    private double z;

    private double xVelocity;
    private double yVelocity;
    private double zVelocity;
    private double theta;
    private double phi;

    private double speed = 200;

    private Camera camera;

    public Player(SceneInfo sceneInfo) {
        super(sceneInfo);
        x = 0;
        y = 0;
        z = 0;
        xVelocity = 0;
        yVelocity = 0;
        zVelocity = 0;
        panelInfo.addKeyListener(this);
    }

    public void setValues(double x, double y, double z, double theta, double phi) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.theta = theta;
        this.phi = phi;
    }


    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public double[] getPosition() {
        double[] point = {x,y,z};
        return point;
    }

    public ArrayList<Renderable> getRenderables() {
        return new ArrayList<Renderable>();
    }

    public void tick() {
        int fps = panelInfo.getFps();

        if (IsKeyPressed.isWPressed()) {
            accelerateRelative1(speed / fps,0,0);
        }
        if (IsKeyPressed.isAPressed()) {
            accelerateRelative1(0,speed / fps,0);
        }
        if (IsKeyPressed.isSPressed()) {
            accelerateRelative1(-speed / fps,0,0);
        }
        if (IsKeyPressed.isDPressed()) {
            accelerateRelative1(0,-speed / fps,0);
        }

        zVelocity -= 100.0 / fps;

        double[] rotation = camera.getRotation();
        setRotation(rotation[0], rotation[1]);


        x += (xVelocity / fps);
        y += (yVelocity / fps);
        z += (zVelocity / fps);

    }

    private void rectifyAngles() {
        theta %= Math.PI * 2;
        if (phi != Math.PI) {
            phi %= Math.PI;
        }
    }

    private void moveRelative(double x, double y, double z) {
        this.x += Math.cos(theta) * x + Math.sin(theta) * y;
        this.y += Math.cos(theta + Math.PI * 3 / 2) * x + Math.sin(theta + Math.PI * 3 / 2) * y;
        this.z += z;
    }

    private void accelerateRelative1(double x, double y, double z) {
        this.xVelocity += Math.cos(theta) * x + Math.sin(theta) * y;
        this.yVelocity += Math.cos(theta + Math.PI * 3 / 2) * x + Math.sin(theta + Math.PI * 3 / 2) * y;
        this.zVelocity += z;
    }

    private void accelerateRelative2(double x, double y, double z) {
        double[] v1 = {Math.sin(phi) * Math.cos(theta), Math.sin(phi) * Math.sin(theta), Math.cos(phi)};
        double[] v2 = {-Math.sin(theta),Math.cos(theta),0};
        double[] v3 = Tools3D.crossProduct(v1, v2);

        this.xVelocity += v1[0] * x + v2[0] * y + v3[0] * z;
        this.yVelocity += v1[1] * x + v2[1] * y + v3[1] * z;
        this.zVelocity += v1[2] * x + v2[2] * y + v3[2] * z;
    }

    public void rotate(double theta, double phi) {
        this.theta += theta;
        if (this.phi + phi > Math.PI) {
            this.phi = Math.PI;
        } else if (this.phi + phi < 0) {
            this.phi = 0;
        } else {
            this.phi += phi;
        }
        rectifyAngles();
    }

    public void setRotation(double theta, double phi) {
        this.theta = theta;
        this.phi = phi;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == ' ') {
            zVelocity = 100;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

}
