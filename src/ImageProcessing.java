/* Compile this before main */


import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.awt.geom.*;

public class ImageProcessing extends JFrame{

    public static Vector allimages=new Vector();
    public static int winNumber=0;
    public static int currentWin=0;
    public static int isToolsWinActive=1;
    public static int isInfoWinActive=1;
    public static int WhoOperationActive=1;
    public static int infoX=0;
    public static int infoY=0;
    public static int openFrameCount = 0;
    public static JTextField txtX ;
    public static JTextField  txtY;
    public static JLabel lbtxtcurd,lbtxtwin,lbrotX,lbrotY;
    public static JLabel fromx1,fromx2,fromy1,fromy2;
    public static int RGBr=0xFF;
    public static int RGBg=0;
    public static int RGBb=0;


    public ImageProcessing(String str)
    {

        super(str);

    }

    public int getRed( int p ) {
        return ((p>>16)&0xFF);
    }
    public int getGreen( int p ) {
        return ((p>>8)&0xFF);
    }
    public int getBlue( int p ) {
        return (p & 0xff);
    }

    public int createRGB(int r, int g, int b){
        return new Color(r, g, b).getRGB();
    }

    String seeColor(String c){
        String temp="";
        if (c.indexOf("-")!=-1 )
            temp="-";

        if((c.indexOf("R")>=0) || (c.indexOf("r")>=0) )
            temp+="charR";
        else if((c.indexOf("G")>=0) || (c.indexOf("g")>=0) )
            temp+="charG";
        else if((c.indexOf("B")>=0) || (c.indexOf("b")>=0) )
            temp+="charB";
        else {  temp="num";
        }
        return temp;
    }

    int getNumber(int c,String ch,String number){
        int temp=0;
        if(ch.equals("num")){
            temp=Integer.parseInt(number);
        }else if(ch.indexOf("charR")>=0){
            temp=getRed(c);
        }else if(ch.indexOf("charG")>=0){
            temp=getGreen(c);
        }else if(ch.indexOf("charB")>=0){
            temp=getBlue(c);
        }

        if(ch.indexOf("-")>=0)
            temp=255-temp;
        return temp;
    }


    BufferedImage chabgeColor(BufferedImage image,String parR,String parG,String parB)
    {
        System.out.println("**R= "+parR+" **G= "+parG+" **B="+parB);
        int w=image.getWidth(this);
        int h=image.getHeight(this);
        int c,red,green,blue;
        String cR,cG,cB;
        BufferedImage newImage=new BufferedImage(w,h,1);
        cR=seeColor(parR);
        cG=seeColor(parG);
        cB=seeColor(parB);
        System.out.println("R= "+cR+" G= "+cG+" B="+cB);
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                c=image.getRGB(x,y);
                red=getNumber(c,seeColor(parR),parR);
                green=getNumber(c,seeColor(parG),parG);
                blue=getNumber(c,seeColor(parB),parB);
                newImage.setRGB(x,y,createRGB(red,green,blue));
            }
        }
        return newImage;
    }

    BufferedImage setColorToXY(BufferedImage image,int Dx,int Dy,int Dwidth,int Dhight){
        int w=image.getWidth(this);
        int h=image.getHeight(this);
        int c,red,green,blue;
        BufferedImage newImage=new BufferedImage(w,h,1);
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                c=image.getRGB(x,y);
                red=getRed(c);
                green=getGreen(c);
                blue=getBlue(c);
                if((y<=Dhight) &&(y>=Dy) && (x<=Dwidth) &&(x>=Dx)  )
                    newImage.setRGB(x,y,createRGB(RGBr,RGBg,RGBb));

                else   newImage.setRGB(x,y,createRGB(red,green,blue));
            }
        }
        return newImage;
    }

    BufferedImage meanFilter1(BufferedImage img){
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        BufferedImage subimage ;
        BufferedImage img2 = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        int data []  = new int[9];
        int red  []  = new int[9];
        int green[]  = new int[9];
        int blue []  = new int[9];
        for (int y = 1; y < h - 1; y++)
            for (int x = 1; x < w - 1; x++) {
                subimage = img.getSubimage(x - 1, y - 1, 3, 3);
                subimage.getRGB(0, 0, 3, 3, data, 0, 3);
                for (int k = 0; k < 9; k++){
                    Color c = new Color(data[k]);
                    red   [k] = c.getRed();
                    green [k] = c.getGreen();
                    blue  [k] = c.getBlue();
                }
                int r = meanValue(red);
                int g = meanValue(green);
                int b = meanValue(blue);
                Color c = new Color(r, g, b);
                img2.setRGB(x, y, c.getRGB());
            }
        return img2;
    }



    BufferedImage medianFilter(BufferedImage img){
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        BufferedImage subimage ;
        BufferedImage img2 = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        int data []  = new int[9];
        int red  []  = new int[9];
        int green[]  = new int[9];
        int blue []  = new int[9];
        for (int y = 1; y < h - 1; y++)
            for (int x = 1; x < w - 1; x++) {
                subimage = img.getSubimage(x - 1, y - 1, 3, 3);
                subimage.getRGB(0,0,3,3,data,0,3);
                for (int k=0;k<9;k++){
                    Color c= new Color(data[k]);
                    red   [k] = c.getRed();
                    green [k] = c.getGreen();
                    blue  [k] = c.getBlue();
                }
                int r = medianValue(red);
                int g = medianValue(green);
                int b = medianValue(blue);
                Color c = new Color(r, g, b);
                img2.setRGB(x, y, c.getRGB());
            }
        return img2;
    }

    int meanValue(int a[])
    {
        int sum = 0;
        for(int i = 0; i < 9; i++)
            sum += a[i];
        return sum / 9;
    }



    int medianValue(int a[]){
        int temp;
        for(int i=0;i<8;i++)
            for(int j=i+1;j<9;j++)
                if (a[j]<a[i]){
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
        return a[4];
    }


    BufferedImage scale(BufferedImage image1,int rx,int ry){

        AffineTransform tx = new AffineTransform();
        System.out.println("The Value of rx,ry is " +rx);
        tx.scale(rx,ry);
        AffineTransformOp op = new AffineTransformOp(tx, null);
        return  op.filter(image1, null);
    }
} 