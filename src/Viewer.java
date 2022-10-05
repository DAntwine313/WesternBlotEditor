/* subclass */


import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import javax.swing.event.*;
import java.awt.event.*;

public class Viewer extends JInternalFrame implements InternalFrameListener , MouseMotionListener ,MouseListener    {

    int active=1;
    int winNum=0;
    BufferedImage oldimg;
    public BufferedImage img1;
    PanleDrw p;

    public String path;
    public Viewer(int X,int Y,int Width,int Hieght ,BufferedImage g1,String parPath)

    {
        super("Tools", true, true, true,true);
        Container c=this.getContentPane();
        img1=g1;
        oldimg=g1;

        p=new PanleDrw(img1);
        c.add(p);

        setBounds(X,Y,Width,Hieght);
        setVisible(true);
        addInternalFrameListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        path=parPath;
        winNum=++ImageProcessing.winNumber;
        ImageProcessing.currentWin=winNum;
        this.setTitle(winNum+"");
        ImageProcessing.openFrameCount++;
        ImageProcessing.lbtxtwin.setText(ImageProcessing.openFrameCount+"");

    }

    public void setOldImage(){
        img1=oldimg;
        p.repaintPanelImage(oldimg);
    }
    public void newAply(){
        oldimg=img1;
    }

    public void updateImage(BufferedImage image){
        img1=image;
        p.repaintPanelImage(img1);
    }

    public void internalFrameClosing(InternalFrameEvent e) {
        System.out.println("closing.... 0");
        active=0;
    }

    public void internalFrameClosed(InternalFrameEvent e) {
        ImageProcessing.openFrameCount--;
        ImageProcessing.lbtxtwin.setText(ImageProcessing.openFrameCount+"");
        active=0;
    }

    public void internalFrameOpened(InternalFrameEvent e) {

        ImageProcessing.currentWin=winNum;
        ImageProcessing.lbtxtcurd.setText(winNum+"");
        ImageProcessing.lbtxtwin.setText(ImageProcessing.openFrameCount+"");
        active=1;
    }

    public void internalFrameIconified(InternalFrameEvent e) {

    }

    public void internalFrameDeiconified(InternalFrameEvent e) {

        ImageProcessing.currentWin=winNum;
        ImageProcessing.lbtxtcurd.setText(winNum+"");
        active=1;
    }

    public void internalFrameActivated(InternalFrameEvent e) {
        ImageProcessing.currentWin=winNum;
        ImageProcessing.lbtxtcurd.setText(winNum+"");
        active=1;
    }

    public void internalFrameDeactivated(InternalFrameEvent e) {
        active=0;
    }

    public void mouseDragged(MouseEvent e) {
        ImageProcessing.fromx2.setText( e.getX()+"");
        ImageProcessing.fromy2.setText( e.getY()+"");
    }

    public void mouseReleased(MouseEvent e) {  }

    public void mouseMoved(MouseEvent e) {
        if (active==1) {
            ImageProcessing.txtX.setText( e.getX()+"");
            ImageProcessing.txtY.setText( e.getY()+"");
        }

    }

    public void mousePressed(MouseEvent e)
    {
        ImageProcessing.lbrotX.setText( e.getX()+"");
        ImageProcessing.lbrotY.setText( e.getY()+"");
        ImageProcessing.fromx1.setText( e.getX()+"");
        ImageProcessing.fromy1.setText( e.getY()+"");
        ImageProcessing.fromx2.setText( e.getX()+"");
        ImageProcessing.fromy2.setText( e.getY()+"");
    }
    public void componentMoved(ComponentEvent e) {   }
    public void componentHidden(ComponentEvent e) {    }
    public void componentResized(ComponentEvent e) {    }
    public void componentShown(ComponentEvent e) {    }
    public void mouseEntered(MouseEvent e) {   }
    public void mouseExited(MouseEvent e) {   }
    public void mouseClicked(MouseEvent e) {   }
}
class PanleDrw extends JPanel {
    public BufferedImage pantimg;

    public PanleDrw(BufferedImage  g2){
        pantimg=g2;
        repaint();
    }

    public void repaintPanelImage(BufferedImage  g3){
        pantimg=g3;
        repaint();
    }
    public void paint(Graphics g){
        try{
            g.drawImage(pantimg,0,0,null);
        }catch (Exception e){}
    }
}
