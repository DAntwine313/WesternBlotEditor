import java.awt.*;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.im4java.core.Operation;
import org.im4java.script.AbstractScriptGenerator;

// for use with mouse clicks

import java.io.*;
import java.io.IOException;
import java.util.Calendar;

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

        // Create Graphical Interface (GUI)
       //resize button
        buttonResize = new JButton("Resize");
        buttonResize.addActionListener(this);
        
      //EdgeDetector button
        buttonEdgeDetector = new JButton("EdgeDetector");
        buttonEdgeDetector.addActionListener(this);
        
      //Invert button
        buttonInvert = new JButton("Invert");
        buttonInvert.addActionListener(this);
        
       //Bright/Contrast button
        buttonBrightnessContrast = new JButton("Bright/Contrast");
        buttonInvert.addActionListener(this);
        
      //Reset button
        buttonReset = new JButton("Reset");
        buttonReset.addActionListener(this);
        
        panelBottom = new JPanel();
        
        //Might not need panel buttons on bottom if we allow user to choose from menu bar 
        
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
        JMenu tools = new JMenu("Image");
        // create menuitems
        JMenuItem file1 = new JMenuItem("Open File");
        Image img = new ImageIcon(this.getClass().getResource("Open-file-icon.png")).getImage();
        file1.setIcon(new ImageIcon(img));
        
        JMenuItem file2 = new JMenuItem("Save");
        Image save = new ImageIcon(this.getClass().getResource("Save-icon.png")).getImage();
        file2.setIcon(new ImageIcon(save));
        
        JMenuItem file3 = new JMenuItem("Save As");
        Image saveas = new ImageIcon(this.getClass().getResource("Save-icon.png")).getImage();
        file3.setIcon(new ImageIcon(saveas));
        
        JMenuItem edit1 = new JMenuItem("Reset");
        JMenuItem tools1 = new JMenuItem("Brightness");
        JMenuItem tools2 = new JMenuItem("Resize");
        // add menu items to menu
        file.add(file1);
        
        JSeparator separator_1 = new JSeparator();
        file.add(separator_1);
        file.add(file2);
        file.add(file3);
        edit.add(edit1);
        tools.add(tools1);
        
        JMenuItem mntmNewMenuItem_1 = new JMenuItem("Contrast");
        tools.add(mntmNewMenuItem_1);
        tools.add(tools2);
        // add menu to menu bar
        mb.add(file);
        
        JSeparator separator = new JSeparator();
        file.add(separator);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
        Image exit = new ImageIcon(this.getClass().getResource("exit-icon.png")).getImage();
        mntmNewMenuItem.setIcon(new ImageIcon(exit));
        mntmNewMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(JFrame.EXIT_ON_CLOSE);
        	}
        });
        file.add(mntmNewMenuItem);
        mb.add(edit);
        mb.add(tools);
        
        JMenuItem mntmNewMenuItem_2 = new JMenuItem("Invert");
        tools.add(mntmNewMenuItem_2);
        // add menubar to panel
        menuBar.setLayout(new BorderLayout());
        menuBar.add(mb);
        
        JMenu mnNewMenu = new JMenu("Analyze");
        mb.add(mnNewMenu);
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
                // creates empty bash script
                writer = new FileWriter("bash_output.sh");
                // header for bash script
                writer.write(
                        "#!/bin/bash\n" +
                                "#-------------------------------------------------------\n" +
                                "# Bash-script autogenerated by im4java\n" +
                                "# at " + Calendar.getInstance().getTime() + "\n" +
                                "#-------------------------------------------------------\n"
                );
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
        }
    }