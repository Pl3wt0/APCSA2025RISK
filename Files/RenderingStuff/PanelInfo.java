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
import java.util.ArrayList;
import java.util.Arrays;

public class PanelInfo {
    private Panel3D panel;

    public PanelInfo(Panel3D panel) {
        this.panel = panel;
    }

    public int getFps() {
        return panel.fps;
    }

    public Dimension getDimension() {
        return panel.getDimension();
    }

    public int getTick() {
        return panel.tick;
    }

    public ArrayList<MouseListener> getMouseListeners() {
        return new ArrayList<>(Arrays.asList(panel.getMouseListeners()));
    }

    public void addMouseListener(MouseListener mouseListener) {
        panel.addMouseListener(mouseListener);
    }

    public void removeMouseListener(MouseListener mouseListener) {
        panel.removeMouseListener(mouseListener);
    }

    public ArrayList<KeyListener> getKeyListeners() {
        return new ArrayList<>(Arrays.asList(panel.getKeyListeners()));
    }

    public void addKeyListener(KeyListener keyListener) {
        panel.addKeyListener(keyListener);
    }

    public void removeKeyListener(KeyListener keyListener) {
        panel.removeKeyListener(keyListener);
    }

    public boolean dimensionChanged() {
        return panel.dimensionChanged();
    }

    public void setMouseControlled(boolean value) {
        panel.setMouseControlled(value);
    }
}
