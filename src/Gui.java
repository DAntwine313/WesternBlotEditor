import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;

// for use with mouse clicks
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.*;
import java.io.IOException;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.im4java.core.ConvertCmd;
import org.im4java.core.DisplayCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.IM4JavaException;


public class Gui extends JFrame implements ActionListener
{
    private JPanel panelBottom;
    private JLabel imgLabel;
    private JButton buttonResize, buttonEdgeDetector, buttonInvert, buttonBrightnessContrast, buttonReset;
    private JTextField textFieldImagePath;



    public Gui()
    {
        super("UMGC Western Blot Editor");

        // Create Graphical Interface
        buttonResize = new JButton("Resize");
        buttonResize.addActionListener(this);
        buttonEdgeDetector = new JButton("EdgeDetector");
        buttonEdgeDetector.addActionListener(this);
        buttonInvert = new JButton("Invert");
        buttonInvert.addActionListener(this);
        buttonBrightnessContrast = new JButton("Bright/Contrast");
        buttonInvert.addActionListener(this);
        buttonReset = new JButton("Reset");
        buttonReset.addActionListener(this);

        panelBottom = new JPanel();
        panelBottom.add(buttonResize);
        panelBottom.add(buttonEdgeDetector);
        panelBottom.add(buttonInvert);
        panelBottom.add(buttonBrightnessContrast);
        panelBottom.add(buttonReset);

        // ImagePanel

        Container l_c = getContentPane();
        l_c.setLayout(new BorderLayout());
        l_c.add(panelBottom, BorderLayout.SOUTH);

        // Load image

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
        textFieldImagePath = new JTextField(100);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            textFieldImagePath.setText(chooser.getSelectedFile().getAbsolutePath());
        }
        String path = textFieldImagePath.getText();
        JLabel imgLabel = new JLabel(new ImageIcon(path));

        l_c.add(imgLabel, BorderLayout.CENTER);
        setVisible(true);
        pack();
    }

    public static void main(String args[]){
        Gui t = new Gui();
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == buttonResize){
            try {
                // create command
                ConvertCmd cmd = new ConvertCmd();
                // create the operation, add images and operators/options
                IMOperation op = new IMOperation();
                op.addImage(textFieldImagePath.getText());
                op.resize(200, 300);
                op.addImage(textFieldImagePath.getText().replace(".jpg", "_resize.jpg"));
                // execute the operation
                cmd.run(op);
            }
            catch (InterruptedException | IOException | IM4JavaException except){
                except.printStackTrace();
            }
        }

        else if(e.getSource() == buttonEdgeDetector){

        }

        else if(e.getSource() == buttonInvert){

        }

        else if(e.getSource() == buttonBrightnessContrast){

        }
    }
}