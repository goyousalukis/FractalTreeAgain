/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasvg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.ViewBox;
//import org.jfree.graphics2d.canvas.CanvasGraphics2D;
//import org.apache.batik.swing.JSVGCanvas;

/**
 *
 * @author winsock
 */
public class compFractalTree extends JComponent{






    private Preferences prefs;
    
    private int branchLength; 
    private int branchAngle;
    private int treeDepth;
    private int trunkAngle;
    private int leftBranchAngle;
    private int rightBranchAngle;
    private boolean branchLengthRandom;
    private boolean branchAngleRandom;
    private String svgElement;
    public Color treeColor;
    public Color rootColor;
    public Color tipColor;
    public Color backgroundColor;
    public Color berryColor;
    private boolean thickBranches;
    private boolean colorRandom;
    private int colorRandomValue;
    public BasicStroke lineStroke;
    
    
    public compFractalTree() {
    this.setPreferredSize(new Dimension(800,800));
    prefs = Preferences.userNodeForPackage(this.getClass());
    branchLength = prefs.getInt("BRANCHLENGTH", 10);
    branchAngle = prefs.getInt("BRANCHANGLE", 20);
    treeDepth = prefs.getInt("TREEDEPTH", 9);
    trunkAngle = prefs.getInt("TRUNKANGLE", 0);
    leftBranchAngle = prefs.getInt("LEFTBRANCHANGLE", 0);
    rightBranchAngle = prefs.getInt("RIGHTBRANCHANGLE", 0);
    branchLengthRandom = prefs.getBoolean("BRANCHLENGTHRANDOM", false);
    branchAngleRandom = prefs.getBoolean("BRANCHANGLERANDOM", false);
    thickBranches = prefs.getBoolean("THICKBRANCHES", false);
    colorRandom = prefs.getBoolean("COLORRANDOM", false);
    colorRandomValue = prefs.getInt("COLORRANDOMVALUE", 100);
    lineStroke = new BasicStroke(1.0f);
    berryColor = Color.CYAN;
    
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension d = getSize();
        int centerX = d.width / 2;
        int centerY = d.height / 2;  
        SVGGraphics2D gs2 = new SVGGraphics2D(d.width, d.height);
        gs2.setPaint(rootColor);
        this.setBackground(backgroundColor);
        g2.setColor(rootColor);
        drawTree(g2,centerX,centerY+300,trunkAngle-90,treeDepth);
        //drawTree(g2,centerX,centerY,trunkAngle+90,treeDepth);
        drawTree(gs2,centerX,d.height - 25,trunkAngle-90,treeDepth);
        svgElement = gs2.getSVGElement();  
        ViewBox v = new ViewBox(0,0,800,800);
        //CanvasGraphics2d g2D = new CanvasGraphics2D("id");
        
//System.out.println(svgElement);
        
        //g2.setPaint(new GradientPaint(0,0,Color.DARK_GRAY, 1000, 0 ,Color.WHITE));
        //g2.fill(new Ellipse2D.Double(centerX,centerY, 50,50));

        
    }
    
    private void drawTree(Graphics g, int x1, int y1, double angle, int depth) {
        int x,y,bl1,bl2;
        
        if (treeColor == null) treeColor=Color.BLACK;
        if (depth == 0) return;
        Random r = new Random();
        x = -5 + r.nextInt(10);
        y = -5 + r.nextInt(10);
        if (!branchAngleRandom){
            x=0;
            y=0;
        }
        bl1 = -5 + r.nextInt(10);
        bl2 = -5 + r.nextInt(10);
        if (!branchLengthRandom){
            bl1=0;
            bl2=0;
        }

        Random p = new Random();        
        Graphics2D g2 = (Graphics2D) g;
        float t, u, v , w;
        t = p.nextFloat()*10;
        u = p.nextFloat()*10;
        v = p.nextFloat()*10;
        w = p.nextFloat()*10;
        System.out.println(t);
        float dash1[] = {t,u,v,w};
//        BasicStroke bs = new BasicStroke(depth-1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, dash1, 0.0f);
        BasicStroke bs = new BasicStroke(depth-1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, dash1, 0.0f);
        BasicStroke thinLine = new BasicStroke(depth -1);
        BasicStroke circle = new BasicStroke(2.0f);
        BasicStroke bss = new BasicStroke(depth-1, lineStroke.getEndCap(), lineStroke.getLineJoin(), lineStroke.getMiterLimit(), lineStroke.getDashArray(), lineStroke.getDashPhase());
        
        if(thickBranches) g2.setStroke(bs);
        /*    if (depth < (treeDepth-2))
               
            else
                g2.setStroke(thinLine);
        else
            g2.setStroke(new BasicStroke(1));
        */
        
        
        int x2 = x1 + (int) (Math.cos(Math.toRadians(angle+x)) * depth * (branchLength+bl1));
        int y2 = y1 + (int) (Math.sin(Math.toRadians(angle+y)) * depth * (branchLength+bl2));
        
        
        g2.setColor(berryColor);
        if (depth<treeDepth/2) {
            g2.setStroke(bs);
            fillCenteredCircle((Graphics2D) g,x2, y2, (depth*branchLength)/2);
        }
        g.setColor(getColor(rootColor,tipColor,depth));
        if(thickBranches) g2.setStroke(bs);
        g.drawLine(x1, y1, x2, y2);
    //  

        drawTree(g, x2, y2, angle - branchAngle + leftBranchAngle, depth - 1);
        drawTree(g, x2, y2, angle + branchAngle + rightBranchAngle, depth - 1);
    }    
    public void fillCenteredCircle(Graphics2D g, double x, double y, int r) {
        x = x-(r/2);
        y = y-(r/2);
        g.draw(new Ellipse2D.Double(x,y,r,r));
       }
    int getDepth() {
        return prefs.getInt("TREEDEPTH", 9);
    }

