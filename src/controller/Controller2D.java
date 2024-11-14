package controller;

import clip.Clipper;
import fill.*;
import model.Line;
import model.Pentagon;
import model.Point;
import model.Polygon;
import rasterize.*;
import view.Panel;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

//Třída pro uživatelské ovládání
public class Controller2D {
    private final Panel panel;
    private SeedFiller seedFiller;
    private ScanLineFiller scanLineFiller;
    private LineRasterizer lineRasterizer; //rasterizer čar
    private PolygonRasterizer polygonRasterizer; //rasterizer polygonů
    private PointRasterizer pointRasterizer; //rasterizer bodů
    private int startX, startY; // Bod kdy byla myš stisknuta
    private Polygon polygon = new Polygon(); //zaznamenávání bodů aktuálního polygonu
    private ArrayList<Polygon> polygons = new ArrayList(); //zaznamenávání nakreslených polygonů (bez aktuálního)
    private ArrayList<Line> lines = new ArrayList(), linesThick = new ArrayList(); //zaznamenávání nakreslených normáních a tlustých čar
    private ArrayList<Polygon> polygonsThick = new ArrayList(); //zaznamenávání nakreslených tučných polygonů (bez aktuálního)
    private ArrayList<Point> points = new ArrayList<>(); //zaznamenávání nakreslených bodů
    private ArrayList<Point> pointsThick = new ArrayList<>(); //zaznamenávání nakreslených tučných bodů
    private boolean thick = false; //Hodnota pro přepínání mezi normálními a tlustými čarami
    private boolean snap = false; //Hodnota pro přepínání mezi normálním režimem, nebo režimem rovných čar
    private boolean scanline = true;
    private Point hPoint = new Point(0,0); //pomocná proměnná pro vypočítání sklonu pro line snapping => držení tlačítka shift
    private Line lineClipper;
    /*
    Modes:
    0: point mode
    1: Line Trivial
    2: Polygon - defaultní režim
    3: Line Graphics
    4: Pentagon
    25: line snapping
    */
    private int mode = 2;

