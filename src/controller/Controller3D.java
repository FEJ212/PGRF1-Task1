package controller;

import rasterize.*;
import render.Renderer;
import solid.*;
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
    private Solid cube;
    private Solid tesseract;
    private Solid pyramid;
    private Axis axisX, axisY, axisZ;
    private Camera camera;
    private Mat4 perspective, orthogonal, current;
    private int startX, startY;
    int outlineColor = 0xFFFFFF;
    int editColor = 0xFF0000;
    private int selectedIndex = -1;
    private String objectId = "";
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

        initScene();

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
                        break;
                    case KeyEvent.VK_ENTER:
                        if(selectedIndex != -1){
                            solids.get(selectedIndex).setColor(outlineColor);
                        }
                        selectedIndex = (selectedIndex+1)%solids.size();
                        solids.get(selectedIndex).setColor(editColor);
                        objectId = solids.get(selectedIndex).getIdentifier();
                        break;
                    case KeyEvent.VK_BACK_SPACE:
                        if (selectedIndex != -1){
                            solids.get(selectedIndex).setColor(outlineColor);
                            selectedIndex = -1;
                            objectId = "";
                        }
                        break;
                }
                repaintCanvas();
                if(objectId.equals("CUBE")){
                    processSolids(cube, e);
                }
                if(objectId.equals("TESSERACT")){
                    processSolids(tesseract, e);
                }
                if(objectId.equals("PYRAMID")){
                    processSolids(pyramid, e);
                }
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

        axisX = new Axis('x');
        axisX.setColor(0xFF0000);
        axisY = new Axis('y');
        axisY.setColor(0x00FF00);
        axisZ = new Axis('z');
        axisZ.setColor(0x0000FF);

        cube = new Cube();
        tesseract = new Tesseract();
        pyramid = new Pyramid();

        current = perspective;
        solids = new ArrayList<>();
        solids.add(cube);
        solids.add(tesseract);
        solids.add(pyramid);
        //objekty
    }
    //vykreslování dříve vykreslených čar
    public void repaintCanvas(){
        panel.clear();
        renderer.setView(camera.getViewMatrix());
        renderer.setProj(current);
        renderer.renderAxis(axisX, axisY, axisZ);
        renderer.renderSolids(solids);
        panel.repaint();
    }

    public void processSolids(Solid solid, KeyEvent keyEvent){
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                solid.setModel(solid.decreaseX());
                break;
            case KeyEvent.VK_RIGHT:
                solid.setModel(solid.increaseX());
                break;
            case KeyEvent.VK_UP:
                solid.setModel(solid.increaseY());
                break;
            case KeyEvent.VK_DOWN:
                solid.setModel(solid.decreaseY());
                break;
            case KeyEvent.VK_SHIFT:
                solid.setModel(solid.increaseZ());
                break;
            case KeyEvent.VK_CONTROL:
                solid.setModel(solid.decreaseZ());
                break;
            case KeyEvent.VK_X:
                solid.setModel(solid.rotateX());
                break;
            case KeyEvent.VK_Y:
                solid.setModel(solid.rotateY());
                break;
            case KeyEvent.VK_Z:
                solid.setModel(solid.rotateZ());
                break;
            case KeyEvent.VK_O:
                solid.setModel(solid.zoomUp());
                break;
            case KeyEvent.VK_L:
                solid.setModel(solid.zoomDown());
            break;
        }
        repaintCanvas();
    }
}