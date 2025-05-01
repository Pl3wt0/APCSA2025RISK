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

public class TextButton extends CustomButton {
    private String text;
    private int fontSize;
    private Color backGroundColor;
    private Color textColor;
    public TextButton(double[] location, double xWidth, double yWidth, double[] hitBox, String text, int fontSize,
            PanelInfo panelInfo, SceneInfo sceneInfo) {
        super(location, xWidth, yWidth, hitBox, panelInfo, sceneInfo);
        this.text = text;
        this.fontSize = fontSize;
        this.backGroundColor = new Color(100, 100, 100);
        this.textColor = new Color(0, 0, 0);
        updateImage(panelInfo);
    }

    @Override
    public void render(Graphics2D g2d, PanelInfo panelInfo, SceneInfo sceneInfo) {
        if (panelInfo.dimensionChanged()) {
            updateImage(panelInfo);
        }

        super.render(g2d, panelInfo, sceneInfo);
        
    }

    protected void updateImage(PanelInfo panelInfo) {
        double width = panelInfo.getDimension().getWidth();
        double height = panelInfo.getDimension().getHeight();
        BufferedImage backGroundImage = new BufferedImage((int)(xWidth * width),(int)(yWidth * height), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = backGroundImage.createGraphics();
        g2d.setColor(backGroundColor);

        double longWidth = (xWidth * width);
        double longHeight = (yWidth * height);

        int x1 = (int)(longWidth * 0.1);
        int x2 = (int)(longWidth * 0.9);
        int y1 = (int)(longHeight * 0.1);
        int y2 = (int)(longHeight * 0.9);
        g2d.fillRect(x1, 0, x2 - x1, y1);
        g2d.fillRect(0, y1, (int)(longWidth), y2 - y1);
        g2d.fillRect(x1, y2, x2 - x1, y1);
        g2d.fillOval(0,0, x1 * 2, y1 * 2);
        g2d.fillOval(0,(int)(longHeight * 0.8), x1 * 2, y1 * 2);
        g2d.fillOval((int)(longWidth * 0.8),0, x1 * 2, y1 * 2);
        g2d.fillOval((int)(longWidth * 0.8),(int)(longHeight * 0.8), x1 * 2, y1 * 2);

        image = new BufferedImage((int)longWidth, (int)longHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d2 = image.createGraphics();

        float alpha = 0.9f;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d2.setComposite(ac);

        g2d2.drawImage(backGroundImage, null, 0, 0);

        float alpha2 = 1f;
        AlphaComposite ac2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha2);
        g2d2.setComposite(ac2);

        g2d2.setColor(textColor);
        g2d2.setFont(new Font("Ariel", Font.PLAIN, fontSize));

        FontMetrics metrics = g2d2.getFontMetrics();
        g2d2.drawString(text, (int)((xWidth * width - metrics.stringWidth(text)) / 2), (int)((yWidth * height - metrics.getHeight()) / 2) + metrics.getAscent());
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
