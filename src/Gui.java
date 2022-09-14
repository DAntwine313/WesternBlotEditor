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
    private JPanel panelBottom, menuBar;
    private JLabel imgLabel;
    private JButton buttonResize, buttonCrop, buttonInvert, buttonBrightnessContrast, buttonReset;
    private JTextField textFieldImagePath;

    private JMenu file, edit, tools;
    private JMenuItem fileOpen, fileSave, fileSaveAs, editReset, toolsCrop, toolsBC, toolsResize;
    private JMenuBar mb;


    public Gui()
    {
        super("UMGC Western Blot Editor");

        // Create Graphical Interface
          // Buttons and Listeners
        buttonResize = new JButton("Resize");
        buttonResize.addActionListener(this);
        buttonCrop = new JButton("Crop");
        buttonCrop.addActionListener(this);
        buttonInvert = new JButton("Invert");
        buttonInvert.addActionListener(this);
        buttonBrightnessContrast = new JButton("Bright/Contrast");
        buttonInvert.addActionListener(this);
        buttonReset = new JButton("Reset");
        buttonReset.addActionListener(this);
            // Create menu items
        fileOpen = new JMenuItem("Open");
        fileOpen.addActionListener(this);
        fileSave = new JMenuItem("Save");
        fileSave.addActionListener(this);
        fileSaveAs = new JMenuItem("Save As");
        fileSaveAs.addActionListener(this);
        editReset = new JMenuItem("Reset");
        editReset.addActionListener(this);
        toolsCrop = new JMenuItem("Crop");
        toolsCrop.addActionListener(this);
        toolsBC = new JMenuItem("Brightness/Contrast");
        toolsBC.addActionListener(this);
        toolsResize = new JMenuItem("Resize");
        toolsResize.addActionListener(this);
            // Create Menu and Add Menu Items
        file = new JMenu("File");
        edit = new JMenu("Edit");
        tools = new JMenu("Tools");
        file.add(fileOpen);
        file.add(fileSave);
        file.add(fileSaveAs);
        edit.add(editReset);
        tools.add(toolsCrop);
        tools.add(toolsBC);
        tools.add(toolsResize);
            // Create Menu Bar
        mb = new JMenuBar();
        mb.add(file);
        mb.add(edit);
        mb.add(tools);
            // Add Top Menu Bar
        menuBar = new JPanel();
        menuBar.setLayout(new BorderLayout());
        menuBar.add(mb);
            // Add Buttons
        panelBottom = new JPanel();
        panelBottom.add(buttonResize);
        panelBottom.add(buttonCrop);
        panelBottom.add(buttonInvert);
        panelBottom.add(buttonBrightnessContrast);
        panelBottom.add(buttonReset);
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
        textFieldImagePath = new JTextField(100);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            textFieldImagePath.setText(chooser.getSelectedFile().getAbsolutePath());
        }
        String path = textFieldImagePath.getText();
        ImageIcon image = new ImageIcon(path);
        JLabel imgLabel = new JLabel();
        imgLabel.setIcon(image);
        imgLabel.getIcon();


        // Add to GUI
        Container l_c = getContentPane();
        l_c.setLayout(new BorderLayout());
        l_c.add(menuBar, BorderLayout.NORTH);
        l_c.add(panelBottom, BorderLayout.EAST);
        l_c.add(imgLabel, BorderLayout.CENTER);
        setVisible(true);
        pack();
    }

    public static void main(String args[]){
        Gui t = new Gui();
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == fileOpen){

        }
        else if(e.getSource() == fileSave){

        }
        else if(e.getSource() == fileSaveAs){
        
        }
        else if(e.getSource() == editReset){
  
        }

        else if(e.getSource() == buttonCrop | e.getSource() == toolsCrop){

        }
        else if(e.getSource() == buttonBrightnessContrast | e.getSource() == toolsBC){

        }
        else if (e.getSource() == buttonResize | e.getSource() == toolsResize) {
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

        else if(e.getSource() == buttonInvert){

        }
    }
}
