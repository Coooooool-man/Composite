package ChooseFile;

import javax.swing.*;

public class DialogFile {


    private  JButton  btnSaveFile   = null;
    private  JButton  btnOpenDir    = null;
    private  JButton  btnFileFilter = null;

    private  JFileChooser fileChooser = null;

    private final String[][] FILTERS = {{"jpg"}, {"png"}};

    public JButton[] GetButton(){

        JButton buttonArray[] = {btnSaveFile, btnOpenDir};

        if(btnSaveFile != null)
            return buttonArray;
        else
            return null;
    }
    public DialogFile(JPanel contents){
        // Кнопка создания диалогового окна для выбора директории
        btnOpenDir = new JButton("Открыть папку");
        // Кнопка создания диалогового окна для сохранения файла
        btnSaveFile = new JButton("Сохранить файл");
        // Кнопка создания диалогового окна для сохранения файла
        //btnFileFilter = new JButton("Фильтрация файлов");

        // Создание экземпляра JFileChooser
        fileChooser = new JFileChooser();
        // Подключение слушателей к кнопкам
        //addFileChooserListeners();

        // Размещение кнопок в интерфейсе

        contents.add(btnOpenDir   );

        contents.add(btnSaveFile  );
        contents.add(btnFileFilter);
        //setContentPane(contents);
    }

}
