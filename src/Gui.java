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
import java.util.*;
import java.util.List;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.IM4JavaException;


public class Gui extends JFrame implements ActionListener
{
    private final JButton buttonResize;
    private final JButton buttonEdge;
    private final JButton buttonMonochrome;
    private final JButton buttonInvert;
    private final JButton buttonBrightnessContrast;
    private final JButton buttonReset;
    private final JMenuItem fileOpen;
    private final JMenuItem fileSave;
    private final JMenuItem fileSaveAs;
    private final JMenuItem editReset;
    private final JMenuItem toolsEdge;
    private final JMenuItem toolsBC;
    private final JMenuItem toolsResize;
    private final JMenuItem toolsMonochrome;
    private final JMenuItem toolsInvert;
    private final JMenuItem historyShowHistory;
    private JScrollPane imageScrollPane;
    private final Container l_c;
    private String imagePath;
    private String originalImage;
    List<String> historyList = new ArrayList<>(
            Arrays.asList());


    public Gui() throws IOException {
        super("UMGC Western Blot Editor");

        // Create Graphical Interface
          // Buttons and Listeners
        buttonResize = new JButton("Resize");
        buttonResize.addActionListener(this);
        buttonInvert = new JButton("Invert");
        buttonInvert.addActionListener(this);
        buttonEdge = new JButton("Edge");
        buttonEdge.addActionListener(this);
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
        toolsEdge = new JMenuItem("Edge");
        toolsEdge.addActionListener(this);
        toolsBC = new JMenuItem("Brightness/Contrast");
        toolsBC.addActionListener(this);
        toolsResize = new JMenuItem("Resize");
        toolsResize.addActionListener(this);
        toolsMonochrome = new JMenuItem("Monochrome");
        toolsMonochrome.addActionListener(this);
        toolsInvert = new JMenuItem("Invert");
        toolsInvert.addActionListener(this);
        historyShowHistory = new JMenuItem("Show History");
        historyShowHistory.addActionListener(this);
            // Create Menu and Add Menu Items
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu tools = new JMenu("Tools");
        JMenu history = new JMenu("History");
        history.add(historyShowHistory);
        file.add(fileOpen);
        file.add(fileSave);
        file.add(fileSaveAs);
        edit.add(editReset);
        tools.add(toolsEdge);
        tools.add(toolsBC);
        tools.add(toolsResize);
        tools.add(toolsMonochrome);
        tools.add(toolsInvert);
            // Create Menu Bar
        JMenuBar mb = new JMenuBar();
        mb.add(file);
        mb.add(edit);
        mb.add(tools);
        mb.add(history);
            // Add Top Menu Bar
        JPanel menuBar = new JPanel();
        menuBar.setLayout(new BorderLayout());
        menuBar.add(mb);
            // Add Buttons
        JPanel panelBottom = new JPanel();
        panelBottom.add(buttonResize);
        panelBottom.add(buttonEdge);
        panelBottom.add(buttonMonochrome);
        panelBottom.add(buttonBrightnessContrast);
        panelBottom.add(buttonInvert);
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
        l_c.add(menuBar, BorderLayout.PAGE_START);
        l_c.add(panelBottom, BorderLayout.LINE_START);
        l_c.add(imageScrollPane, BorderLayout.LINE_END);
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
        String newImage;

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
        else if(e.getSource() == historyShowHistory){
            JTextArea textArea = new JTextArea();
            JScrollPane textScrollPane = new JScrollPane(textArea);
            textArea.setEditable(false);
            for (int i=0; i<historyList.size(); i++){
                textArea.append(i+1 + ". "+ historyList.get(i) + "\n");
            }
            JPanel historyPanel = new JPanel();
            historyPanel.setLayout(new GridLayout(2,2));
            historyPanel.add(textScrollPane);
            historyPanel.setSize(700, 500);
            JFrame historyFrame = new JFrame();
            historyFrame.add(historyPanel);
            historyFrame.setVisible(true);
            historyFrame.setTitle("Operations History");
            historyFrame.pack();

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
            historyList.clear();
        }

        else if(e.getSource() == buttonEdge | e.getSource() == toolsEdge){
            // ImageMagick Call
            ConvertCmd cmd = new ConvertCmd();
            // create the operation, add images and operators/options
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            op.edge(1.0);
            newImage = imagePath.replace(".jpg", "_edge.jpg");
            op.addImage(newImage);
            // execute the operation
            try {
                cmd.run(op);
            } catch (IOException | InterruptedException | IM4JavaException ex) {
                throw new RuntimeException(ex);
            }
            historyList.add("Edge");
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
            historyList.add("brightness: "+brightSlider.getValue()+" contrast: "+contrastSlider.getValue());
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
                historyList.add("resize: " + Integer.parseInt(width.getText()) + "x" + Integer.parseInt(height.getText()));
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
            historyList.add("monochrome");
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
        else if(e.getSource() == buttonInvert | e.getSource() == toolsInvert){
            // ImageMagick Call
            ConvertCmd cmd = new ConvertCmd();
            // create the operation, add images and operators/options
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            op.negate();
            newImage = imagePath.replace(".jpg", "_negate.jpg");
            historyList.add("invert/negate");
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
