package Files.RenderingStuff.Renderables;
import java.awt.color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Files.RenderingStuff.IsKeyPressed;
import Files.RenderingStuff.PanelInfo;
import Files.RenderingStuff.Renderable;
import Files.RenderingStuff.SceneElement;
import Files.RenderingStuff.SceneInfo;
import Files.RenderingStuff.SceneObject;
import Files.RenderingStuff.Renderables.Face;
import Files.RenderingStuff.Renderables.Line;
import Files.RenderingStuff.Tools3D;
import Files.RenderingStuff.Camera;

import java.util.*;
import tools.a;
import java.awt.*;

public class Particle extends SceneElement implements Renderable {
    private double x;
    private double y;
    private double z;
    private double width;
    private double height;

    private Image image;
    
    public Particle(SceneInfo sceneInfo, double x, double y, double z, double width, double height, String location) {
        super(sceneInfo);
        try {                
            image = ImageIO.read(new File(location));
        } catch (IOException ex) {
            a.pr("Particle IO exception: ");
            a.prl(ex);
        }
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
    }

    public double[] getPosition() {
        double[] point = {x,y,z};
        return point;
    }
    
    public void render(Graphics2D g2d) {
        Camera camera = sceneInfo.getCamera();
        Dimension dimension = panelInfo.getDimension();
        double[] point = {x,y,z};
        double[] screenPoint = Tools3D.getScreenPoint(point, camera, dimension);
        double distance = Tools3D.getDistance(this, camera);
        double newWidth = width / distance * dimension.getHeight();
        double newHeight = height / distance * dimension.getHeight();
        if (screenPoint != null) {
            g2d.drawImage(image, (int)(screenPoint[0] - newWidth / 2), (int)(screenPoint[1] - newHeight / 2), (int)(screenPoint[0] + newWidth), (int)(screenPoint[1] + newHeight), 0, 0, image.getWidth(null), image.getHeight(null), null);
        }
    }

    public ArrayList<Renderable> getRenderables() {
        ArrayList<Renderable> self = new ArrayList<Renderable>();
        self.add(this);
        return self;
    }

    public void tick() {

    }


}
