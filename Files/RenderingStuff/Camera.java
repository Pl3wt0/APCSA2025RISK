package Files.RenderingStuff;

import java.util.ArrayList;

import Files.RenderingStuff.SceneObjects.*;
import Files.RenderingStuff.SceneObjects.Player;
import tools.a;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;


public class Camera extends Robot {
    public double x;
    public double y;
    public double z;
    public double theta;
    public double phi;
    public double scale;
    public double speed = 100;
    public double rotateSpeed = 0.5;

    public double[][] directionMatrix;
    public double[][] invertedMatrix;

    public Dimension dimension;

    private Player player;

    private boolean active = true;

    public Camera(double x, double y, double z, double theta, double phi, double scale) throws AWTException {
        this.x = x;
        this.y = y;
        this.z = z;
        this.theta = theta;
        this.phi = phi;
        this.scale = scale;
        calculateMatrix();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            if (active) {
                                setInactive();
                            } else {
                                setActive();
                            }
                        }
                }
                return false;
            }
        });
    }

    public Camera() throws AWTException{
        x = 0;
        y = 0;
        z = 0;
        theta = 0;
        phi = Math.PI / 2;
        scale = 1;
        calculateMatrix();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            if (active) {
                                setInactive();
                            } else {
                                setActive();
                            }
                        }
                }
                return false;
            }
        });
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setValues(double x, double y, double z, double theta, double phi, double scale) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.theta = theta;
        this.phi = phi;
        this.scale = scale;
        calculateMatrix();
    }

    private void move(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    private void goTo(double[] point) {
        this.x = point[0];
        this.y = point[1];
        this.z = point[2];
    }

    private void rectifyAngles() {
        theta %= Math.PI * 2;
        if (phi != Math.PI) {
            phi %= Math.PI;
        }
    }

    public void tick(int tps, int tick,  ArrayList<SceneObject> sceneObjects, IsKeyPressed isKeyPressed) {
        
    }

    public void renderTick(int fps, int tick, ArrayList<SceneObject> sceneObjects, IsKeyPressed isKeyPressed, Dimension dimension) {
        this.dimension = dimension;        

        if (player != null) {
            goTo(player.getPosition());
        } else {
            if (IsKeyPressed.isWPressed()) {
                moveRelative(speed / fps,0,0);
            }
            if (IsKeyPressed.isAPressed()) {
                moveRelative(0,-speed / fps,0);
            }
            if (IsKeyPressed.isSPressed()) {
                moveRelative(-speed / fps,0,0);
            }
            if (IsKeyPressed.isDPressed()) {
                moveRelative(0,speed / fps,0);
            }
            if (IsKeyPressed.isSpacePressed()) {
                moveRelative(0,0,speed / fps);
            }
            if (IsKeyPressed.isShiftPressed()) {
                moveRelative(0,0,-speed / fps);
            }    
        }

        if (active) {
            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            rotate(-(mouseLocation.getX() - (int)dimension.getWidth() / 2) / 100 * rotateSpeed, (mouseLocation.getY() - (int)dimension.getHeight() / 2) / 100 * rotateSpeed);
    
            mouseMove((int)(dimension.getWidth() / 2), (int)(dimension.getHeight() / 2));    
        }
        calculateMatrix();
    }

    public ArrayList<Renderable> getRenderables() {
        return null;
    }

    private void moveRelative(double x, double y, double z) {
        this.x += Math.cos(theta) * x + Math.sin(theta) * y;
        this.y += Math.cos(theta + Math.PI * 3 / 2) * x + Math.sin(theta + Math.PI * 3 / 2) * y;
        this.z += z;
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

    public void calculateMatrix() {
        double[] v1 = {Math.sin(phi) * Math.cos(theta), Math.sin(phi) * Math.sin(theta), Math.cos(phi)};
        double[] v2 = {-Math.sin(theta),Math.cos(theta),0};
        double[] v3 = Tools3D.crossProduct(v1, v2);
        v3[0] *= -1;
        v3[1] *= -1;
        v3[2] *= -1;

        double[][] cameraMatrix = {v1,v2,v3};
        directionMatrix = cameraMatrix;
        invertedMatrix = Tools3D.invertMatrix(cameraMatrix, 3);
    }

    public double[][] getInvertedMatrix() {
        return invertedMatrix;
    }

    public double[][] getDirectionMatrix() {
        return directionMatrix;
    }

    public double[] getPosition() {
        double[] point = { x, y, z };
        return point;
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

    public double[] getRotation() {
        double[] rotation = {theta, phi};
        return rotation;
    }

    public void setActive() {
        mouseMove((int)(dimension.getWidth() / 2), (int)(dimension.getHeight() / 2));    
        active = true;
    }

    public void setInactive() {
        active = false;
    }

    public String toString() {
        return (x + ", " + y + ", " + z + "; " + theta + ", " + phi);
    }

}
