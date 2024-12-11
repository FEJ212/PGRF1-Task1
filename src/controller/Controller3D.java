package controller;

import rasterize.*;
import render.Renderer;
import solid.Arrow;
import solid.Solid;
import transforms.*;
import view.Panel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

//Třída pro uživatelské ovládání
public class Controller3D {
    private final Panel panel;
    private Renderer renderer;
    private ArrayList<Solid> solids;
    private Arrow arrow;
    //private Axis axisX, axisY, axisZ;
    private Camera camera;
    private Mat4 perspective, orthogonal, current;
    private int startX, startY;

    double translateX = 0;
    double translateY = 0;
    double translateZ = 0;
    int scale = 0;
    int rotate = 0;

    public Controller3D(Panel panel){
        this.panel = panel;
        renderer = new Renderer(new LineRasterizerGraphics(panel.getRaster()), panel.getRaster());

        arrow = new Arrow();

        initListener();

        repaintCanvas();
    }

    private void initListener(){
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
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
                camera = camera.addAzimuth(Math.PI*(e.getX()-startX)/(double)panel.getWidth());
                camera = camera.addZenith(Math.PI*(e.getY()-startY)/(double)panel.getHeight());

                if(camera.getZenith()>90){
                    camera = camera.withZenith(90);
                }
                if(camera.getZenith()<-90){
                    camera = camera.withZenith(-90);
                }

                startX = e.getX();
                startY = e.getY();
                repaintCanvas();
            }
        });
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            //detekce zmáčknutého shiftu pro zapnutí line snappingu
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_Q:
                        camera = camera.up(0.1);
                        repaintCanvas();
                        break;
                    case KeyEvent.VK_E:
                        camera = camera.down(0.1);
                        repaintCanvas();
                        break;
                    case KeyEvent.VK_W:
                        camera = camera.forward(0.1);
                        repaintCanvas();
                        break;
                    case KeyEvent.VK_S:
                        camera = camera.backward(0.1);
                        repaintCanvas();
                        break;
                    case KeyEvent.VK_A:
                        camera = camera.left(0.1);
                        repaintCanvas();
                        break;
                    case KeyEvent.VK_D:
                        camera = camera.right(0.1);
                        repaintCanvas();
                        break;
                    case KeyEvent.VK_P:
                        if(current == perspective){
                            current = orthogonal;
                        } else {
                            current = perspective;
                        }
                }
                repaintCanvas();
            }
            //vypnutí režimu line snapping po puštění tlačítka shift
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
    public void initScene(){
        camera = new Camera(new Vec3D(0.5,-5,2.3),Math.toRadians(90),Math.toRadians(-15),10,true);
        perspective = new Mat4PerspRH(Math.PI/4,panel.getHeight()/(float)panel.getWidth(),0.1,20.);
        orthogonal = new Mat4OrthoRH((float)panel.getWidth()/100,(float)panel.getHeight()/100,0.1,20.);
        /*
        axisX = new Axis('x');
        axisX.setColor(0xFF0000);
        axisY = new Axis('y');
        axisY.setColor(0x00FF00);
        axisZ = new Axis('z');
        axisZ.setColor(0x0000FF);
         */
        current = perspective;
        solids = new ArrayList<>();
        //objekty
    }
    //vykreslování dříve vykreslených čar
    public void repaintCanvas(){
        //clear(0x000000);

        panel.repaint();
    }
}