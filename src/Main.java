import controller.*;
import view.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //vytvoření okna
        Window window = new Window(1280, 720);
        //implementace uživatelksého ovládání pro 2D režim
        //new Controller2D(window.getPanel());
        //implementace uživatelksého ovládání pro 3D režim
        new Controller3D(window.getPanel());
    }
}