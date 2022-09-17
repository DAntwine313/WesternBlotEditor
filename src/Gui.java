import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// for use with mouse clicks

import java.io.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.im4java.core.*;


public class Gui extends JFrame implements ActionListener {
    private JPanel panelBottom, menuBar;
    private JLabel imgLabel;
    private JButton buttonResize;
    private JButton buttonEdgeDetector;
    private JButton buttonInvert;
    private JButton buttonBrightnessContrast;
    private JButton buttonReset;
    private JTextField textFieldImagePath;


    public Gui() {
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
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            textFieldImagePath.setText(chooser.getSelectedFile().getAbsolutePath());
        }
        String path = textFieldImagePath.getText();
        JLabel imgLabel = new JLabel(new ImageIcon(path));

        l_c.add(imgLabel, BorderLayout.CENTER);

        // Menu
        menuBar = new JPanel();
        // create a menubar
        JMenuBar mb = new JMenuBar();
        // create a menu
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu tools = new JMenu("Tools");
        // create menuitems
        JMenuItem file1 = new JMenuItem("Open");
        JMenuItem file2 = new JMenuItem("Save");
        JMenuItem file3 = new JMenuItem("Save As");
        JMenuItem edit1 = new JMenuItem("Reset");
        JMenuItem tools1 = new JMenuItem("Brightness/Contrast");
        JMenuItem tools2 = new JMenuItem("Resize");
        // add menu items to menu
        file.add(file1);
        file.add(file2);
        file.add(file3);
        edit.add(edit1);
        tools.add(tools1);
        tools.add(tools2);
        // add menu to menu bar
        mb.add(file);
        mb.add(edit);
        mb.add(tools);
        // add menubar to panel
        menuBar.setLayout(new BorderLayout());
        menuBar.add(mb);
        l_c.add(menuBar, BorderLayout.NORTH);
        setVisible(true);
        pack();
    }

    public static void main(String args[]) throws FileNotFoundException {
        Gui t = new Gui();
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {

        FileWriter writer = null;
        {
            try {
                writer = new FileWriter("bash_output.sh");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (e.getSource() == buttonResize) {
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

                } catch (InterruptedException | IOException | IM4JavaException except) {
                    except.printStackTrace();
                }
                try {
                    writer.write("\nThe RESIZE line");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } else if (e.getSource() == buttonEdgeDetector) {

            } else if (e.getSource() == buttonInvert) {

            } else if (e.getSource() == buttonBrightnessContrast) {
                }
            }
/*
    public static void BashScriptGenerator()  {

        // create new empty shell script file
        // write commands to shell script
        PrintWriter writer = null;

        {
            try {
                writer = new PrintWriter("/Users/pfspooter/Desktop/bash_output.sh");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
                writer.println("The first line");
                writer.close();
            }
        }

 */
        }
    }