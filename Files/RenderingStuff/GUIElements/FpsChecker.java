package Files.RenderingStuff.GUIElements;

import java.util.ArrayList;

import Files.RenderingStuff.PanelInfo;
import Files.RenderingStuff.SceneInfo;


public class FpsChecker extends TextButton {
    private String tpsText;
    private ArrayList<Double> last5Values = new ArrayList<Double>();     

    public FpsChecker(SceneInfo sceneInfo, double[] location, double xWidth, double yWidth, double[] hitBox, int fontSize) {
        super(sceneInfo, location, xWidth, yWidth, hitBox, "", fontSize);
        this.text = tpsText;
    }

    
}
