package Files.RenderingStuff.GUIElements;

import Files.RenderingStuff.*;
import tools.a;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.events.MouseEvent;

public class CustomButton implements GUIElement, MouseListener {
    protected double[] location;
    protected double xWidth;
    protected double yWidth;
    protected BufferedImage image;
    protected double[] hitBox;
    protected PanelInfo panelInfo;
    protected SceneInfo sceneInfo;
    protected Object returnValue;

    public CustomButton(double[] location, double xWidth, double yWidth, double[] hitBox, 
            PanelInfo panelInfo, SceneInfo sceneInfo) {
        this.location = location;
        this.xWidth = xWidth;
        this.yWidth = yWidth;
        this.panelInfo = panelInfo;
        this.sceneInfo = sceneInfo;
        this.hitBox = hitBox;
        if (hitBox != null) {
            panelInfo.addMouseListener(this);
        }
    }

    public void render(Graphics2D g2d, PanelInfo panelInfo, SceneInfo sceneInfo) {
        double width = panelInfo.getDimension().getWidth();
        double height = panelInfo.getDimension().getHeight();
        g2d.drawImage(image, (int) (location[0] * width), (int) (location[1] * height), (int) (xWidth * width),
                (int) (yWidth * height), null);
    }

    public void tick(PanelInfo panelInfo, SceneInfo sceneInfo) {

    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] point) {
        location = point;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        double width = panelInfo.getDimension().getWidth();
        double height = panelInfo.getDimension().getHeight();
        /*
         * if (e.getX() > hitBox[0] * width && e.getX() < hitBox[2] * width && e.getY()
         * > hitBox[1] * height
         * && e.getY() < hitBox[3] * height) {
         * InteractionHandler.doSomething();
         * }
         */
        if (e.getX() > location[0] * width && e.getX() < (location[0] + xWidth) * width && e.getY() > location[1] * height
                && e.getY() < (location[1] + yWidth) * height) {
            whenClicked(e);
        }

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {

    }

    public void whenClicked(java.awt.event.MouseEvent e) {

    }

    public void remove() {
        panelInfo.removeMouseListener(this);
        sceneInfo.getGuiElements().remove(this);
    }
}
