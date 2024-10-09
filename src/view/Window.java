package view;

import javax.swing.*;

public class Window extends JFrame{
    private final Panel panel;
    public Window(int width, int height) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("01");
        setVisible(true);

        panel = new Panel(width, height);
        add(panel);
        pack();

        panel.setFocusable(true);
        panel.grabFocus();
    }
    public Panel getPanel(){
        return panel;
    }
}
