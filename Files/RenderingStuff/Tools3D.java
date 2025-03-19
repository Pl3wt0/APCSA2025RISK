package Files.RenderingStuff;
import java.awt.*;
import tools.a;

public class Tools3D {
    private static double[] nullScreenPoint;

    public static double[] nullScreenPoint() {
        return nullScreenPoint;
    }

    public static double calculateTheta(double x, double y, double z) {
        double length = Math.sqrt(x * x + y * y);
        if (length == 0) {
            return 0;
        }
        if (y == 0) {
            return Math.acos(x / length) * Math.signum(x);
        } else {
            return Math.acos(x / length) * Math.signum(y);
        }
    }

    public static double calculatePhi(double x, double y, double z) {
        double length = Math.sqrt(x * x + y * y + z * z);
        if (length == 0) {
            return 0;
        }
        return Math.acos(z / length);
    }

    public static double[] calculatePoint(double theta, double phi, double r) {
        double x = r * Math.sin(phi) * Math.cos(theta);
        double y = r * Math.sin(phi) * Math.sin(theta);
        double z = r * Math.cos(phi);

        double[] point = {x,y,z};

        return point;
    }   

    public static double[] adjustPoint(double[] point, double scale, double theta, double phi, double x, double y, double z) {
        double[] newPoint = point.clone();

        double angle;
        if (newPoint[0] == 0.0 || newPoint[0] == -0.0) {
            angle = Math.PI/2;
        } else {
            angle = Math.atan(newPoint[2] / newPoint[0]);
        }


        if (newPoint[0] < 0) {
            angle += Math.PI;
        }
        

        angle -= (phi);

        double r2 = Math.sqrt(newPoint[0] * newPoint[0] + newPoint[2] * newPoint[2]);    

        newPoint[0] = Math.cos(angle) * r2;
        newPoint[2] = Math.sin(angle) * r2;

        double newTheta = Tools3D.calculateTheta(newPoint[0], newPoint[1], newPoint[2]);
        double newPhi = Tools3D.calculatePhi(newPoint[0], newPoint[1], newPoint[2]);
        double r = Math.sqrt(newPoint[0] * newPoint[0] + newPoint[1] * newPoint[1] + newPoint[2] * newPoint[2]);

        newTheta += theta;

        newPoint = Tools3D.calculatePoint(newTheta, newPhi, r);
 
        newPoint[0] = newPoint[0] * scale + x;
        newPoint[1] = newPoint[1] * scale + y;
        newPoint[2] = newPoint[2] * scale + z;



        return newPoint;
    }

    public static double[] getScreenPoint(double[] point, Camera camera, Dimension dimension) {
        double[][] matrix = camera.getInvertedMatrix();

        double[] cameraPosition = camera.getPosition();

        double x = (point[0] - cameraPosition[0]) * matrix[0][0] + (point[1] - cameraPosition[1]) * matrix[1][0] + (point[2] - cameraPosition[2]) * matrix[2][0];
        double y = (point[0] - cameraPosition[0]) * matrix[0][1] + (point[1] - cameraPosition[1]) * matrix[1][1] + (point[2] - cameraPosition[2]) * matrix[2][1];
        double z = (point[0] - cameraPosition[0]) * matrix[0][2] + (point[1] - cameraPosition[1]) * matrix[1][2] + (point[2] - cameraPosition[2]) * matrix[2][2];

        if (x > 0) {
            double screenX = (-y / x);
            double screenY = (z / x);
    
            double screenSizeX = dimension.getWidth();
            double screenSizeY = dimension.getHeight();
            double min = Math.min(screenSizeX, screenSizeY);

            double[] screenPoint = {screenX * (min / 2 / camera.scale) + (screenSizeX / 2), screenY * (min / 2 / camera.scale) + (screenSizeY / 2)};
            return screenPoint;
        } else {
            return nullScreenPoint;
        }
    }

