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

public class FileButton extends CustomButton {
    public FileButton(SceneInfo sceneInfo, double[] location, double xWidth, double yWidth, double[] hitBox, String fileLocation) {
        super(sceneInfo, location, xWidth, yWidth, hitBox);
        try {
            image = ImageIO.read(new File(fileLocation));
        } catch (Exception ex) {
            a.pr("Button IO exception: ");
            a.prl(ex);
        }
    }
}
