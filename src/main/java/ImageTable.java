import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;


public class ImageTable {
    
    public static JTable createTable(double[] d, int w, int h){
        Object[][] data = new Object[h][w];
        int k = 0;
        DecimalFormat f = new DecimalFormat("###.#####");
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++) {
                data[j][i] = f.format(d[k]);
                k++;
            }
        String[] columnNames = new String[w];
        for (int i = 0; i < w; i++){
            columnNames[i] = Integer.toString(i + 1);
        }
        JTable table = new JTable(data, columnNames);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.setTableHeader(null);
        return table;
    } 
    
    public static void tableToExcel(JTable table, File file) throws IOException {
        TableModel model = table.getModel();
        try (FileWriter output = new FileWriter(file)) {
            for(int k = 0; k < model.getRowCount(); k++) {
                for(int j = 0; j < model.getColumnCount(); j++) {
                    output.write(model.getValueAt(k, j).toString() + "\t");
                }
                output.write("\n");
            }
        }
    }
    
    public static void createScale(double[] values, int[] colors){
        Graphics g = StartDialog.tablePane.getGraphics();
        int point1 = 10;
        int point2 = 25;
        DecimalFormat f = new DecimalFormat("###.#####");
        if (colors != null) {
            for (int i = 0; i < colors.length; i++) {
                Color newColor = new Color(colors[i], colors[i], colors[i]);
                g.setColor(newColor);
                g.fillRect(5, point1, 20, 20);
                newColor = Color.BLACK;
                g.setColor(newColor);
                String str;
                if (i != colors.length - 1) str = f.format(values[i]) + " - " + f.format(values[i + 1] - 0.00001);
                else str = f.format(values[i]) + " - " + f.format(values[i + 1]);
                g.drawString(str, 35, point2);
                point1 =  point1 + 20;
                point2 =  point2 + 20;
            }
        }
    }
}
