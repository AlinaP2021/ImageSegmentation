import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import static java.lang.Math.log;


public class FractalSegmentation {
     
    public static double[] d;
    public static int width;
    public static int height;
    public static int eps;
    
    public static void calcOfSignature(BufferedImage inputFile){
        WritableRaster raster = inputFile.getRaster();
        width = inputFile.getWidth() / eps;
        height = inputFile.getHeight() / eps;
        int n = width * height;
        int[][][] g = new int[eps][eps][n];
        int[][][] u = new int[eps][eps][4];
        int[][][] b = new int[eps][eps][4];
        long[] V = new long[4];
        double[] A1 = new double[3];
        d = new double[n];
        double[] A = new double[3];
        double[] B = new double[3];
        int r = 0;
        V[0] = 0;
        for (int x = 1; x <= width; x++)
            for (int y = 1; y <= height; y++) {
                for (int k = (x - 1) * eps; k < x * eps; k++)
                    for (int l = (y - 1) * eps; l < y * eps; l++) {
                         int q = k - (x - 1) * eps;
                         int p = l - (y - 1) * eps;
                         g[p][q][r] = raster.getSample(k, l, 0);
                    }
                r = r + 1;
            }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= 3; j++) {
                V[j] = 0;
                A1[j - 1] = 0;
                for (int k = 0; k < eps; k++)
                    for (int l = 0; l < eps; l++) {
                        if (j == 1) {
                            u[k][l][j-1] = g[k][l][i];
                            b[k][l][j-1] = g[k][l][i];
                        }
                        int max = u[k][l][j - 1] + 1;
                        if (l + 1 < eps) if (max < u[k][l + 1][j - 1]) max = u[k][l + 1][j - 1];
                        if (l - 1 >= 0) if (max < u[k][l - 1][j - 1]) max = u[k][l - 1][j - 1];
                        if (k + 1 < eps) if (max < u[k + 1][l][j - 1]) max = u[k + 1][l][j - 1];
                        if (k - 1 >= 0) if (max < u[k - 1][l][j - 1]) max = u[k - 1][l][j - 1];
                        u[k][l][j] = max;
                        int min = b[k][l][j - 1] - 1;
                        if (l + 1 < eps) if (min > b[k][l + 1][j - 1]) min = b[k][l + 1][j - 1];
                        if (l - 1 >= 0) if (min > b[k][l - 1][j - 1]) min = b[k][l - 1][j - 1];
                        if (k + 1 < eps) if (min > b[k + 1][l][j - 1]) min = b[k + 1][l][j - 1];
                        if (k - 1 >= 0) if (min > b[k - 1][l][j - 1]) min = b[k - 1][l][j - 1];
                        b[k][l][j] = min;
                        V[j]=V[j] + (u[k][l][j] - b[k][l][j]);
                    }
                A1[j - 1] = (double)(V[j] - V[j - 1]) / 2;
                A[j - 1] = log(j) / log(2);
                B[j - 1] = log(A1[j - 1]) / log(2);
            }
            d[i] = 2 - leastSquareMethod(A, B);
        }
    }
    
    public static double leastSquareMethod(double[] A, double[] B){
        int n = 3;
        double s1 = 0;
        for (int i = 0; i < n; i++){
            s1 = s1 + A[i] * B[i];
        }
        double s2 = 0;
        for (int i = 0; i < n; i++){
            s2 = s2 + A[i];
        }
        double s3 = 0;
        for (int i = 0; i < n; i++){
            s3 = s3 + B[i];
        }
        double s4 = 0;
        for (int i = 0; i < n; i++){
            s4 = s4 + A[i] * A[i];
        }
        double a = (-n * s1 + s2 * s3) / (s2 * s2 - n * s4);
        if (Double.isNaN(a)) a = Double.POSITIVE_INFINITY;
        return -a;
    }
}
