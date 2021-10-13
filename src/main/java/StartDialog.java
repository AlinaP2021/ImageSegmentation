import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StartDialog extends JDialog {
    
    public static BufferedImage originalImage = null;
    public static BufferedImage resultImage = null;
    public static int method;
    public static StartDialog dialog;

    public StartDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {

        fileChooser1.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif", "bmp"));

        fileChooser2.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser2.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif", "bmp"));

        fileChooser3.setAcceptAllFileFilterUsed(false);
        fileChooser3.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser3.setFileFilter(new FileNameExtensionFilter("Excel files", "xls", "xlsx"));

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Image Segmentation");
        setBackground(Color.WHITE);

        parametersPanel.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, null));
        parametersPanel.setPreferredSize(new Dimension(642, 50));

        cellsSizeField.setPreferredSize(new Dimension(45, 15));
        colorsNumberField.setPreferredSize(new Dimension(45, 15));

        cellsSizeTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
        cellsSizeTitle.setText("Размер ячейки:");

        colorsNumberTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
        colorsNumberTitle.setText("Количество цветов:");

        parametersTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
        parametersTitle.setText("Параметры сегментирования");

        GroupLayout parametersPanelLayout = new GroupLayout(parametersPanel);
        parametersPanel.setLayout(parametersPanelLayout);
        parametersPanelLayout.setHorizontalGroup(
                parametersPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(parametersPanelLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(parametersPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, parametersPanelLayout.createSequentialGroup()
                                                .addComponent(cellsSizeTitle, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cellsSizeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(38, 38, 38)
                                                .addComponent(colorsNumberTitle, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(colorsNumberField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(122, 122, 122))
                                        .addGroup(GroupLayout.Alignment.TRAILING, parametersPanelLayout.createSequentialGroup()
                                                .addComponent(parametersTitle, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
                                                .addGap(220, 220, 220))))
        );
        parametersPanelLayout.setVerticalGroup(
                parametersPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(parametersPanelLayout.createSequentialGroup()
                                .addComponent(parametersTitle)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(parametersPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(cellsSizeTitle)
                                        .addComponent(cellsSizeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(colorsNumberTitle)
                                        .addComponent(colorsNumberField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35))
        );

        desktopPane.setBackground(new Color(240, 240, 240));
        desktopPane.setForeground(Color.WHITE);
        desktopPane.setPreferredSize(new Dimension(622, 650));

        originalImagePlace.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, null));
        originalImagePlace.setPreferredSize(new Dimension(300, 300));

        resultImagePlace.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, null));
        resultImagePlace.setPreferredSize(new Dimension(300, 300));

        originalImageTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
        originalImageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        originalImageTitle.setVerticalAlignment(SwingConstants.TOP);
        originalImageTitle.setPreferredSize(new Dimension(300, 20));
        originalImageTitle.setVerticalTextPosition(SwingConstants.TOP);

        resultImageTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
        resultImageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        resultImageTitle.setVerticalAlignment(SwingConstants.TOP);
        resultImageTitle.setHorizontalTextPosition(SwingConstants.CENTER);
        resultImageTitle.setPreferredSize(new Dimension(300, 45));
        resultImageTitle.setVerticalTextPosition(SwingConstants.TOP);

        tablePane.setBorder(null);
        tablePane.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tablePane.setPreferredSize(new Dimension(157, 269));

        additionalPane.setBorder(null);
        additionalPane.setForeground(new Color(240, 240, 240));
        additionalPane.setFont(new Font("Tahoma", Font.PLAIN, 12));
        additionalPane.setPreferredSize(new Dimension(445, 240));

        additionalLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        additionalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        additionalLabel.setPreferredSize(new Dimension(400, 16));

        GroupLayout desktopPaneLayout = new GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
                desktopPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(desktopPaneLayout.createSequentialGroup()
                                .addGroup(desktopPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(desktopPaneLayout.createSequentialGroup()
                                                .addComponent(originalImagePlace, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(resultImagePlace, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(desktopPaneLayout.createSequentialGroup()
                                                .addComponent(originalImageTitle, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(resultImageTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(desktopPaneLayout.createSequentialGroup()
                                                .addGroup(desktopPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(additionalPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(additionalLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addComponent(tablePane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 2, Short.MAX_VALUE))
        );
        desktopPaneLayout.setVerticalGroup(
                desktopPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(desktopPaneLayout.createSequentialGroup()
                                .addGroup(desktopPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(originalImagePlace, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(resultImagePlace, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(desktopPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(originalImageTitle, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(resultImageTitle, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(desktopPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(desktopPaneLayout.createSequentialGroup()
                                                .addComponent(additionalPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(additionalLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(tablePane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(16, Short.MAX_VALUE))
        );
        desktopPane.setLayer(originalImagePlace, JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(resultImagePlace, JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(originalImageTitle, JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(resultImageTitle, JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(tablePane, JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(additionalPane, JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(additionalLabel, JLayeredPane.DEFAULT_LAYER);

        menuBar.setBackground(Color.BLUE);
        menuBar.setBorder(BorderFactory.createEtchedBorder());

        fileMenu.setForeground(Color.WHITE);
        fileMenu.setText("Файл");

        openMenuItem.setText("Открыть");
        openMenuItem.addActionListener(this::openActionPerformed);
        fileMenu.add(openMenuItem);

        saveMenuItem.setText("Сохранить результат сегментирования");
        saveMenuItem.addActionListener(this::saveActionPerformed);
        fileMenu.add(saveMenuItem);

        exitMenuItem.setText("Выход");
        exitMenuItem.addActionListener(this::exitActionPerformed);
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        segmentationMenu.setForeground(Color.WHITE);
        segmentationMenu.setText("Сегментация");

        fractalMenuItem.setText("Сегментировать методом модифицированной фрактальной сигнатуры");
        fractalMenuItem.addActionListener(this::fractalMethodActionPerformed);
        segmentationMenu.add(fractalMenuItem);

        multifractalMenuItem.setText("Сегментировать с помощью мультифрактального анализа");
        multifractalMenuItem.addActionListener(this::multifractalMethodActionPerformed);
        segmentationMenu.add(multifractalMenuItem);

        menuBar.add(segmentationMenu);

        tableMenu.setForeground(Color.WHITE);
        tableMenu.setText("Таблица");

        showTableMenuItem.setText("Показать");
        showTableMenuItem.addActionListener(this::tableActionPerformed);
        tableMenu.add(showTableMenuItem);

        saveTableMenuItem.setText("Сохранить как файл Excel");
        saveTableMenuItem.addActionListener(this::saveTableActionPerformed);
        tableMenu.add(saveTableMenuItem);

        menuBar.add(tableMenu);

        helpMenu.setForeground(Color.WHITE);
        helpMenu.setText("Помощь");

        manualMenuItem.setText("Руководство пользователя");
        manualMenuItem.addActionListener(this::manualActionPerformed);
        helpMenu.add(manualMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(parametersPanel, GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(desktopPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(parametersPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(desktopPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        pack();
    }

    private void openActionPerformed(ActionEvent evt) {
        resetValues();
        originalImagePlace.setIcon(new ImageIcon(" "));
        originalImageTitle.setText(" ");

        method = 0;
        int returnVal = fileChooser1.showOpenDialog(this);
        File file;
        boolean indicator = false;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser1.getSelectedFile();
            BufferedImage img = null;
            try {
                img = ImageIO.read(file);
            } catch (IOException ex) {
                Logger.getLogger(StartDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (img != null) {
                img = ImageConversion.copyImage(img);
                if (ImageConversion.isRGBColor(img)) {
                    Object[] possibilities = {"Выбрать усреднение компонент RGB",
                            "Выбрать Red компоненту",
                            "Выбрать Green компоненту",
                            "Выбрать Blue компоненту",
                            "Выбрать компоненту Hue",
                            "Выбрать компоненту Saturation",
                            "Выбрать компоненту Value (Brightness)",
                            "Выбрать компоненту CIE-L",
                            "Выбрать компоненту CIE-a",
                            "Выбрать компоненту CIE-b"};
                    Object message = "Вы открыли цветное изображение.\nВыберите палитру для преобразования изображения";
                    Object o = JOptionPane.showInputDialog(dialog, message, "Преобразование изображения", JOptionPane.PLAIN_MESSAGE, null, possibilities, message);
                    if (possibilities[0].equals(o)) {
                        img = ImageConversion.toGrayscaleImage(img);
                        indicator = true;
                    } else if (possibilities[1].equals(o)) {
                        img = ImageConversion.selectionOfComponent(img, 1);
                        indicator = true;
                    } else if (possibilities[2].equals(o)) {
                        img = ImageConversion.selectionOfComponent(img, 2);
                        indicator = true;
                    } else if (possibilities[3].equals(o)) {
                        img = ImageConversion.selectionOfComponent(img, 3);
                        indicator = true;
                    } else if (possibilities[4].equals(o)) {
                        img = ImageConversion.selectionOfComponent(img, 4);
                        indicator = true;
                    } else if (possibilities[5].equals(o)) {
                        img = ImageConversion.selectionOfComponent(img, 5);
                        indicator = true;
                    } else if (possibilities[6].equals(o)) {
                        img = ImageConversion.selectionOfComponent(img, 6);
                        indicator = true;
                    } else if (possibilities[7].equals(o)) {
                        img = ImageConversion.selectionOfComponent(img, 7);
                        indicator = true;
                    } else if (possibilities[8].equals(o)) {
                        img = ImageConversion.selectionOfComponent(img, 8);
                        indicator = true;
                    } else if (possibilities[9].equals(o)) {
                        img = ImageConversion.selectionOfComponent(img, 9);
                        indicator = true;
                    }
                } else {
                    img = ImageConversion.toGrayscaleImage(img);
                    indicator = true;
                }
                if (indicator) {
                    originalImage = ImageConversion.copyImage(img);
                    originalImagePlace.setIcon(new ImageIcon(ImageConversion.resizeImage(img)));
                    originalImageTitle.setText("Исходное изображение размера " + img.getWidth() + "×" + img.getHeight());
                }
            }
        } else {
            System.out.println("File access cancalled by user");
        }
    }

    private void fractalMethodActionPerformed(ActionEvent evt) {
        resetValues();
        method = 1;
        String str1 = cellsSizeField.getText();
        String str2 = colorsNumberField.getText();
        if (str1.equals("") || str2.equals("")){
            JOptionPane.showMessageDialog(dialog, "Введите параметры сегментирования!");
        } else {
            int cellsSize = Integer.parseInt(str1);
            int colorsNumber = Integer.parseInt(str2);
            BufferedImage originalImg = ImageConversion.copyImage(originalImage);
            FractalSegmentation.eps = cellsSize;
            long startTime = System.currentTimeMillis();
            FractalSegmentation.calcOfSignature(originalImg);
            long spentTime = System.currentTimeMillis() - startTime;
            int width = FractalSegmentation.width;
            int height = FractalSegmentation.height;
            double[] d = FractalSegmentation.d;
            BufferedImage resultImg = ImageConversion.fractalMethodColoration(originalImg, cellsSize, colorsNumber, width, height, d);
            resultImage = resultImg;
            resultImagePlace.setIcon(new ImageIcon(ImageConversion.resizeImage(resultImg)));
            resultImageTitle.setText("<html><center>Сегментация методом фрактальной сигнатуры." +
                    "<br>Размер изображения: " + resultImage.getWidth() + "×" + resultImage.getHeight() +
                    ". Время: " + spentTime + "мс</center></html>");
        }
    }

    private void resetValues() {
        ImageConversion.colors = null;
        ImageConversion.values = null;
        FractalSegmentation.d = null;
        MultifractalSegmentation.alfa = null;
        resultImagePlace.setIcon(new ImageIcon(" "));
        resultImageTitle.setText(" ");
        additionalLabel.setText(" ");
        additionalPane.getViewport().removeAll();
        additionalPane.repaint();
        tablePane.repaint();
    }

    private void tableActionPerformed(ActionEvent evt) {
        JTable table;
        try {
            if (method == 1) {
                table = ImageTable.createTable(FractalSegmentation.d, FractalSegmentation.width, FractalSegmentation.height);
                additionalPane.getViewport().add(table);
                additionalPane.repaint();
                additionalLabel.setText("В таблице - значения фрактальной сигнатуры");
                ImageTable.createScale(ImageConversion.values, ImageConversion.colors);
            } else if (method == 2) {
                table = ImageTable.createTable(MultifractalSegmentation.alfa, MultifractalSegmentation.width, MultifractalSegmentation.height);
                additionalPane.getViewport().add(table);
                additionalPane.repaint();
                additionalLabel.setText("В таблице - значения силы сингулярности");
                ImageTable.createScale(ImageConversion.values, ImageConversion.colors);
            }
        } catch (OutOfMemoryError e){
            JOptionPane.showMessageDialog(dialog, "<html>Таблица слишком большая, <br>показана не будет.</html>");
        }
    }

    private void saveActionPerformed(ActionEvent evt) {
         int returnVal = fileChooser2.showSaveDialog(null);
         if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser2.getSelectedFile();
             try {
                 if (file.getName().indexOf('.') == -1){
                    file = new File(file.getPath() + ".png");
                 }
                 ImageIO.write(resultImage, "png", file);
             } catch (IOException ex) {
                 Logger.getLogger(StartDialog.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
    }

    private void exitActionPerformed(ActionEvent evt) {
        System.exit(0); 
    }

    private void multifractalMethodActionPerformed(ActionEvent evt) {
        resetValues();
        method = 2;
        String str1 = cellsSizeField.getText();
        String str2 = colorsNumberField.getText();
        if (str1.equals("") || str2.equals("")){
            JOptionPane.showMessageDialog(dialog, "Введите параметры сегментирования!");
        } else {
            int cellsSize = Integer.parseInt(str1);
            int colorsNumber = Integer.parseInt(str2);
            BufferedImage originalImg = ImageConversion.copyImage(originalImage);
            MultifractalSegmentation.eps = cellsSize;
            long startTime = System.currentTimeMillis();
            MultifractalSegmentation.calcOfAlfa(originalImg);
            long spentTime = System.currentTimeMillis() - startTime;
            int width = MultifractalSegmentation.width;
            int height = MultifractalSegmentation.height;
            double[] alfa = MultifractalSegmentation.alfa.clone();
            BufferedImage resultImg = ImageConversion.multifractalMethodColoration(originalImg, cellsSize, colorsNumber, width, height, alfa);
            resultImage = resultImg;
            ImageIcon imageIcon = new ImageIcon(ImageConversion.resizeImage(resultImg));
            resultImagePlace.setIcon(imageIcon);
            resultImageTitle.setText("<html><center>Сегментация с помощью мультифрактального анализа." +
                    "<br>Размер изображения: " + resultImage.getWidth() + "×" + resultImage.getHeight() +
                    ". Время: " + spentTime + "мс</center></html>");
        }
    }

    private void saveTableActionPerformed(ActionEvent evt) {
        JTable table = null;
        try {
            if (method == 1) {
                table = ImageTable.createTable(FractalSegmentation.d, FractalSegmentation.width, FractalSegmentation.height);
            } else if (method == 2) {
                table = ImageTable.createTable(MultifractalSegmentation.alfa, MultifractalSegmentation.width, MultifractalSegmentation.height);
            }
            int returnVal = fileChooser3.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION){
                File file = fileChooser3.getSelectedFile();
                if (file.getName().indexOf('.') == -1) {
                    file = new File(file.getPath() + ".xls");
                }
                if (table != null) ImageTable.tableToExcel(table, file);
            }
        } catch (OutOfMemoryError e){
            JOptionPane.showMessageDialog(dialog, "<html>Таблица слишком большая, <br>сохранена не будет.</html>");
        } catch (IOException ex) {
            Logger.getLogger(StartDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manualActionPerformed(ActionEvent evt) {
        UserManual userManual = new UserManual();
        desktopPane.add(userManual);
        userManual.show();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            dialog = new StartDialog(new JFrame(), true);
            dialog.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    private final JPanel parametersPanel = new JPanel();
    private final JLabel parametersTitle = new JLabel();
    private final JLabel cellsSizeTitle = new JLabel();
    private final JLabel colorsNumberTitle = new JLabel();
    private final JTextField cellsSizeField = new JTextField();
    private final JTextField colorsNumberField = new JTextField();
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final JFileChooser fileChooser1 = new JFileChooser();
    private final JFileChooser fileChooser2 = new JFileChooser();
    private final JFileChooser fileChooser3 = new JFileChooser();
    public static JLabel originalImagePlace = new JLabel();
    public static JLabel resultImagePlace = new JLabel();
    private final JLabel originalImageTitle = new JLabel();
    private final JLabel resultImageTitle = new JLabel();
    private final JLabel additionalLabel = new JLabel();
    private final JScrollPane additionalPane = new JScrollPane();
    public static JScrollPane tablePane = new JScrollPane();
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu();
    private final JMenu segmentationMenu = new JMenu();
    private final JMenu tableMenu = new JMenu();
    private final JMenu helpMenu = new JMenu();
    private final JMenuItem manualMenuItem = new JMenuItem();
    private final JMenuItem openMenuItem = new JMenuItem();
    private final JMenuItem exitMenuItem = new JMenuItem();
    private final JMenuItem saveMenuItem = new JMenuItem();
    private final JMenuItem saveTableMenuItem = new JMenuItem();
    private final JMenuItem fractalMenuItem = new JMenuItem();
    private final JMenuItem multifractalMenuItem = new JMenuItem();
    private final JMenuItem showTableMenuItem  = new JMenuItem();
}
