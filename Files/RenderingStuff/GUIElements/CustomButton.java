package Files.RenderingStuff.GUIElements;

import Files.RenderingStuff.*;
import tools.a;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class CustomButton implements GUIElement{
    private double[] location;
    private double xWidth;
    private double yWidth;
    private BufferedImage image;

    public CustomButton(double[] location, double xWidth, double yWidth, String fileLocation) {
        this.location = location;
        this.xWidth = xWidth;
        this.yWidth = yWidth;
        try {                
            image = ImageIO.read(new File(fileLocation));
        } catch (IOException ex) {
            a.pr("Particle IO exception: ");
            a.prl(ex);
        }
    }

    public void render(Graphics2D g2d, PanelInfo panelInfo, SceneInfo sceneInfo) {
        g2d.drawImage(image, (int)location[0], (int)location[1], (int)xWidth, (int)yWidth, null);
    }
    public void tick(PanelInfo panelInfo, SceneInfo sceneInfo) {

    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] point) {
        location = point;
    }

}
