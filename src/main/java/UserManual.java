import javax.swing.*;
import java.awt.*;

public class UserManual extends JInternalFrame {

   
    public UserManual() {
        initComponents();
    }

    private void initComponents() {

        JScrollPane scrollPane = new JScrollPane();
        JTextArea textArea = new JTextArea();

        setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
        setClosable(true);
        setTitle("Руководство пользователя");
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);

        textArea.setEditable(false);
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        textArea.setText("""
                   «ImageSegmentation» - это программное средство для сегментации\s
                изображения двумя методами:
                   1. методом модифицированной фрактальной сигнатуры;
                   2. методом мультифрактального анализа.

                   Системные требования: необходимо установить на компьютере\s
                JRE (Java Runtime Environment).

                   В начале работы с программой нажмите «Файл» → «Открыть»\s
                в меню главного окна, в появившемся окне выберите файл\s
                изображения (типы jpg, jpeg, png, gif, bmp).\s
                   Программа работает с изображениями в градациях серого.\s
                Если Вы открываете цветное изображение, то в появившемся окне\s
                выберите преобразование для данного изображения.
                   Изображение загружается в левый  верхний угол главного окна.

                   Перед началом сегментации на панели «Параметры сегментирования»\s
                введите размер стороны ячейки и количество цветов окрашивания.
                   Предупреждение: если введенный размер ячейки большой, то большая\s
                часть изображения будет усечена.\s
                   Рекомендуемые размеры ячейки: 2, 3, 4, 5.
                   Рекомендуемое количество цветов: 2, 3, 4, 5.

                   Чтобы сегментировать изображение методом модифицированной\s
                фрактальной сигнатуры, нажмите «Сегментация» → «Сегментировать\s
                методом модифицированной фрактальной сигнатуры». Результат\s
                сегментирования появится в правой верхней части главного окна.\s

                   Чтобы сегментировать методом мультифрактального анализа,
                нажмите «Сегментация» → «Сегментировать с помощью\s
                мультифрактального анализа».

                   В ходе сегментации производится вычисление значений:
                для первого метода – фрактальной сигнатуры,\s
                для второго метода – силы сингулярности.\s
                Данные значения можно посмотреть в виде таблицы, нажав\s
                «Таблица» → «Показать». Таблица со шкалой окрашивания появится\s
                в нижней части окна. Для сохранения данной таблицы на компьютер\s
                пользователя, нажмите «Таблица» → «Сохранить как файл Excel».

                   Для сохранения полученного изображения в результате сегментирования,
                нажмите «Файл» → «Сохранить результат сегментирования».

                   Доступ к Руководству пользователя осуществляется по нажатию\s
                «Помощь» → «Руководство пользователя» в главном меню окна.
                """);
        textArea.setWrapStyleWord(true);
        scrollPane.setViewportView(textArea);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
        );
        pack();
    }
}
