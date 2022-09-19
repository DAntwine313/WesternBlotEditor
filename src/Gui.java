import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.IM4JavaException;


public class Gui extends JFrame implements ActionListener
{
    private JPanel panelBottom, menuBar;
    private JLabel imgLabel;
    private JButton buttonResize, buttonCrop, buttonInvert, buttonBrightnessContrast, buttonReset;

    private JMenu file, edit, tools;
    private JMenuItem fileOpen, fileSave, fileSaveAs, editReset, toolsCrop, toolsBC, toolsResize;
    private JMenuBar mb;
    private JScrollPane imageScrollPane;
    private Container l_c;
    private DisplayImage g = null;
    private String imagePath = null;


    public Gui() throws IOException {
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

        // Add to GUI
        l_c = getContentPane();
        l_c.setLayout(new BorderLayout());
        l_c.add(menuBar, BorderLayout.NORTH);
        l_c.add(panelBottom, BorderLayout.WEST);
        setVisible(true);
        pack();
    }

    public static void main(String args[]) throws IOException {
        Gui t = new Gui();
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == fileOpen){
            g = new DisplayImage();
            try {
                if(imageScrollPane != null)
                    l_c.remove(imageScrollPane);
                imageScrollPane = new DisplayImage().getDisplayImage();
                l_c.add(imageScrollPane);
                l_c.revalidate();
                imagePath = g.imagePath;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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
                op.addImage();
                op.resize(200, 300);
                op.addImage(imagePath.replace(".jpg", "_resize.jpg"));
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
