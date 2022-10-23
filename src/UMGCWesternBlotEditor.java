import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.IM4JavaException;
public class UMGCWesternBlotEditor extends JFrame implements ActionListener
{
    private final JButton buttonResize;
    private final JButton buttonEdge;
    private final JButton buttonScriptEdge;
    private final JButton buttonMonochrome;
    private final JButton buttonInvert;
    private final JButton buttonBrightnessContrast;
    private final JButton buttonSC;
    private final JButton buttonReset;
    private final JButton buttonLastImage;
    private final JButton exportHistory;
    private final JMenuItem fileOpen;
    private final JMenuItem fileSaveAs;
    private final JMenuItem editReset;
    private final JMenuItem toolsEdge;
    private final JMenuItem toolsBC;
    private final JMenuItem toolsSC;
    private final JMenuItem toolsResize;
    private final JMenuItem toolsMonochrome;
    private final JMenuItem toolsInvert;
    private final JMenuItem scriptEdge;
    private final JMenuItem historyShowHistory;
    private JScrollPane imageScrollPane;
    private final Container l_c;
    private String imagePath;
    private String imageDirectory;
    private String imageName;
    private String originalImage;
    private String lastImage;
    private int opCount = 0;
    List<String> historyList = new ArrayList<>(
            List.of());
    private String extension;
    public UMGCWesternBlotEditor() throws IOException {
        super("UMGC Western Blot Editor");
        // Create Graphical Interface
        // Buttons and Listeners
        buttonResize = new JButton("Resize");
        buttonResize.addActionListener(this);

        buttonInvert = new JButton("Invert");
        buttonInvert.addActionListener(this);

        buttonEdge = new JButton("Edge");
        buttonEdge.addActionListener(this);

        buttonScriptEdge = new JButton("Edge");
        buttonScriptEdge.addActionListener(this);

        buttonMonochrome = new JButton("Monochrome");
        buttonMonochrome.addActionListener(this);

        buttonBrightnessContrast = new JButton("Bright/Contrast");
        buttonBrightnessContrast.addActionListener(this);

        buttonSC = new JButton("Sigmoidal Contrast");
        buttonSC.addActionListener(this);

        buttonReset = new JButton("Reset");
        buttonReset.addActionListener(this);

        buttonLastImage = new JButton("Get Last Image");
        buttonLastImage.addActionListener(this);

        /* ********************************************** Create menu items **********************************************/
        fileOpen = new JMenuItem("Open");
        fileOpen.addActionListener(this);
        fileSaveAs = new JMenuItem("Save As");
        fileSaveAs.addActionListener(this);
        editReset = new JMenuItem("Reset");
        editReset.addActionListener(this);
        toolsEdge = new JMenuItem("Edge");
        toolsEdge.addActionListener(this);
        toolsBC = new JMenuItem("Brightness/Contrast");
        toolsBC.addActionListener(this);
        toolsSC = new JMenuItem("Sigmoidal Contrast");
        toolsSC.addActionListener(this);
        toolsResize = new JMenuItem("Resize");
        toolsResize.addActionListener(this);
        toolsMonochrome = new JMenuItem("Monochrome");
        toolsMonochrome.addActionListener(this);
        toolsInvert = new JMenuItem("Invert");
        toolsInvert.addActionListener(this);
        scriptEdge = new JMenuItem("Edge Detection Bash Script");
        scriptEdge.addActionListener(this);
        historyShowHistory = new JMenuItem("Show History");
        historyShowHistory.addActionListener(this);
        exportHistory = new JButton("Export History");
        exportHistory.addActionListener(this);


        /* ********************************************** Create Menu and Add Menu Items **********************************************/


        ///FILE MENU
        JMenu file = new JMenu("File");
        file.setMnemonic('F'); // Alt -F will access files
        //BUTTONS UNDER FILE MENU//
        //quick keystrokes -> Open = Ctrl-O
        file.add(fileOpen);
        fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK ));

        // quick key strokes ->  Save As = Ctrl-A
        file.add(fileSaveAs);
        fileSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

        ///EDIT MENU
        JMenu edit = new JMenu("Edit");
        edit.setMnemonic('E'); // Alt -E will access Edits
        //BUTTONS UNDER EDIT MENU
        //quick keystrokes -> Reset Image = Ctrl- R
        edit.add(editReset);
        editReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));

        JMenu tools = new JMenu("Tools");
        tools.setMnemonic('T'); // Alt -T will access Tools

        JMenu history = new JMenu("History");
        history.setMnemonic('H'); // Alt -H will access History
        history.add(historyShowHistory);

        // will need a help button not shown on menu bar just yet-- need to finalize program//
        JMenu help = new JMenu("Help");
        help.setMnemonic('P'); // Alt -P will access Help




        tools.add(new JLabel("Detect Lines"));
        tools.add(toolsEdge);
        tools.add(new JLabel("Detect edge and Straighten bands (Unix/Linux)"));
        tools.add(scriptEdge);
        tools.add(new JSeparator());
        tools.add(new JLabel("Transform"));
        tools.add(toolsResize);
        tools.add(new JSeparator());
        tools.add(new JLabel("Image Color"));
        tools.add(toolsBC);
        tools.add(toolsSC);
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
        JPanel panelButtonEdge = new JPanel();
        panelButtonEdge.add(new JLabel("Detect Lines"));
        panelButtonEdge.add(new JSeparator());
        panelButtonEdge.add(buttonEdge);
        panelButtonEdge.add(new JLabel("Detect Lines (Unix/Linux)"));
        panelButtonEdge.add(new JSeparator());
        panelButtonEdge.add(buttonScriptEdge);
        JPanel panelButtonTransform = new JPanel();
        panelButtonTransform.add(new JLabel("Transform"));
        panelButtonTransform.add(new JSeparator());
        panelButtonTransform.add(buttonResize);
        JPanel panelButtonColor = new JPanel();
        panelButtonColor.add(new JLabel("Color"));
        panelButtonColor.add(new JSeparator());
        panelButtonColor.add(buttonMonochrome);
        panelButtonColor.add(buttonBrightnessContrast);
        panelButtonColor.add(buttonSC);
        panelButtonColor.add(buttonInvert);
        panelButtonColor.add(buttonLastImage);
        panelButtonColor.add(buttonReset);

        // Load Initial Image ** FIND A BETTER WAY TO DO THIS!! ***
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG, TIFF, PNG, HEIC Images", "jpg", "tiff", "png", "heic");
        chooser.setFileFilter(filter);
        JTextField textFieldImagePath = new JTextField(100);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            textFieldImagePath.setText(chooser.getSelectedFile().getAbsolutePath());
        }


        imagePath = textFieldImagePath.getText();

        if(imagePath.contains(".jpg"))
            extension = ".jpg";
        else if(imagePath.contains(".tiff"))
            extension = ".tiff";
        else if (imagePath.contains(".png"))
            extension = ".png";
        else if (imagePath.contains(".heic"))
            extension = ".heic";
        originalImage = imagePath;
        File imgFile = new File(textFieldImagePath.getText());
        imageDirectory = imgFile.getParent();
        imageName = imgFile.getName();
        BufferedImage img = ImageIO.read(imgFile);
        ImageIcon icon = new ImageIcon(img);
        JLabel image = new JLabel(icon);
        imageScrollPane = new JScrollPane(image);
        buttonLastImage.setEnabled(false);

        // Load Content Pane and add components
        l_c = getContentPane();
        l_c.setLayout(new BorderLayout());
        l_c.add(menuBar, BorderLayout.NORTH);
        l_c.add(panelButtonColor, BorderLayout.SOUTH);
        l_c.add(panelButtonEdge, BorderLayout.EAST);
        l_c.add(panelButtonTransform, BorderLayout.WEST);
        l_c.add(imageScrollPane, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }
    public static void main(String[] args) throws IOException {
        UMGCWesternBlotEditor t = new UMGCWesternBlotEditor();
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    // action listeners
    public void actionPerformed(ActionEvent e) {
        String newImage;
        if (e.getSource() == fileOpen) {
            opCount = 0;
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG, TIFF, PNG, HEIC Images", "jpg", "tiff", "png", "heic");
            chooser.setFileFilter(filter);
            JTextField textFieldImagePath = new JTextField(100);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                textFieldImagePath.setText(chooser.getSelectedFile().getAbsolutePath());
            }

            imagePath = textFieldImagePath.getText();
            if(imagePath.contains(".jpg"))
                extension = ".jpg";
            else if(imagePath.contains(".tiff"))
                extension = ".tiff";
            else if (imagePath.contains(".png"))
                extension = ".png";
            else if (imagePath.contains(".heic"))
                extension = ".heic";
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
            historyList.clear();
            l_c.revalidate();
            buttonLastImage.setEnabled(false);
        }

        else if (e.getSource() == fileSaveAs) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG, TIFF, PNG, HEIC Images", "jpg", "tiff", "png", "heic");
            fileChooser.setFileFilter(filter);
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                ConvertCmd cmd = new ConvertCmd();
                IMOperation op = new IMOperation();
                op.addImage(imagePath);
                op.addImage(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    cmd.run(op);
                } catch (IOException | InterruptedException | IM4JavaException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        else if (e.getSource() == historyShowHistory) {
            JTextArea textArea = new JTextArea();
            JScrollPane textScrollPane = new JScrollPane(textArea);
            textArea.setEditable(false);
            for (int i = 0; i < historyList.size(); i++) {
                textArea.append(i + 1 + ". " + historyList.get(i) + "\n");
            }
            JPanel historyPanel = new JPanel();
            historyPanel.setLayout(new BorderLayout());
            historyPanel.add(textScrollPane, BorderLayout.CENTER);
            historyPanel.setSize(700, 500);
            historyPanel.add(exportHistory, BorderLayout.SOUTH);
            JFrame historyFrame = new JFrame();

            historyFrame.add(historyPanel);
            historyFrame.pack();
            historyFrame.setVisible(true);
            historyFrame.setTitle("Operations History");
        }

        else if (e.getSource() == buttonReset | e.getSource() == editReset) {
            opCount = 0;
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
            lastImage = null;
            buttonLastImage.setEnabled(false);
        }
        else if (e.getSource() == buttonEdge | e.getSource() == toolsEdge) {
            opCount++;
            JTextField thickness = new JTextField();
            JPanel edgePanel = new JPanel();
            edgePanel.setLayout(new GridLayout(2, 2));
            edgePanel.add(new JLabel("Thickness: "));
            edgePanel.add(thickness);
            JOptionPane.showConfirmDialog(null, edgePanel,
                    "Edge Thickness", JOptionPane.OK_CANCEL_OPTION);
            // ImageMagick Call
            ConvertCmd cmd = new ConvertCmd();
            // create the operation, add images and operators/options
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            op.edge(Double.parseDouble(thickness.getText()));
            newImage = imagePath.replace(extension, "_"+opCount+extension);
            op.addImage(newImage);
            historyList.add("edge: " + thickness.getText());
            // execute the operation
            try {
                cmd.run(op);
            } catch (IOException | InterruptedException | IM4JavaException ex) {
                throw new RuntimeException(ex);
            }
            lastImage = imagePath;
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
            buttonLastImage.setEnabled(true);
        }


        else if (e.getSource() == buttonScriptEdge | e.getSource() == scriptEdge) {
            opCount++;
            JTextField Radius = new JTextField();
            JTextField LowerLimit = new JTextField();
            JTextField UpperLimit = new JTextField();
            JPanel CannyThreshold = new JPanel();
            CannyThreshold.setLayout(new GridLayout(4, 4));
            CannyThreshold.add(new JLabel("Radius (MxN): "));
            CannyThreshold.add(Radius);
            CannyThreshold.add(new JLabel("Lower Percent: "));
            CannyThreshold.add(LowerLimit);
            CannyThreshold.add(new JLabel("Upper Percent: "));
            CannyThreshold.add(UpperLimit);
            JOptionPane.showConfirmDialog(null, CannyThreshold,
                    "Canny Edge Detection Parameter", JOptionPane.OK_CANCEL_OPTION);
            String threshold = String.valueOf(Radius.getText()) + "+" + String.valueOf(LowerLimit.getText()) + "%+" + String.valueOf(UpperLimit.getText())+"%";

            //start() will be in try catch
            File file1 = new File("./bash_scripts/band_detection_real.sh");
            file1.setExecutable(true);
            try {
                ProcessBuilder pb = new ProcessBuilder("./bash_scripts/band_detection_real.sh");
                Map<String, String> env = pb.environment();
                env.put("VAR1", this.imageDirectory);
                env.put("VAR2", this.imageName);
                env.put("VAR3", threshold);
                Process p = pb.start();
                p.waitFor();
                System.out.println("Script executed successfully");
                historyList.add("Edge Script(OS/Linux): radius: " + Radius.getText() + " lower limit: " + LowerLimit.getText() + " upper limit: " + UpperLimit.getText());
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }

            Path pathtoFolder = Path.of(imageDirectory);
            Path pathtoFile = pathtoFolder.resolve("composite.png");

            lastImage = imagePath;
            imagePath = String.valueOf(pathtoFile);

            File imgFile = new File(imagePath);
            BufferedImage img;
            System.out.println(imgFile);
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
            buttonLastImage.setEnabled(true);
        }
        else if (e.getSource() == buttonBrightnessContrast | e.getSource() == toolsBC) {
            opCount++;
            // Pop up window for brightness contrast params
            //Change the JTextfield for brightness/contrast so that user can use slider to adjust value or type in the value.
            JPanel bcPanel = new JPanel();
            bcPanel.setLayout(new GridLayout(2, 1));
            JOptionPane optionBPane = new JOptionPane();
            JTextField brightness = new JTextField(3);
            JSlider sliderB = new JSlider(-100, 100, 0);
            sliderB.setMajorTickSpacing(25);
            sliderB.setPaintTicks(true);
            sliderB.setPaintLabels(true);
            ChangeListener changeListener = changeEvent -> {
                JSlider theSlider = (JSlider) changeEvent.getSource();
                if (!theSlider.getValueIsAdjusting()) {
                    brightness.setText(String.valueOf(theSlider.getValue()));
                }
            };
            sliderB.addChangeListener(changeListener);
            brightness.setEditable(false);
            optionBPane.setMessage(new Object[]{"Select a Brightness: ", brightness, sliderB});
            JOptionPane optionCPane = new JOptionPane();
            JTextField contrast = new JTextField(3);
            JSlider sliderC = new JSlider(-100, 100, 0);
            sliderC.setMajorTickSpacing(25);
            sliderC.setPaintTicks(true);
            sliderC.setPaintLabels(true);
            ChangeListener changeListenerC = changeEvent -> {
                JSlider theSliderC = (JSlider) changeEvent.getSource();
                if (!theSliderC.getValueIsAdjusting()) {
                    contrast.setText(String.valueOf(theSliderC.getValue()));
                }
            };
            sliderC.addChangeListener(changeListenerC);
            contrast.setEditable(false);
            optionCPane.setMessage(new Object[]{"Select a Contrast: ", contrast, sliderC});
            bcPanel.add(optionBPane);
            bcPanel.add(optionCPane);
            JOptionPane.showConfirmDialog(null, bcPanel,
                    "Brightness / Contrast", JOptionPane.OK_CANCEL_OPTION);
            // ImageMagick Call
            ConvertCmd cmd = new ConvertCmd();
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            op.brightnessContrast((double) sliderB.getValue(), (double) sliderC.getValue());
            // Label new image with update
            newImage = imagePath.replace(extension, "_"+opCount+extension);
            // ImageMagick write newImage
            op.addImage(newImage);
            historyList.add("brightness: " + sliderB.getValue() + " contrast: " + sliderC.getValue());
            try {
                cmd.run(op);
            } catch (IOException | IM4JavaException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            // Update image path and reload image in JScrollPane
            lastImage = imagePath;
            imagePath = newImage;
            File imgFile = new File(imagePath);
            BufferedImage img;
            try {
                img = ImageIO.read(imgFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            //Update permissions for bash script

            File file1 = new File("./bash_scripts/brightness_contrast.sh");
            file1.setExecutable(true);

            // Process builder calls bash script
            try {
                ProcessBuilder pb = new ProcessBuilder("./bash_scripts/brightness_contrast.sh");
                Map<String, String> env = pb.environment();
                env.put("VAR1", this.lastImage);
                env.put("VAR2", String.valueOf(sliderB.getValue()));
                env.put("VAR3", String.valueOf(sliderC.getValue()));
                env.put("VAR4", this.imagePath);
                Process p = pb.start();
                p.waitFor();
                System.out.println("Script executed successfully");
            } catch (InterruptedException | IOException var33) {
                throw new RuntimeException(var33);
            }

            ImageIcon icon = new ImageIcon(img);
            JLabel image = new JLabel(icon);
            l_c.remove(imageScrollPane);
            imageScrollPane = new JScrollPane(image);
            l_c.add(imageScrollPane);
            l_c.revalidate();
            buttonLastImage.setEnabled(true);
        }

        else if (e.getSource() == buttonResize | e.getSource() == toolsResize) {
            try {
                opCount++;
                // resize popup
                JTextField width = new JTextField(5);
                JTextField height = new JTextField(5);
                JPanel resizePanel = new JPanel();
                resizePanel.setLayout(new GridLayout(2, 2));
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
                newImage = imagePath.replace(extension, "_"+opCount+extension);
                op.addImage(newImage);
                // execute the operation
                cmd.run(op);
                historyList.add("resize: " + Integer.parseInt(width.getText()) + "x" + Integer.parseInt(height.getText()));
                lastImage = imagePath;
                imagePath = newImage;
                File imgFile = new File(imagePath);
                BufferedImage img;
                try {
                    img = ImageIO.read(imgFile);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                //Update permissions for bash script

                File file1 = new File("./bash_scripts/resize.sh");
                file1.setExecutable(true);

                // Process builder calls bash script
                try {
                    ProcessBuilder pb = new ProcessBuilder("bash_scripts/resize.sh");
                    Map<String, String> env = pb.environment();
                    env.put("VAR1", this.lastImage);
                    env.put("VAR2", String.valueOf(width.getText()));
                    env.put("VAR3", String.valueOf(height.getText()));
                    env.put("VAR4", this.imagePath);

                    Process p = pb.start();
                    p.waitFor();
                    System.out.println("Script executed successfully");
                } catch (InterruptedException | IOException var29) {
                    throw new RuntimeException(var29);
                }

                ImageIcon icon = new ImageIcon(img);
                JLabel image = new JLabel(icon);
                l_c.remove(imageScrollPane);
                imageScrollPane = new JScrollPane(image);
                l_c.add(imageScrollPane);
                l_c.revalidate();
                buttonLastImage.setEnabled(true);
            } catch (InterruptedException | IOException | IM4JavaException except) {
                except.printStackTrace();
            }
        }

        else if (e.getSource() == buttonMonochrome | e.getSource() == toolsMonochrome) {
            opCount++;
            // ImageMagick Call
            ConvertCmd cmd = new ConvertCmd();
            // create the operation, add images and operators/options
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            op.monochrome();
            newImage = imagePath.replace(extension, "_"+opCount+extension);
            op.addImage(newImage);
            // execute the operation
            try {
                cmd.run(op);
            } catch (IOException | InterruptedException | IM4JavaException ex) {
                throw new RuntimeException(ex);
            }
            historyList.add("monochrome");
            lastImage = imagePath;
            imagePath = newImage;
            File imgFile = new File(imagePath);
            BufferedImage img;
            try {
                img = ImageIO.read(imgFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            //Update permissions for bash script

            File file1 = new File("./bash_scripts/monochrome.sh");
            file1.setExecutable(true);

            // Process builder calls bash script
            try {
                ProcessBuilder pb = new ProcessBuilder("./bash_scripts/monochrome.sh");
                Map<String, String> env = pb.environment();
                env.put("VAR1", this.lastImage);
                env.put("VAR2", this.imagePath);

                Process p = pb.start();
                p.waitFor();
                System.out.println("Script executed successfully");
            } catch (InterruptedException | IOException var26) {
                throw new RuntimeException(var26);
            }

            ImageIcon icon = new ImageIcon(img);
            JLabel image = new JLabel(icon);
            l_c.remove(imageScrollPane);
            imageScrollPane = new JScrollPane(image);
            l_c.add(imageScrollPane);
            l_c.revalidate();
            buttonLastImage.setEnabled(true);
        }

        else if (e.getSource() == buttonInvert | e.getSource() == toolsInvert) {
            opCount++;
            // ImageMagick Call
            ConvertCmd cmd = new ConvertCmd();
            // create the operation, add images and operators/options
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            op.negate();
            newImage = imagePath.replace(extension, "_"+opCount+extension);
            historyList.add("invert/negate");
            op.addImage(newImage);
            // execute the operation
            try {
                cmd.run(op);
            } catch (IOException | InterruptedException | IM4JavaException ex) {
                throw new RuntimeException(ex);
            }
            lastImage = imagePath;
            imagePath = newImage;
            File imgFile = new File(imagePath);
            BufferedImage img;
            try {
                img = ImageIO.read(imgFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            //Update permissions for bash script

            File file1 = new File("./bash_scripts/invert.sh");
            file1.setExecutable(true);

            // Process builder calls bash script
            try {
                ProcessBuilder pb = new ProcessBuilder("./bash_scripts/invert.sh");
                Map<String, String>env = pb.environment();
                env.put("VAR1", this.lastImage);
                env.put("VAR2", this.imagePath);
                Process p = pb.start();
                p.waitFor();
                System.out.println("Script executed successfully");
            } catch (InterruptedException | IOException var23) {
                throw new RuntimeException(var23);
            }

            ImageIcon icon = new ImageIcon(img);
            JLabel image = new JLabel(icon);
            l_c.remove(imageScrollPane);
            imageScrollPane = new JScrollPane(image);
            l_c.add(imageScrollPane);
            l_c.revalidate();
            buttonLastImage.setEnabled(true);
        }
        else if (e.getSource() == buttonSC | e.getSource() == toolsSC) {
            opCount++;
            JTextField cc = new JTextField(5);
            JTextField cf = new JTextField(5);
            JPanel CSPanel = new JPanel();
            CSPanel.setLayout(new GridLayout(2, 2));
            CSPanel.add(new JLabel("Contrast Center(%): "));
            CSPanel.add(cc);
            CSPanel.add(new JLabel("Contrast Factor: "));
            CSPanel.add(cf);
            JOptionPane.showConfirmDialog(null, CSPanel,
                    "Sigmoidal Contrasting", JOptionPane.OK_CANCEL_OPTION);
            // ImageMagick Call
            ConvertCmd cmd = new ConvertCmd();
            // create the operation, add images and operators/options
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            op.sigmoidalContrast(Double.parseDouble(cc.getText()), Double.parseDouble(cf.getText()));
            newImage = imagePath.replace(extension, "_"+opCount+extension);
            historyList.add("sigmoidal contrast: center= " + cc.getText() + " factor= " + cf.getText());
            op.addImage(newImage);
            // execute the operation
            try {
                cmd.run(op);
            } catch (IOException | InterruptedException | IM4JavaException ex) {
                throw new RuntimeException(ex);
            }
            lastImage = imagePath;
            imagePath = newImage;
            File imgFile = new File(imagePath);
            BufferedImage img;
            try {
                img = ImageIO.read(imgFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            //Update permissions for bash script

            File file1 = new File("./bash_scripts/sigmoidal.sh");
            file1.setExecutable(true);

            // Process builder calls bash script
            try {
                ProcessBuilder pb = new ProcessBuilder("./bash_scripts/sigmoidal.sh");
                Map<String, String> env = pb.environment();
                env.put("VAR1", this.lastImage);
                env.put("VAR2", String.valueOf(cc.getText()));
                env.put("VAR3", String.valueOf(cf.getText()));
                env.put("VAR4", this.imagePath);

                Process p = pb.start();
                p.waitFor();
                System.out.println("Script executed successfully");
            } catch (InterruptedException | IOException var21) {
                throw new RuntimeException(var21);
            }

            ImageIcon icon = new ImageIcon(img);
            JLabel image = new JLabel(icon);
            l_c.remove(imageScrollPane);
            imageScrollPane = new JScrollPane(image);
            l_c.add(imageScrollPane);
            l_c.revalidate();
            buttonLastImage.setEnabled(true);
        }

        else if (e.getSource() == exportHistory) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a (.txt)file to save");
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                PrintWriter output;
                try {
                    output = new PrintWriter(fileChooser.getSelectedFile());
                    output.write("Order of Operations on Image " + originalImage +"\n");
                    for (int i=0; i<historyList.size(); i++){
                        output.write(i+1 + ". " + historyList.get(i) + "\n");
                    }
                    output.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        else if (e.getSource() == buttonLastImage) {
            opCount--;
            imagePath = lastImage;
            File imgFile = new File(imagePath);
            BufferedImage img;
            try {
                img = ImageIO.read(imgFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            //Update permissions for bash script

            File file1 = new File("./bash_scripts/remove_last.sh");
            file1.setExecutable(true);

            // Process builder calls bash script
            try {
                ProcessBuilder pb = new ProcessBuilder("./bash_scripts/remove_last.sh");
                Process p = pb.start();
                p.waitFor();
                System.out.println("Script executed successfully");
            } catch (InterruptedException | IOException var18) {
                throw new RuntimeException(var18);
            }

            ImageIcon icon = new ImageIcon(img);
            JLabel image = new JLabel(icon);
            l_c.remove(imageScrollPane);
            imageScrollPane = new JScrollPane(image);
            l_c.add(imageScrollPane);
            l_c.revalidate();
            int lastItemIndex = historyList.size() - 1;
            historyList.remove(lastItemIndex);
            buttonLastImage.setEnabled(false);
        }
    }
}
