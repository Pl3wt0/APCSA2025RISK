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

    public static double[] adjustPoint(double[] point, double scale, double[] rotation1, double[] rotation2, double x, double y, double z) {
        double[] rotation3 = crossProduct(rotation1, rotation2);

        double adjustedX = (point[0] * rotation1[0] + point[1] * rotation2[0] + point[2] * rotation3[0]) * scale + x;
        double adjustedY = (point[0] * rotation1[1] + point[1] * rotation2[1] + point[2] * rotation3[1]) * scale + y;
        double adjustedZ = (point[0] * rotation1[2] + point[1] * rotation2[2] + point[2] * rotation3[2]) * scale + z;

        double[] returnPoint = {adjustedX, adjustedY, adjustedZ};
        return returnPoint;
    }

    public static double[] getScreenPoint(double[] point, Camera camera, Dimension dimension) {
        double[][] matrix = camera.getInvertedMatrix();

        double[] relativePoint = vectorDifference(point, camera.getPosition());

        double x = relativePoint[0] * matrix[0][0] + relativePoint[1] * matrix[1][0] + relativePoint[2] * matrix[2][0];
        double y = relativePoint[0] * matrix[0][1] + relativePoint[1] * matrix[1][1] + relativePoint[2] * matrix[2][1];
        double z = relativePoint[0] * matrix[0][2] + relativePoint[1] * matrix[1][2] + relativePoint[2] * matrix[2][2];

        if (x > 0) {
            double screenSizeX = dimension.getWidth();
            double screenSizeY = dimension.getHeight();
            double min = Math.min(screenSizeX, screenSizeY);

            double scale = min / (camera.scale * x * 2);

            double screenX = (-y * scale) + (screenSizeX * 0.5);
            double screenY = (z * scale) + (screenSizeY * 0.5);
    
            double[] screenPoint = {screenX, screenY};
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
    
    public static double magnitude(double[] v) {
        return v[0] * v[0] + v[1] * v[1] + v[2] * v[2];
    }

    public static double[] scalarMult(double[] vector, double scalar) {
        double[] returnVector = vector.clone();
        for (int i = 0; i < returnVector.length; i++) {
            returnVector[i] *= scalar;
        }
        return returnVector;
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

    public static double square(double[] v) {
        return v[0] * v[0] + v[1] * v[1] + v[2] * v[2];
    }

    public static double angleBetween(double[] v1, double[] v2) {
        return Math.acos(Tools3D.dotProduct(v1, v2) / Tools3D.magnitude(v1) / Tools3D.magnitude(v2));
    }
    
    public static void swapRows(double[][] matrix, int row1, int row2) {
        double temp;
        for (int i = 0; i < matrix[0].length; i++) {
            temp = matrix[row1][i];
            matrix[row1][i] = matrix[row2][i];
            matrix[row2][i] = temp;
        }
    }

    public static void toReducedRowEchelon(double[][] matrix) { //algorithm adapted from wikipedia
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

    public static double[] getFloorPoint(Camera camera, int screenX, int screenY, Dimension dimension, double z) {
        double[][] invertedMatrix = camera.getInvertedMatrix();
        z = camera.getZ() - z;

        double screenSizeX = dimension.getWidth();
        double screenSizeY = dimension.getHeight();
        double min = Math.min(screenSizeX, screenSizeY);

        double sX = (screenX - screenSizeX / 2) * 2 * camera.scale / min;
        double sY = (screenY - screenSizeY / 2) * 2 * camera.scale / min;

        double[][] matrix1 = {{1, -invertedMatrix[0][0], -invertedMatrix[1][0]},
                            {-sX, -invertedMatrix[0][1], -invertedMatrix[1][1]},
                            {sY, -invertedMatrix[0][2], -invertedMatrix[1][2]}};
        double determinant1 = Tools3D.determinant(matrix1);

        double[][] matrix2 = {{invertedMatrix[2][0] * z, -invertedMatrix[0][0], -invertedMatrix[1][0]},
                            {invertedMatrix[2][1] * z, -invertedMatrix[0][1], -invertedMatrix[1][1]},
                            {invertedMatrix[2][2] * z, -invertedMatrix[0][2], -invertedMatrix[1][2]}};
        double determinant2 = Tools3D.determinant(matrix2);

        double[][] matrix3 = {{1, invertedMatrix[2][0] * z, -invertedMatrix[1][0]},
                            {-sX, invertedMatrix[2][1] * z, -invertedMatrix[1][1]},
                            {sY, invertedMatrix[2][2] * z, -invertedMatrix[1][2]}};
        double determinant3 = Tools3D.determinant(matrix3);

        double[][] matrix4 = {{1, -invertedMatrix[0][0], invertedMatrix[2][0] * z},
                            {-sX, -invertedMatrix[0][1], invertedMatrix[2][1] * z},
                            {sY, -invertedMatrix[0][2], invertedMatrix[2][2] * z}};
        double determinant4 = Tools3D.determinant(matrix4);


         if (determinant2 / determinant1 < 0) {
            double[] point = {camera.getX() - determinant3 / determinant1, camera.getY() - determinant4 / determinant1};
            return point;
        } else {
            return null;
        }
     }
}
