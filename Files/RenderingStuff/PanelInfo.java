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

public class PanelInfo {
    private Panel3D panel;

    public PanelInfo(Panel3D panel) {
        this.panel = panel;
    }

    public int getFps() {
        return panel.fps;
    }

    public int getTps() {
        return panel.tps;
    }

    public Dimension getDimension() {
        return panel.getDimension();
    }

    public int getTick() {
        return panel.tick;
    }
}
