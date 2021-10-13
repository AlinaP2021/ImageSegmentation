import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import static java.lang.Math.log;


public class MultifractalSegmentation {
    
    public static double[] alfa;
    public static int width;
    public static int height;
    public static int eps;
    
    public static void calcOfAlfa(BufferedImage inputFile){
        WritableRaster raster = inputFile.getRaster();
        width = inputFile.getWidth() / eps;
        height = inputFile.getHeight() / eps;
        int n = width * height;
        alfa = new double[n];
        double[] A = new double[3];
        double[] B = new double[3];
        int[][] sum = new int[3][n];
        long sumImg = 0;
        int r = 0;
        for (int x = 1; x <= width; x++)
            for (int y = 1; y <= height; y++) {
                sum[0][r] = 0;
                for (int k = (x - 1) * eps; k < x * eps; k++)
                    for (int l = (y - 1) * eps; l < y * eps; l++) {
                         sum[0][r] += raster.getSample(k, l, 0);
                    }
                sum[1][r] = 0;
                sum[2][r] = 0;
                if (x != 1) {
                    int k = (x - 1) * eps - 1;
                    for (int l = (y - 1) * eps; l < y * eps; l++) {
                         sum[1][r] += raster.getSample(k, l, 0);
                         if(eps != 1 || x > 2) sum[2][r] += raster.getSample(k - 1, l, 0);
                    }
                }
                if (y != 1) {
                    int l = (y - 1) * eps - 1;
                    for (int k = (x - 1) * eps; k < x * eps; k++) {
                         sum[1][r] += raster.getSample(k, l, 0);
                         if(eps != 1 || y > 2) sum[2][r] += raster.getSample(k, l - 1, 0);
                    }
                }
                if (x != width) {
                    int k = x * eps;
                    for (int l = (y - 1) * eps; l < y * eps; l++) {
                         sum[1][r] += raster.getSample(k, l, 0);
                         if(eps != 1 || x < width - 1) sum[2][r] += raster.getSample(k + 1, l, 0);
                    }
                }
                if (y != height) {
                    int l = y * eps;
                    for (int k = (x - 1) * eps; k < x * eps; k++) {
                         sum[1][r] += raster.getSample(k, l, 0);
                         if(eps != 1 || y < height - 1) sum[2][r] += raster.getSample(k, l + 1, 0);
                    }
                }
                if (x != 1 && y != 1) {
                    int k = (x - 1) * eps - 1;
                    int l = (y - 1) * eps - 1;
                    sum[1][r] += raster.getSample(k, l, 0);
                    if (eps != 1 || (x > 2 && y > 2)){
                        sum[2][r] += raster.getSample(k - 1, l - 1, 0);
                        sum[2][r] += raster.getSample(k - 1, l, 0);
                        sum[2][r] += raster.getSample(k, l - 1, 0);
                    }
                }
                if (y != 1 && x != width) {
                    int k = x * eps;
                    int l = (y - 1) * eps - 1;
                    sum[1][r] += raster.getSample(k, l, 0);
                    if (eps != 1 || (y > 2 && x < width - 1)) {
                        sum[2][r] += raster.getSample(k + 1, l - 1, 0);
                        sum[2][r] += raster.getSample(k + 1, l, 0);
                        sum[2][r] += raster.getSample(k, l - 1, 0);
                    }
                }
                if (x != width && y != height) {
                    int k = x * eps;
                    int l = y * eps;
                    sum[1][r] += raster.getSample(k, l, 0);
                    if (eps != 1 || (x < width - 1 && y < height - 1)) {
                        sum[2][r] += raster.getSample(k + 1, l + 1, 0);
                        sum[2][r] += raster.getSample(k + 1, l, 0);
                        sum[2][r] += raster.getSample(k, l + 1, 0);}
                }
                if (y != height && x != 1) {
                    int k = (x - 1) * eps - 1;
                    int l = y * eps;
                    sum[1][r] += raster.getSample(k, l, 0);
                    if (eps!=1 || (y < height - 1 && x > 2)) {
                        sum[2][r] += raster.getSample(k - 1, l + 1, 0);
                        sum[2][r] += raster.getSample(k - 1, l, 0);
                        sum[2][r] += raster.getSample(k, l + 1, 0);
                    }
                }
                sum[1][r] += sum[0][r];
                sum[2][r] += sum[1][r];
                sumImg = sumImg + sum[0][r];
                r = r + 1;
            }
        for (int i = 0; i < n; i++){
            A[0] = log(eps) / log(2);
            A[1] = log(eps + 2) / log(2);
            A[2] = log(eps + 4) / log(2);
            B[0] = log(sum[0][i]) / log(2);
            B[1] = log(sum[1][i]) / log(2);
            B[2] = log(sum[2][i]) / log(2);
            alfa[i] = FractalSegmentation.leastSquareMethod(A, B);
        }
    }
}
