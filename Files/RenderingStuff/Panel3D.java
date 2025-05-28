package Files.RenderingStuff;

import javax.swing.JPanel;
import javax.swing.Timer;
import tools.a;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.image.BufferedImage;
import java.util.*;
import tools.a;
import javax.swing.*;


public class Panel3D extends JPanel {
    public int fps;
    public int tick;
    private Scene scene;

    private PanelInfo panelInfo;

    private BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    private Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

    private boolean mouseControlled = false;

    private Dimension lastDimension;
    private boolean dimensionChanged = true;


    public Panel3D(int fps, double timeSpeed) {
        super();
        this.fps = fps;
        lastDimension = getSize();
        

        panelInfo = new PanelInfo(this);

    }
    
    public void setUp() {
        scene = new Scene(this);

        setCursor(null);

        Timer timer = new Timer((int) ((1.0 / fps) * 1000), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!lastDimension.equals(getSize())) {
                    lastDimension = getSize();
                    dimensionChanged = true;
                } else {
                    dimensionChanged = false;
                }
                scene.tickScene(fps, tick);
                repaint();
            }
        });
        timer.start();
    }

    public void gainFocus() {
        //setCursor(blankCursor);
        scene.gainFocus();
    }

    public void loseFocus() {
        scene.loseFocus();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1440, 900);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        Dimension dimension = getSize();

        scene.renderScene(g2d, dimension);

        tick++;

        g2d.dispose();
    }

    public Dimension getDimension() {
        return getSize();
    }

    public PanelInfo getPanelInfo() {
        return panelInfo;
    }

    public boolean dimensionChanged() {
        return dimensionChanged;
    }

    public void setMouseControlled(boolean value) {
        mouseControlled = value;
        if (value) {
            setCursor(blankCursor);
        } else {
            setCursor(null);
        }
    }
}
