package Cube.SceneObjects;

import Cube.PanelInfo;
import Cube.SceneInfo;
import Cube.Renderables.*;

public class FallingCube extends Cube {
    private Graph graph;

    public FallingCube(double x, double y, double z, double scale, double theta, double phi, Graph graph) {
        super(x, y, z, scale, theta, phi);
        this.graph = graph;
    }

    public void tick(PanelInfo panelInfo, SceneInfo sceneInfo) {
        super.tick(panelInfo, sceneInfo);
        z -= 5;
        
        boolean intersectsAny = false;
        for (Face face : graph.getFaces()) {
            int num = -1;
            for (double[] point : getNewPoints()) {
                if (face.whichSide(point)) {
                    if (num == -1) {
                        num = 1;
                    } else if (num == 0) {
                        intersectsAny = true;
                    }
                } else {
                    if (num == -1) {
                        num = 0;
                    } else if (num == -1) {
                        intersectsAny = true;
                    }
                }
            }
        }

        if (intersectsAny) {
            z = 100;
        }
    }
        
}
