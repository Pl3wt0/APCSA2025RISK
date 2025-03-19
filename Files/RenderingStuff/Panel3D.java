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


public class Panel3D extends JPanel {
    public int tps;
    public int fps;
    public int tick;
    private double tickCount;
    private Scene scene;

    private PanelInfo panelInfo;

    public Panel3D(int tps, int fps, double timeSpeed) {
        this.tps = tps;
        this.fps = fps;

        panelInfo = new PanelInfo(this);
        scene = new Scene(this);

        Timer timer = new Timer((int) ((1.0 / fps) * 1000), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tickCount += (((double) (tps)) / fps);
                while (tickCount >= 1.0) {
                    scene.tickScene(tps, tick);
                    tickCount -= 1.0;
                }
                repaint();
            }
        });
        timer.start();
    }

    public void gainFocus() {
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

        scene.renderTickScene(fps, tick, dimension);

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

}