    public static double[] normalize(double[] vector) {
        double[] v = vector.clone();
        double sum = 0;
        for (int i = 0; i < v.length; i++) {
            sum += v[i] * v[i];
        }
        sum = Math.sqrt(sum);
        for (int i = 0; i < v.length; i++) {
            v[i] /= sum;
        }
        return v;
    }

    public static double[] crossProduct(double[] v1, double[] v2) {
        if (v1.length != 3 || v2.length != 3) {
            throw new Error("cross product size missmatch");
        }
        double[] returnArray = {v1[1] * v2[2] - v1[2] * v2[1], v1[2] * v2[0] - v1[0] * v2[2],v1[0] * v2[1] - v1[1] * v2[0]};
        return returnArray;
    }

    public static double dotProduct(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new Error("dot product size missmatch");
        }
        double sum = 0;
        for (int i = 0; i < v1.length; i++) {
            sum += v1[i] * v2[i];
        }
        return sum;
    }

    public static double[] vectorSum(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new Error("vector sum size missmatch");
        }
        double[] returnVector = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            returnVector[i] = v1[i] + v2[i];
        }
        return returnVector;
    }

    public static double[] vectorDifference(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new Error("vector difference size missmatch");
        }
        double[] returnVector = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            returnVector[i] = v1[i] - v2[i];
        }
        return returnVector;
    }
    
    public static void swapRows(double[][] matrix, int row1, int row2) {
        double temp;
        for (int i = 0; i < matrix[0].length; i++) {
            temp = matrix[row1][i];
            matrix[row1][i] = matrix[row2][i];
            matrix[row2][i] = temp;
        }
    }

    public static void toReducedRowEchelon(double[][] matrix) { //adapted (stolen) from wikipedia
        int h = 0;
        int k = 0;

        while (h < matrix.length && k < matrix[0].length) {
            double maxValue = matrix[h][k];
            int maxRow = h;
            for (int i = h + 1; i < matrix.length; i++) {
                if (Math.abs(matrix[i][k]) > Math.abs(maxValue)) {
                    maxValue = matrix[i][k];
                    maxRow = i;
                }
            }
            if (maxValue == 0) {

                k += 1;
            } else {
                swapRows(matrix, h, maxRow);
                for (int i = 0; i < matrix.length; i++) {
                    if (i != h) {
                        double ratio = matrix[i][k] / matrix[h][k];
                        matrix[i][k] = 0;
                        for (int j = k + 1; j < matrix[0].length; j++) {
                            matrix[i][j] -= matrix[h][j] * ratio;
                        }
                    }
                }
                double divideNumber = matrix[h][k];
                for (int i = k; i < matrix[0].length; i++) {
                    matrix[h][i] /= divideNumber;
                }
                h++;
                k++;
            }
        }
    }

    public static double determinant(double[][] m) {
        return m[0][0] * m[1][1]* m[2][2] + m[0][1] * m[1][2] * m[2][0] + m[0][2] * m[1][0] * m[2][1] - m[2][0] * m[1][1]* m[0][2] - m[2][1] * m[1][2] * m[0][0] - m[2][2] * m[1][0] * m[0][1];
    }

    public static double[][] invertMatrix(double[][] matrix, int size) {
        double[][] newMatrix = new double[size][size * 2];

        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
            newMatrix[i][size + i] = 1;
        }

        toReducedRowEchelon(newMatrix);

        double[][] invertedMatrix = new double[size][size];
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size; j++) {
                invertedMatrix[i][j] = newMatrix[i][j + size];
            }
        }

        return invertedMatrix;
    }

 
    public static double getDistance(double x1, double x2, double y1, double y2, double z1, double z2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
    }

    public static double getDistance(Renderable r, Camera camera) {
        double[] renderablePoint = r.getPosition();
        return getDistance(renderablePoint[0], camera.x, renderablePoint[1], camera.y, renderablePoint[2], camera.z);
    }
}
