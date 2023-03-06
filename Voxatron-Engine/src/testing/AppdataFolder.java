package testing;

import debug.DebugDraw;

import javax.naming.spi.DirectoryManager;
import java.io.*;

public class AppdataFolder {
    public static String DATA_FOLDER = System.getenv("APPDATA") + "\\Voxatron";

    public AppdataFolder() {
File dir = new File(DATA_FOLDER);
if(!dir.exists()) {
    dir.mkdir();
}
        File file = new File(DATA_FOLDER + "\\test.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("test\n");
            fileWriter.close();

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int i = 0;
            while(bufferedReader.readLine() != null) {
                i++;
            }
           // DebugDraw.instance.print("Lines in file: " + i);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}
