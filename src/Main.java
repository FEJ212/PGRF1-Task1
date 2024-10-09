import controller.Controller2D;
import view.*;

public class Main {

    public static void main(String[] args) {
        Window window = new Window(1280, 720);
        new Controller2D(window.getPanel());
    }
}