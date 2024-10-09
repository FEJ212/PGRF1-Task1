package controller;

import model.Line;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerGraphics;
import rasterize.LineRasterizerTrivial;
import view.Panel;

import java.awt.event.*;

public class Controller2D {
    private final Panel panel;
    private LineRasterizer lineRasterizer;
    //private Line line2, pLine;

    public Controller2D(Panel panel){
        this.panel = panel;

        lineRasterizer = new LineRasterizerTrivial(panel.getRaster());

        initListener();

        panel.addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
              cross(e.getX(),e.getY());
          }
          public void mouseReleased(MouseEvent e) {
              cross(e.getX(),e.getY());
          }
        });
    }

    private void initListener(){
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                panel.clear();
                Line line = new Line(panel.getWidth()/2, panel.getHeight()/2,e.getX(),e.getY());
                lineRasterizer.drawLine(line);
                cross(panel.getWidth()/2, panel.getHeight()/2);
                panel.repaint();
                /*if(pLine!=line2){
                    lineRasterizer = new LineRasterizerGraphics(panel.getRaster(), 0x000000);
                    lineRasterizer.drawLine(pLine);
                    panel.repaint();
                    lineRasterizer = new LineRasterizerGraphics(panel.getRaster());
                    line2 = new Line(panel.getWidth()/2, panel.getHeight()/2,e.getX(),e.getY());
                    lineRasterizer.drawLine(line2);
                    panel.repaint();
                    pLine = line2;
                }else {
                    lineRasterizer = new LineRasterizerGraphics(panel.getRaster());
                    line2 = new Line(640,360,e.getX(),e.getY());
                    lineRasterizer.drawLine(line2);
                    panel.repaint();
                    pLine = line2;
                }*/
                //panel.getRaster().setRGB(e.getX(),e.getY(),0x00ff00);
            }
        });
        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (keyChar=='t'){
                    lineRasterizer = new LineRasterizerTrivial(panel.getRaster());
                    System.out.println("Triviální vykreslení");
                } else if (keyChar=='g') {
                    lineRasterizer = new LineRasterizerGraphics(panel.getRaster());
                    System.out.println("Graphics vyhreslení");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void cross(int x, int y){
        panel.getRaster().setRGB(x, y, 0xff0000);
        for(int i = 1; i < 3; i++){
            panel.getRaster().setRGB(x+i,y+i,0xff0000);
            panel.getRaster().setRGB(x-i,y-i,0xff0000);
            panel.getRaster().setRGB(x-i,y+i,0xff0000);
            panel.getRaster().setRGB(x+i,y-i,0xff0000);
        }
        panel.repaint();
    }
}
