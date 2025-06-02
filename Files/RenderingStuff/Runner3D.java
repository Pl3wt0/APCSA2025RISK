package Files.RenderingStuff;
import tools.a;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.image.BufferedImage;


public class Runner3D {

    public static void main(String[] args) {
        new Runner3D();
    }

    public Runner3D() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Runner3D");

                Panel3D panel = new Panel3D(60, 1);
                frame.add(panel);

                frame.addWindowFocusListener(new WindowFocusListener() {
                    @Override
                    public void windowGainedFocus(WindowEvent e) {
                        panel.gainFocus();
                    }
                    
                    @Override
                    public void windowLostFocus(WindowEvent e) {
                        panel.loseFocus();
                    }
                });            

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                panel.setUp();
                panel.setFocusable(true);
                frame.setVisible(true);
                //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
    }
}