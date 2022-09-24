import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.IM4JavaException;


public class Gui extends JFrame implements ActionListener
{
    private JPanel panelBottom, menuBar;
    private JButton buttonResize, buttonCrop, buttonMonochrome, buttonBrightnessContrast, buttonReset;

    private JMenu file, edit, tools;
    private JMenuItem fileOpen, fileSave, fileSaveAs, editReset, toolsCrop, toolsBC, toolsResize, toolsMonochrome;
    private JMenuBar mb;
    private JScrollPane imageScrollPane;
    private Container l_c;
    private String imagePath, newImage, originalImage;


    public Gui() throws IOException {
        super("UMGC Western Blot Editor");

        // Create Graphical Interface
          // Buttons and Listeners
        buttonResize = new JButton("Resize");
        buttonResize.addActionListener(this);
        buttonCrop = new JButton("Crop");
        buttonCrop.addActionListener(this);
        buttonMonochrome = new JButton("Monochrome");
        buttonMonochrome.addActionListener(this);
        buttonBrightnessContrast = new JButton("Bright/Contrast");
        buttonBrightnessContrast.addActionListener(this);
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
        toolsMonochrome = new JMenuItem("Monochrome");
        toolsMonochrome.addActionListener(this);
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
        tools.add(toolsMonochrome);
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
        panelBottom.add(buttonMonochrome);
        panelBottom.add(buttonBrightnessContrast);
        panelBottom.add(buttonReset);

        // Load Initial Image ** FIND A BETTER WAY TO DO THIS!! ***
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG, GIF, PNG Images", "jpg", "gif", "png");
        chooser.setFileFilter(filter);
        JTextField textFieldImagePath = new JTextField(100);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            textFieldImagePath.setText(chooser.getSelectedFile().getAbsolutePath());
        }
        imagePath = textFieldImagePath.getText();
        originalImage = imagePath;
        File imgFile = new File(textFieldImagePath.getText());
        BufferedImage img = ImageIO.read(imgFile);
        ImageIcon icon = new ImageIcon(img);
        JLabel image = new JLabel(icon);
        imageScrollPane = new JScrollPane(image);


