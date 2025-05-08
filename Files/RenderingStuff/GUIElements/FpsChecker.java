package Files.RenderingStuff.GUIElements;

import java.util.ArrayList;

import Files.RenderingStuff.PanelInfo;
import Files.RenderingStuff.SceneInfo;


public class FpsChecker extends TextButton {
    private String tpsText;
    private ArrayList<Double> last5Values = new ArrayList<Double>();     

    public FpsChecker(double[] location, double xWidth, double yWidth, double[] hitBox, int fontSize,
            PanelInfo panelInfo, SceneInfo sceneInfo) {
        super(location, xWidth, yWidth, hitBox, "", fontSize, panelInfo, sceneInfo);
        this.text = tpsText;
    }

    
}