    public Controller2D(Panel panel){
        this.panel = panel;
        // inicializace rasterizerů
        lineRasterizer = new LineRasterizerTrivial(panel.getRaster());
        polygonRasterizer = new PolygonRasterizer(lineRasterizer);
        pointRasterizer = new PointRasterizer(panel.getRaster(), thick);

        lineClipper = new Line(0,panel.getHeight()/2,panel.getWidth(),panel.getHeight()/2);
        repaintCanvas();

        initListener();

        panel.addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
              if(SwingUtilities.isRightMouseButton(e)){
                  seedFiller = new SeedFiller(panel.getRaster(),new Point(e.getX(),e.getY()));
                  seedFiller.fill();
                  panel.repaint();
              } else {
                  //poznamenání počátečních hodnot x a y do proměných
                  startX = e.getX();
                  startY = e.getY();
                  //pokud se tvoří nový polygon, zaznamená se počáteční bod
                  if (mode == 2 && polygon.size() == 0) {
                      polygon.addPoint(new Point(e.getX(), e.getY()));
                  }
              }
          }
          public void mouseReleased(MouseEvent e) {
              if (!(SwingUtilities.isRightMouseButton(e))) {
                  //Vykreslení bodu
                  if (mode == 0) {
                      Point point = new Point(e.getX(), e.getY());
                      pointRasterizer.drawPoint(point);
                      //uložení do vhodného ArrayListu dle stavu tloušťky
                      if (thick) {
                          pointsThick.add(point);
                      } else {
                          points.add(point);
                      }
                  }
                  //vykreslení polyonu
                  if (mode == 2) {
                      Point p = new Point(e.getX(), e.getY());
                      polygon.addPoint(p);
                      panel.clear();
                      repaintCanvas();
                      polygonRasterizer.rasterize(polygon);
                  }
                  if (mode == 2 && polygon.size() >= 3) {
                      polygonRasterizer.rasterize(polygon);
                      if (scanline){
                          scanLineFiller = new ScanLineFiller(polygon, lineRasterizer, polygonRasterizer);
                          scanLineFiller.fill();
                      }
                  }
                  //vykreslování úseček
                  if (mode == 1 || mode == 3) {
                      //režim pro vodorovné, svyslé a diagonální úsečky
                      if (snap) {
                          Line line = new Line(startX, startY, hPoint.getX(), hPoint.getY());
                          //roztřízení tlustých a normálních úseček do odpovídajícího ArrayListu pro překreslování
                          if (thick) {
                              linesThick.add(line);
                          } else {
                              lines.add(line);
                          }
                          //normální režim vykreslování úsečky
                      } else {
                          Line line = new Line(startX, startY, e.getX(), e.getY());
                          //roztřízení tlustých a normálních úseček do odpovídajícího ArrayListu pro překreslování
                          if (thick) {
                              linesThick.add(line);
                          } else {
                              lines.add(line);
                          }
                      }
                  } else if (mode == 4) {
                      Pentagon pentagon = new Pentagon();
                      pentagon.calculate(new Point(startX, startY), new Point(e.getX(), e.getY()));
                      Polygon polygon = new Polygon(pentagon.getPolygon().getPoints());
                      polygonRasterizer.rasterize(polygon);
                      polygons.add(polygon);
                  }
                  panel.repaint();
              }
          }
          @Override
          public void mouseClicked(MouseEvent e) {

          }
        });
    }

    private void initListener(){
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            //vykreslování čar při tažení myší pro lepší vizualizaci výsledné čáry
            public void mouseDragged(MouseEvent e) {
                if (!(SwingUtilities.isRightMouseButton(e))) {
                    panel.clear();
                    repaintCanvas(); //funkce pro vykreslení předchozích čar
                    //vykreslování bodu
                    if (mode == 0) {
                        Point point = new Point(e.getX(), e.getY());
                        pointRasterizer.drawPoint(point);
                        //vykreslování netučné čáry dle Trivial algoritmu
                    } else if (mode == 1 && !thick) {
                        lineRasterizer = new LineRasterizerTrivial(panel.getRaster());
                        //normální vykreslování
                        if (!snap) {
                            Line line = new Line(startX, startY, e.getX(), e.getY());
                            lineRasterizer.drawLine(line);
                            //line snapping vykreslování
                        } else {
                            hPoint = snapping(new Point(startX, startY), new Point(e.getX(), e.getY()));
                            Line line = new Line(startX, startY, hPoint.getX(), hPoint.getY());
                            lineRasterizer.drawLine(line);
                        }
                        //vykreslování netučné čáry dle algoritmu graphics
                    } else if (mode == 3 && !thick) {
                        lineRasterizer = new LineRasterizerGraphics(panel.getRaster());
                        //normální vykreslování
                        if (!snap) {
                            Line line = new Line(startX, startY, e.getX(), e.getY());
                            lineRasterizer.drawLine(line);
                            //vykreslování pomocí line snapping
                        } else {
                            hPoint = snapping(new Point(startX, startY), new Point(e.getX(), e.getY()));
                            Line line = new Line(startX, startY, hPoint.getX(), hPoint.getY());
                            lineRasterizer.drawLine(line);
                        }
                        //vykreslování tučné čáry
                    } else if (mode == 1 || mode == 3 && thick) {
                        //normální vykreslování
                        if (!snap) {
                            lineRasterizer = new LineRasterizerThick(panel.getRaster());
                            Line line = new Line(startX, startY, e.getX(), e.getY());
                            lineRasterizer.drawLine(line);
                            //vykreslování pomocí line snapping
                        } else {
                            lineRasterizer = new LineRasterizerThick(panel.getRaster());
                            hPoint = snapping(new Point(startX, startY), new Point(e.getX(), e.getY()));
                            Line line = new Line(startX, startY, hPoint.getX(), hPoint.getY());
                            lineRasterizer.drawLine(line);
                        }
                        //vykreslování polygonu
                    } else if (mode == 2) {
                        //znovuvykreslování již nakreslených částí polygonu
                        if (polygon.size() >= 2) polygonRasterizer.rasterize(polygon);
                        //nastavení rasterizace dle požadavku na tučnost
                        if (thick) {
                            polygonRasterizer.setLineRasterizer(new LineRasterizerThick(panel.getRaster()));
                        } else {
                            polygonRasterizer.setLineRasterizer(new LineRasterizerTrivial(panel.getRaster()));
                        }
                        //vykreslení prvotní čáry (mezi prvními 2 body)
                        if (polygon.size() < 2) {
                            Line line = new Line(startX, startY, e.getX(), e.getY());
                            lineRasterizer.drawLine(line);
                            //vykreslování čar po nakreslení první čáry
                        } else {
                            Line line = new Line(polygon.getPoint(0).getX(), polygon.getPoint(0).getY(), e.getX(), e.getY());
                            lineRasterizer.drawLine(line);
                            int pp = polygon.size(); //pp = Pomocná Proměnná
                            Line line2 = new Line(polygon.getPoint(pp - 1).getX(), polygon.getPoint(pp - 1).getY(), e.getX(), e.getY());
                            lineRasterizer.drawLine(line2);
                        }
                    } else if (mode == 4) {
                        Pentagon pentagon = new Pentagon();
                        pentagon.calculate(new Point(startX, startY), new Point(e.getX(), e.getY()));
                        Polygon polygon = new Polygon(pentagon.getPolygon().getPoints());
                        polygonRasterizer.rasterize(polygon);
                    }
                    panel.repaint();
                }
            }
        });
        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                //Přepínač režimů dle zmáčknutých tlačítek
                switch (keyChar){
                    //lineTrivial
                    case 'l':
                        //uložení rozdělaného polygonu
                        if (thick){
                            polygonsThick.add(polygon);
                        } else {
                            polygons.add(polygon);
                        }
                        polygon = new Polygon();
                        //přepnutí režimu a vypsání zprávy o provedení do konzole
                        mode = 1;
                        lineRasterizer = new LineRasterizerTrivial(panel.getRaster());
                        System.out.println("Triviální vykreslení přímky");
                        break;
                    //lineGraphics
                    case 'g':
                        //uložení rozdělaného polygonu
                        if (thick){
                            polygonsThick.add(polygon);
                        } else {
                            polygons.add(polygon);
                        }
                        polygon = new Polygon();
                        //přepnutí režimu a vypsání zprávy o provedení do konzole
                        mode = 3;
                        lineRasterizer = new LineRasterizerGraphics(panel.getRaster());
                        System.out.println("Graphics vyhreslení přímky");
                        break;
                    //režim polygonu
                    case 'p':
                        //uložení rozdělaného polygonu
                        if (thick){
                            polygonsThick.add(polygon);
                        } else {
                            polygons.add(polygon);
                        }
                        polygon = new Polygon();
                        //přepnutí režimu a vypsání zprávy o provedení do konzole
                        mode = 2;
                        System.out.println("Polygon vykreslování");
                        break;
                    //přepnutí režimu tloušťky
                    case 't':
                        //zapnutí režimu
                        if(!thick){
                            thick = true;
                            if(mode==2){
                                polygons.add(polygon);
                                polygon = new Polygon();
                            }
                            pointRasterizer = new PointRasterizer(panel.getRaster(), thick);
                            polygonRasterizer.setLineRasterizer(new LineRasterizerThick(panel.getRaster()));
                            System.out.println("Čáry budou tučné");
                        //vypnutí režimu
                        } else {
                            thick = false;
                            if(mode==2){
                                polygonsThick.add(polygon);
                                polygon = new Polygon();
                            }
                            pointRasterizer = new PointRasterizer(panel.getRaster(), thick);
                            polygonRasterizer.setLineRasterizer(new LineRasterizerTrivial(panel.getRaster()));
                            System.out.println("Čáry nebudou tučné");
                        }
                        break;
                    //mód bodů
                    case'b':
                        //uložení rozpracovaného polygonu
                        if (thick){
                            polygonsThick.add(polygon);
                        } else {
                            polygons.add(polygon);
                        }
                        polygon = new Polygon();
                        //přepnutí režimu a vypsání zprávy o provedení do konzole
                        mode = 0;
                        System.out.println("Mód bodů");
                        break;
                    //vymazání plátna
                    case'c':
                        polygon = new Polygon();
                        lines.clear();
                        polygons.clear();
                        linesThick.clear();
                        polygonsThick.clear();
                        panel.clear();
                        panel.repaint();
                        break;
                    case'f':
                        //přepnutí režimu a vypsání zprávy o provedení do konzole
                        if (scanline) {
                            scanline = false;
                            System.out.println("ScanLine off");
                        } else {
                            scanline = true;
                            System.out.println("ScanLine on");
                        }
                        break;
                    case'o':
                        if (thick){
                            polygonsThick.add(polygon);
                        } else {
                            polygons.add(polygon);
                        }
                        polygon = new Polygon();
                        mode = 4;
                        System.out.println("pentagon mód");
                        break;
                }
            }
            //detekce zmáčknutého shiftu pro zapnutí line snappingu
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.isShiftDown()){
                    snap=true;
                }
            }
            //vypnutí režimu line snapping po puštění tlačítka shift
            @Override
            public void keyReleased(KeyEvent e) {
                if(!e.isShiftDown()){
                    snap=false;
                }
            }
        });
    }
    //vykreslování dříve vykreslených čar
    public void repaintCanvas(){
        /*
        lineRasterizer.drawLine(lineClipper);
        Clipper clipper = new Clipper();
        List<Point> clipperPoints = new ArrayList<>();
        clipperPoints.add(new Point(lineClipper.getX1(), lineClipper.getY1()));
        clipperPoints.add(new Point(lineClipper.getX2(), lineClipper.getY2()));
        clipper.clip(clipperPoints, polygon.getPoints());
        */
        //vykreslování netučných čar
        for (int i = 0; i < lines.size(); i++) {
            lineRasterizer = new LineRasterizerTrivial(panel.getRaster());
            lineRasterizer.drawLine(lines.get(i));
        }
        //vykreslování netučných polygonů
        for (int i = 0; i<polygons.size(); i++){
            polygonRasterizer.setLineRasterizer(new LineRasterizerTrivial(panel.getRaster()));
            polygonRasterizer.rasterize(polygons.get(i));
        }
        //vykreslování tučných čar
        for (int i = 0; i < linesThick.size(); i++) {
            lineRasterizer = new LineRasterizerThick(panel.getRaster());
            lineRasterizer.drawLine(linesThick.get(i));
        }
        //vykreslování tučných polygonů
        for (int i = 0; i<polygonsThick.size(); i++){
            polygonRasterizer.setLineRasterizer(new LineRasterizerThick(panel.getRaster()));
            polygonRasterizer.rasterize(polygonsThick.get(i));
        }
        //vykreslování bodů
        for (int i = 0; i<points.size(); i++){
            pointRasterizer = new PointRasterizer(panel.getRaster(), false);
            pointRasterizer.drawPoint(points.get(i));
        }
        //vykreslování tučných bodů
        for (int i = 0; i<pointsThick.size(); i++){
            pointRasterizer = new PointRasterizer(panel.getRaster(), true);
            pointRasterizer.drawPoint(pointsThick.get(i));
        }
        //vrácení polygon rasterizeru do správného režimu (při vykreslování tučných polygonů se přepnul na lineThick
        // a bez tohoto kódu by se pro netučné polygonu nevrátil do trivial módu, takže by všechny polygony byli tučné
        // během vykreslování i když to není požadováno)
        if (!thick){
            polygonRasterizer.setLineRasterizer(new LineRasterizerTrivial(panel.getRaster()));
        }
        pointRasterizer = new PointRasterizer(panel.getRaster(), thick);
    }
    //metoda pro vypočítání bodů pro funcki line snapping (shift mód)
    public Point snapping(Point pStart, Point pEnd){
        int x = 0,y = 0;
        //výpočet rozdílu mezi počátečnymi a konečnými body x a y
        int lenght = pEnd.getX()-pStart.getX();
        int height = pEnd.getY()-pStart.getY();
        //výpočet hodnoty pro detekci úhlů při vykreslování úsečky
        float k = (pStart.getY()- pEnd.getY())/(float)(pStart.getX()-pEnd.getX());
        //výpočet vodorovné čáry
        if(k<=(Math.sqrt(3)/3)&&k>(-(Math.sqrt(3)/3))){
            x=pEnd.getX();
            y=pStart.getY();
        //výpočet svislé čáry
        }else if(k<=(-(Math.sqrt(3)))||k>(Math.sqrt(3))){
            x=pStart.getX();
            y=pEnd.getY();
        //výpočet klesající diagonální čáry => F(y)=-x
        } else if (k<=(Math.sqrt(3))&&k>(Math.sqrt(3)/3)) {
            x=pEnd.getX();
            y=pStart.getY()+lenght;
        //výpočet rostoucí diagonální čáry => F(y)=x
        } else if(k<=(-(Math.sqrt(3)/3))&&k>(-Math.sqrt(3))){
            x=pStart.getX()-height;
            y=pEnd.getY();
        }
        return new Point(x,y);
    }
}