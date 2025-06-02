package Files.RenderingStuff.GUIElements;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Files.RenderingStuff.SceneInfo;
import tools.a;

public class InputButton extends TextButton implements KeyListener {
    private boolean done = false;
    public InputButton(SceneInfo sceneInfo, double[] location, double xWidth, double yWidth, double[] hitBox, int fontSize) {
        super(sceneInfo, location, xWidth, yWidth, hitBox, "", fontSize);
        panelInfo.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (Character.isDigit(c) && !done) {
            text += c;
            updateImage();
        }
        if (c == '-' && !done) {
            text += '-';
            updateImage();
        }
        if (c == '\n') {
            done = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
    public boolean isDone() {
        return done;
    }

    public String getText() {
        return text;
    }
}