        // Add to GUI
        l_c = getContentPane();
        l_c.setLayout(new BorderLayout());
        l_c.add(menuBar, BorderLayout.NORTH);
        l_c.add(panelBottom, BorderLayout.WEST);
        l_c.add(imageScrollPane, BorderLayout.EAST);
        setVisible(true);
        pack();
    }

    public static void main(String args[]) throws IOException {
        Gui t = new Gui();
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // JSlider optionPane For Brightness Contrast
    static JSlider getSlider(final JOptionPane optionPane) {
        JSlider slider = new JSlider(-127, 127, 0);
        slider.setMajorTickSpacing(50);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        ChangeListener changeListener = changeEvent -> {
            JSlider theSlider = (JSlider) changeEvent.getSource();
            if (!theSlider.getValueIsAdjusting()) {
                optionPane.setInputValue(new Integer(theSlider.getValue()));
            }
        };
        slider.addChangeListener(changeListener);
        return slider;
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == fileOpen){
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG, GIF, PNG Images", "jpg", "gif", "png");
            chooser.setFileFilter(filter);
            JTextField textFieldImagePath = new JTextField(100);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                textFieldImagePath.setText(chooser.getSelectedFile().getAbsolutePath());
            }
            imagePath = textFieldImagePath.getText();
            // Set original path to use with reset
            originalImage = imagePath;
            File imgFile = new File(imagePath);
            BufferedImage img;
            try {
                img = ImageIO.read(imgFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            ImageIcon icon = new ImageIcon(img);
            JLabel image = new JLabel(icon);
            l_c.remove(imageScrollPane);
            imageScrollPane = new JScrollPane(image);
            l_c.add(imageScrollPane);
            l_c.revalidate();
        }
        else if(e.getSource() == fileSave){

        }
        else if(e.getSource() == fileSaveAs){

        }
        else if(e.getSource() == buttonReset | e.getSource() == editReset){
            imagePath = originalImage;
            File imgFile = new File(imagePath);
            BufferedImage img;
            try {
                img = ImageIO.read(imgFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            ImageIcon icon = new ImageIcon(img);
            JLabel image = new JLabel(icon);
            l_c.remove(imageScrollPane);
            imageScrollPane = new JScrollPane(image);
            l_c.add(imageScrollPane);
            l_c.revalidate();
        }

        else if(e.getSource() == buttonCrop | e.getSource() == toolsCrop){

        }

        else if(e.getSource() == buttonBrightnessContrast | e.getSource() == toolsBC){
            // Pop up window for brightness contrast params
            JPanel bcPanel = new JPanel();
            bcPanel.setLayout(new GridLayout(2,1));
            JOptionPane optionBPane = new JOptionPane();
            JSlider brightSlider = getSlider(optionBPane);
            JTextField brightness = new JTextField(3);
            brightness.setText((String) optionBPane.getInputValue());
            optionBPane.setMessage(new Object[] { "Select a Brightness: ", brightness, brightSlider });
            JOptionPane optionCPane = new JOptionPane();
            JSlider contrastSlider = getSlider(optionCPane);
            optionCPane.setMessage(new Object[] { "Select a Contrast: ", contrastSlider });
            bcPanel.add(optionBPane);
            bcPanel.add(optionCPane);
            JOptionPane.showConfirmDialog(null, bcPanel,
                    "Brightness / Contrast", JOptionPane.OK_CANCEL_OPTION);
            // ImageMagick Call
            ConvertCmd cmd = new ConvertCmd();
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            op.brightnessContrast((double) brightSlider.getValue(), (double) contrastSlider.getValue());
                // Label new image with update
            newImage = imagePath.replace(".jpg", "_brightness"+brightSlider.getValue()+"&contrast"+contrastSlider.getValue()+".jpg");
                // ImageMagick write newImage
            op.addImage(newImage);
            try {
                cmd.run(op);
            } catch (IOException | IM4JavaException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
                // Update image path and reload image in JscrollPane
            imagePath = newImage;
            File imgFile = new File(imagePath);
            BufferedImage img;
            try {
                img = ImageIO.read(imgFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            ImageIcon icon = new ImageIcon(img);
            JLabel image = new JLabel(icon);
            l_c.remove(imageScrollPane);
            imageScrollPane = new JScrollPane(image);
            l_c.add(imageScrollPane);
            l_c.revalidate();
        }

        else if (e.getSource() == buttonResize | e.getSource() == toolsResize) {
            try {
                // resize popup
                JTextField width = new JTextField(5);
                JTextField height = new JTextField(5);
                JPanel resizePanel = new JPanel();
                resizePanel.setLayout(new GridLayout(2,2));
                resizePanel.add(new JLabel("Width: "));
                resizePanel.add(width);
                resizePanel.add(new JLabel("Height: "));
                resizePanel.add(height);
                JOptionPane.showConfirmDialog(null, resizePanel,
                        "Resize Dimensions", JOptionPane.OK_CANCEL_OPTION);
                // ImageMagick Call
                ConvertCmd cmd = new ConvertCmd();
                // create the operation, add images and operators/options
                IMOperation op = new IMOperation();
                op.addImage(imagePath);
                op.resize(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
                newImage = imagePath.replace(".jpg", "_resize.jpg");
                op.addImage(newImage);
                // execute the operation
                cmd.run(op);
                imagePath = newImage;
                File imgFile = new File(imagePath);
                BufferedImage img;
                try {
                    img = ImageIO.read(imgFile);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                ImageIcon icon = new ImageIcon(img);
                JLabel image = new JLabel(icon);
                l_c.remove(imageScrollPane);
                imageScrollPane = new JScrollPane(image);
                l_c.add(imageScrollPane);
                l_c.revalidate();
            }
            catch (InterruptedException | IOException | IM4JavaException except){
                except.printStackTrace();
            }
        }

        else if(e.getSource() == buttonMonochrome | e.getSource() == toolsMonochrome){
            // ImageMagick Call
            ConvertCmd cmd = new ConvertCmd();
            // create the operation, add images and operators/options
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            op.monochrome();
            newImage = imagePath.replace(".jpg", "_monochrome.jpg");
            op.addImage(newImage);
            // execute the operation
            try {
                cmd.run(op);
            } catch (IOException | InterruptedException | IM4JavaException ex) {
                throw new RuntimeException(ex);
            }
            imagePath = newImage;
            File imgFile = new File(imagePath);
            BufferedImage img;
            try {
                img = ImageIO.read(imgFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            ImageIcon icon = new ImageIcon(img);
            JLabel image = new JLabel(icon);
            l_c.remove(imageScrollPane);
            imageScrollPane = new JScrollPane(image);
            l_c.add(imageScrollPane);
            l_c.revalidate();
        }
    }
        }
