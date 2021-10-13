import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;


public class ImageConversion {
    
    public static double[] values;
    public static int[] colors;
    
    public static boolean isRGBColor(BufferedImage img){
        Color color;
        for (int i = 0; i < img.getWidth(); i++)
            for(int j = 0; j < img.getHeight(); j++) {
                int rgb = img.getRGB(i, j);
                color = new Color(rgb, true);
                if (color.getRed() != color.getGreen() || color.getGreen() != color.getBlue()){
                    return true;
                }
            }
        return false;
    }
    
    public static Image resizeImage(BufferedImage originalImage){
        return originalImage.getScaledInstance(300, -1, Image.SCALE_FAST);
    }
    
    public static BufferedImage toGrayscaleImage(BufferedImage originalImage){
        BufferedImage image = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < originalImage.getWidth(); i++)
            for(int j = 0; j < originalImage.getHeight(); j++){
                int rgb = originalImage.getRGB(i, j);
                Color color = new Color(rgb, true);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int average = (red+green+blue)/3;
                color = new Color(average, average, average);
                image.setRGB(i, j, color.getRGB());
            }
        return image;
    }
    
    public static double[] RGBtoXYZ(int red, int green, int blue) {
        double[] xyz = new double[3];
        double varR = (double) red / 255;
        double varG = (double) green / 255;
        double varB = (double) blue / 255;
        if (varR > 0.04045) varR = Math.pow((varR + 0.055) / 1.055, 2.4);
        else varR = varR / 12.92;
        if (varG > 0.04045) varG = Math.pow((varG + 0.055) / 1.055, 2.4);
        else varG = varG / 12.92;
        if (varB > 0.04045) varB = Math.pow((varB + 0.055) / 1.055, 2.4);
        else varB = varB / 12.92;
        varR = varR * 100;
        varG = varG * 100;
        varB = varB * 100;
        xyz[0] = varR * 0.4124 + varG * 0.3576 + varB * 0.1805;
        xyz[1] = varR * 0.2126 + varG * 0.7152 + varB * 0.0722;
        xyz[2] = varR * 0.0193 + varG * 0.1192 + varB * 0.9505;
        return xyz;
    }
    
    public static double[] XYZtoLab(double X, double Y, double Z) {
        double[] Lab = new double[3]; 
        double ref_X = 95.047;
        double ref_Y = 100.000;
        double ref_Z = 108.883;
        double var_X = X / ref_X;
        double var_Y = Y / ref_Y;
        double var_Z = Z / ref_Z;
        if (var_X > 0.008856) var_X = Math.pow(var_X, 0.333);
        else var_X = 7.787 * var_X + 0.137931;
        if (var_Y > 0.008856) var_Y = Math.pow(var_Y, 0.333);
        else var_Y = 7.787 * var_Y + 0.137931;
        if (var_Z > 0.008856) var_Z = Math.pow(var_Z, 0.333);
        else var_Z = 7.787 * var_Z + 0.137931;
        Lab[0] = (116 * var_Y) - 16;
        Lab[1] = 500 * (var_X - var_Y);
        Lab[2] = 200 * (var_Y - var_Z);
        return Lab;
    }
    
    public static BufferedImage selectionOfComponent(BufferedImage originalImage, int comp){
        BufferedImage image = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < originalImage.getWidth(); i++)
            for(int j = 0; j < originalImage.getHeight(); j++){
                int rgb = originalImage.getRGB(i, j);
                Color color = new Color(rgb, true);
                int m = 0;
                if (comp == 1) {
                    m = color.getRed();
                } else if (comp == 2) {
                    m = color.getGreen();
                } else if (comp == 3) {
                    m = color.getBlue();
                } else if (comp == 4){
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    float[] hsv = Color.RGBtoHSB(red, green, blue, null);
                    m = (int)(hsv[0] * 255);
                } else if (comp == 5) {
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    float[] hsv = Color.RGBtoHSB(red, green, blue, null);
                    m = (int)(hsv[1] * 255);
                } else if (comp == 6) {
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    float[] hsv = Color.RGBtoHSB(red, green, blue, null);
                    m = (int)(hsv[2] * 255);
                } else if (comp == 7) {
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    double[] xyz = RGBtoXYZ(red, green, blue);
                    double[] lab = XYZtoLab(xyz[0], xyz[1], xyz[2]);
                    m = (int)lab[0];
                } else if (comp == 8) {
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    double[] xyz = RGBtoXYZ(red, green, blue);
                    double[] lab = XYZtoLab(xyz[0], xyz[1], xyz[2]);
                    m = (int)lab[1] + 128;
                } else if (comp == 9) {
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    double[] xyz = RGBtoXYZ(red, green, blue);
                    double[] lab = XYZtoLab(xyz[0], xyz[1], xyz[2]);
                    m = (int)lab[2] + 128;
                }
                color = new Color(m, m, m);
                image.setRGB(i, j, color.getRGB());
            }
        return image;
    }

    static BufferedImage copyImage(BufferedImage file) {
        BufferedImage copyImg = new BufferedImage(file.getWidth(null), file.getHeight(null), BufferedImage.TYPE_INT_RGB);
        copyImg.createGraphics().drawImage(file, 0, 0, null);
        return copyImg;
    }
     
    public static BufferedImage fractalMethodColoration(BufferedImage file, int eps, int color, int width, int height, double[] d){
        colors = new int[color];
        values = new double[color + 1];
        int n = width * height;
        double[] d1 = d.clone();
        Arrays.sort(d1);
        int step = (n - 1) / color;
        int step1 = 255 / (color - 1);
        for (int j = 0; j < color; j++){
            values[j] = d1[j * step];
            colors[j] = j * step1;
        }
        values[color] = d1[n - 1];
        colors[color - 1] = 255;
        int i = 0;
        for (int x = 1; x <= width; x++)
            for (int y = 1; y <= height; y++){
                int m = 0;
                for (int j = 1; j < color; j++){
                    if (values[j - 1] <= d[i] && d[i] < values[j]) m = colors[j - 1];
                }
                if (d[i] >= values[color - 1]) m = colors[color - 1];

                for (int k = (x - 1) * eps; k < x * eps; k++)
                    for (int l = (y - 1) * eps; l < y * eps; l++){
                        Color col = new Color(m, m, m);
                        file.setRGB(k, l, col.getRGB());
                }
                i = i + 1;
            }
        return file.getSubimage(0, 0, width * eps, height * eps);
    }
    
    public static BufferedImage multifractalMethodColoration(BufferedImage file, int eps, int color, int width, int height, double[] d){
        colors = new int[color];
        values = new double[color + 1];
        int n = width * height;
        double[] d1 = d.clone();
        Arrays.sort(d1);
        int min = 0;
        values[0] = d1[0];
        colors[0] = 0;
        if (d1[0] == Double.NEGATIVE_INFINITY) {
            values[0] = Double.NEGATIVE_INFINITY;
            for (int j = 1; j < n; j++) {
                if (d1[j] != Double.NEGATIVE_INFINITY) {
                    min = j;
                    break;
                }
            }
        }
        int step = (n - 1 - min) / color;
        int step1 = 255 / (color - 1);
        for (int j = 1; j < color; j++) {
            values[j] = d1[min + j * step];
            colors[j] = j * step1;
        }
        values[color] = d1[n - 1];
        colors[color - 1] = 255;
        int i = 0;
            for (int x = 1; x <= width; x++)
                for (int y = 1; y <= height; y++) {
                    int m = 0;
                    for (int j = 1; j < color - 1; j++) {
                        if (values[j] <= d[i] && d[i] < values[j + 1]) {
                            m = colors[j];
                        }
                    }
                    if (d[i] >= values[color - 1]) {
                        m = colors[color - 1];
                    }
                    for (int k = (x - 1) * eps; k < x * eps; k++)
                        for (int l = (y - 1) * eps; l < y * eps; l++){
                            Color col = new Color(m, m, m);
                            file.setRGB(k, l, col.getRGB());
                    }
                i = i + 1;
                }
        return file.getSubimage(0, 0, width * eps, height * eps);
    }
}
