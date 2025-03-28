package Files.RenderingStuff.Renderables;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import Files.RenderingStuff.Camera;
import Files.RenderingStuff.IsKeyPressed;
import Files.RenderingStuff.PanelInfo;
import Files.RenderingStuff.Renderable;
import Files.RenderingStuff.SceneInfo;
import Files.RenderingStuff.SceneObject;
import Files.RenderingStuff.Tools3D;
import Files.RenderingStuff.SceneObjects.*;
import tools.a;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image3D implements Renderable {

    private double[] location;
    private double scale;
    private BufferedImage image;


    public Image3D(double[] location, double scale, String fileLocation) {
        this.location = location;
        this.scale = scale;
        try {                
            image = ImageIO.read(new File(fileLocation));
        } catch (IOException ex) {
            a.pr("Particle IO exception: ");
            a.prl(ex);
        }
    }

    public double[] getPosition() {
        return location;
    }

    public void render(Graphics2D g2d, PanelInfo panelInfo, SceneInfo sceneInfo) {
            Camera camera = sceneInfo.getCamera();
            Dimension dimension = panelInfo.getDimension();

            int width = image.getWidth();
            int height = image.getHeight();
            BufferedImage distortedImage = new BufferedImage((int)panelInfo.getDimension().getWidth(), (int)panelInfo.getDimension().getHeight(), BufferedImage.TYPE_INT_ARGB);

            for (int x = 0; x < (int)panelInfo.getDimension().getWidth(); x++) {
                for (int y = 0; y < (int)panelInfo.getDimension().getHeight(); y++) {
                    double[] oldFloorPoint = Tools3D.getFloorPoint(camera, x, y, dimension, 0);
                    if (oldFloorPoint == null) {
                        distortedImage.setRGB(x, y, 0x00000000); // Set alpha to 0 for transparency
                    } else {
                        int[] floorPoint = {-(int)((oldFloorPoint[0]) * width / scale), (int)((oldFloorPoint[1]) * height / scale)};
                        if (floorPoint[0] > 0 && floorPoint[0] < image.getWidth() && floorPoint[1] > 0 && floorPoint[1] < image.getHeight()) {
                            int rbg = image.getRGB(floorPoint[0], floorPoint[1]);
                            distortedImage.setRGB(x, y, rbg);
                        } else {
                            distortedImage.setRGB(x, y, 0x00000000); // Set alpha to 0 for transparency
                        }    
                    }
                }
            }
    
            
            g2d.drawImage(distortedImage, null, null);
        
    }

    public void tick(PanelInfo panelInfo, SceneInfo sceneInfo) {

    }

    public void renderTick(PanelInfo panelInfo, SceneInfo sceneInfo) {
        
    }

    public ArrayList<Renderable> getRenderables() {
        ArrayList<Renderable> self = new ArrayList<Renderable>();
        self.add(this);
        return self;
    }

}