import controller.*;
import view.*;

public class Main {

    public static void main(String[] args) {
        //vytvoření okna
        Window window = new Window(1280, 720);
        //implementace uživatelksého ovládání
        new Controller3D(window.getPanel());
    }
}