    void setDepth(int tmpDepth) {
        treeDepth = tmpDepth;
        prefs.putInt("TREEDEPTH", tmpDepth);
        this.repaint();
    }

    int getBranchLength() {
        return prefs.getInt("BRANCHLENGTH", 10);
    }

    void setBranchLength(int value) {
        prefs.putInt("BRANCHLENGTH", branchLength=value);
        this.repaint();
    }
    int getBranchAngle() {
        return prefs.getInt("BRANCHANGLE", 20);
    }    

    void setBranchAngle(int value) {
        prefs.putInt("BRANCHANGLE", branchAngle=value);
        this.repaint();
    }
    
    String getSVGElement()
    {
        return svgElement;
    }

    int getTrunkAngle() {
        return prefs.getInt("TRUNKANGLE", 0);
    }

    void setTrunkAngle(int value) {
        prefs.putInt("TRUNKANGLE", trunkAngle=value);
        this.repaint();
    }

    int getLeftBranchAngle() {
        return prefs.getInt("LEFTBRANCHANGLE", 0);
    }

    int getRightBranchAngle() {
        return prefs.getInt("RIGHTBRANCHANGLE", 0);
    }

    void setLeftBranchAngle(int value) {
        prefs.putInt("LEFTBRANCHANGLE", leftBranchAngle=value);
        this.repaint();
    }

    void setRighBranchAngle(int value) {
        prefs.putInt("RIGHTBRANCHANGLE", rightBranchAngle=value);
        this.repaint();
    }
    
    boolean getBranchLengthRandom() {
        return prefs.getBoolean("BRANCHLENGTHRANDOM", false);
    }
    void setBranchLengthRandom(boolean selected) {
        prefs.putBoolean("BRANCHLENGTHRANDOM", branchLengthRandom = selected );
        this.repaint();
    }

    boolean getBranchAngleRandom() {
        return prefs.getBoolean("BRANCHANGLERANDOM", false);
    }
    void setBranchAngleRandom(boolean selected) {
       prefs.putBoolean("BRANCHANGLERANDOM", branchAngleRandom = selected );
        this.repaint();
    }    

    boolean getThickBranches() {
        return prefs.getBoolean("THICKBRANCHES", false);
    }

    void setThickBranches(boolean selected) {
        prefs.putBoolean("THICKBRANCHES", thickBranches = selected );
        this.repaint();
    }
     void setColorRandom(boolean selected) {
        prefs.putBoolean("COLORRANDOMVALUE", colorRandom = selected );
        this.repaint();
    }   
     boolean getRandomColor() {
        return prefs.getBoolean("COLORRANDOMVALUE", false);
    }
      void setRandomColorValue(int value) {
        prefs.putInt("COLORRANDOMVALUE", colorRandomValue=value);
        this.repaint();
    }
    Color getColor(Color rootColor, Color tipColor, int iter)
    {
        if (rootColor != null) {
        } else {
            return new Color(0,0,0);
        }
        Random r0 = new Random();
        int x = -(colorRandomValue/2) + r0.nextInt(colorRandomValue);
        int y = -(colorRandomValue/2)+ r0.nextInt(colorRandomValue);
        int z = -(colorRandomValue/2) + r0.nextInt(colorRandomValue);
        int r_1,r_2,g_1,g_2,b_1,b_2, r, g, b;
        r_1 = rootColor.getRed();
        r_2 = tipColor.getRed();
        g_1 = rootColor.getGreen();
        g_2 = tipColor.getGreen();
        b_1 = rootColor.getBlue();
        b_2 = tipColor.getBlue();
        double tmp;
        
       r=(int)((iter*1.0/treeDepth)*(r_1-r_2))+r_2;
       g=(int)((iter*1.0/treeDepth)*(g_1-g_2))+g_2;
       b=(int)((iter*1.0/treeDepth)*(b_1-b_2))+b_2;
       if(colorRandom){
           r=r+x;
           g=g+x;
           z=z+x;
       }
       if (r<0) r=0;
       if (r>255) r=255;
       if (g<0) g=0;
       if (g>255) g=255;
       if (b<0) b=0;
       if (b>255) b=255;
       
       //System.out.println("Iter:" + iter + "R:"+r+" G:"+g+" B:"+b);
       return new Color(r,g,b);
    }

   





    
}
