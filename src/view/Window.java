package view;

import javax.swing.*;
//vytvoření okna programu
public class Window extends JFrame{
    private final Panel panel;
    //konstruktor
    public Window(int width, int height) {
        //nastavení vlastností okna
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("01");
        setVisible(true);
        //přidání panelu na kreslení
        panel = new Panel(width, height);
        add(panel);
        pack();
        //nastavení vlastností panelu
        panel.setFocusable(true);
        panel.grabFocus();
    }
    //getter panelu
    public Panel getPanel(){
        return panel;
    }
}
