package controller;

import rasterize.*;
import render.Renderer;
import solid.Arrow;
import view.Panel;
import javax.swing.*;
import java.awt.event.*;

//Třída pro uživatelské ovládání
public class Controller3D {
    private final Panel panel;
    private Renderer renderer;
    private Arrow arrow;

    public Controller3D(Panel panel){
        this.panel = panel;
        renderer = new Renderer(new LineRasterizerGraphics(panel.getRaster()));

        arrow = new Arrow();

        initListener();

        repaintCanvas();
    }

    private void initListener(){
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

            }
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }
        });
        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            //detekce zmáčknutého shiftu pro zapnutí line snappingu
            @Override
            public void keyPressed(KeyEvent e) {

            }
            //vypnutí režimu line snapping po puštění tlačítka shift
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
    //vykreslování dříve vykreslených čar
    public void repaintCanvas(){
        panel.clear();
        renderer.renderSolid(arrow);
        panel.repaint();
    }
}