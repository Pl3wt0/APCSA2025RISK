package tools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class a {
    public static void pr(Object obj) {
        System.out.print(obj.toString());
    }
    public static <T>void pr(T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            a.pr(arr[i] + ", ");
        }
    }
    public static <T>void pr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            a.pr(arr[i] + ", ");
        }
    }
    public static <T>void pr(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            a.pr(arr[i] + ", ");
        }
    }
    public static <T>void pr(boolean[] arr) {
        for (int i = 0; i < arr.length; i++) {
            a.pr(arr[i] + ", ");
        }
    }
    public static <T>void prl(T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            a.pr(arr[i] + ", ");
        }
        a.prl("");
    }
    public static <T>void prl(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            a.pr(arr[i] + ", ");
        }
        a.prl("");
    }
    public static <T>void prl(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            a.pr(arr[i] + ", ");
        }
        a.prl("");
    }
    public static <T>void prl(boolean[] arr) {
        for (int i = 0; i < arr.length; i++) {
            a.pr(arr[i] + ", ");
        }
        a.prl("");
    }
    public static <T>void pr(T[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                a.pr(arr[i][j] + ", ");
            }
            a.prl("");
        }
    }
    public static void pr(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                a.pr(arr[i][j] + ", ");
            }
            a.prl("");
        }
    }
    public static void pr(double[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                a.pr(arr[i][j] + ", ");
            }
            a.prl("");
        }
    }
    public static void pr(boolean[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                a.pr(arr[i][j] + ", ");
            }
            a.prl("");
        }
    }

    public static void prl(Object obj) {
        System.out.println(obj.toString());
    }
    public static void prArr(ArrayList<?> arr) {
        for (Object a : arr) {
            pr(a.toString());
        }
        prl("");
    }
    public static void pr2DArr(ArrayList<ArrayList<?>> arr) {
        for (ArrayList<?> arr1 : arr) {
            prArr(arr1);
        } 
        prl("");
    }

}
