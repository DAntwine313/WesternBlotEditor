import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DisplayImage {

    JTextField textFieldImagePath;
    String imagePath;
    public static void main(String[] args) throws IOException {
        DisplayImage gui = new DisplayImage();
    }

    public JScrollPane getDisplayImage() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG, GIF, PNG Images", "jpg", "gif", "png");
        chooser.setFileFilter(filter);
        textFieldImagePath = new JTextField(100);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            textFieldImagePath.setText(chooser.getSelectedFile().getAbsolutePath());
            imagePath = textFieldImagePath.getText();
        }
        BufferedImage img = ImageIO.read(chooser.getSelectedFile().getAbsoluteFile());
        ImageIcon icon = new ImageIcon(img);
        JLabel image = new JLabel(icon);
        JScrollPane imageScrollPane = new JScrollPane(image);
        return imageScrollPane;
    }

}
