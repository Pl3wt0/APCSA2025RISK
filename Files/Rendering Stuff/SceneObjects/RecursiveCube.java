package Cube.SceneObjects;

import java.util.ArrayList;

import Cube.IsKeyPressed;
import Cube.PanelInfo;
import Cube.Renderable;
import Cube.SceneInfo;
import Cube.SceneObject;

public class RecursiveCube implements SceneObject{
    private double x;
    private int y;
    private int z;
    private int scale;
    private int whichCube;

    public RecursiveCube(double x, double y, double z, double scale, int num, ArrayList<SceneObject> sceneObjects, int whichCube) {
        sceneObjects.add(new Cube(x, y, z, scale, 0, 0));
        if (num > 0) {
            for (int i = -1; i <= 1; i +=2) {
                for (int j = -1; j <= 1; j +=2) {
                    for (int k = -1; k <= 1; k +=2) {
                        if ((1 - i) * 4 + (1 - j) * 2 + (1 - k) != whichCube) {
                            sceneObjects.add(new RecursiveCube(x + i * scale * 5.0/4, y + j * scale * 5.0/4, z + k * scale * 5.0/4, scale / 4, num - 1, sceneObjects, i * 4 + j * 2 + k));
                        }
                    }
                }
            }
        }
    }
    public ArrayList<Renderable> getRenderables() {
        ArrayList<Renderable> arr = new ArrayList<Renderable>();
        return arr;
    }
    public void tick(PanelInfo panelInfo, SceneInfo sceneInfo) {

    }
    public void renderTick(PanelInfo panelInfo, SceneInfo sceneInfo) {

    }

}
