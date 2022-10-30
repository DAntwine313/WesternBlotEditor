import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Run_WesternblotEditor {
    public static void main(String[] args){
        File file1 = new File("./bash_scripts/refresh_output.sh");
        file1.setExecutable(true);
        try {
            new UMGCWesternBlotEditor();

            ProcessBuilder pb = new ProcessBuilder("./bash_scripts/refresh_output.sh");
            Process p = pb.start();
            p.waitFor();
            System.out.println("Script executed successfully");
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}