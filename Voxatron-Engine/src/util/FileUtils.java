package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtils {
    public static List<File> getSubFiles(File file) {
        List<File> files = new ArrayList<>();
        if (file.isDirectory()) {
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                if (listFile.isDirectory()) {
                    files.addAll(getSubFiles(listFile));
                } else {
                    files.add(listFile);
                }
            }
        }
        return files;
    }

    public static List<File> getAllFiles(File file) {
        List<File> files = new ArrayList<>();
        if (file.isDirectory()) {
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                if (listFile.isDirectory()) {
                    files.addAll(getAllFiles(listFile));
                }
                files.add(listFile);
            }
        }
        return files;
    }

    public static void deleteExistingDirectory(File file) {
        if (file.exists() && file.isDirectory()) {
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                if (listFile.isDirectory()) {
                    deleteExistingDirectory(listFile);
                } else {
                    deleteExistingFile(listFile);
                }
            }
            file.delete();
        }
    }

    public static void deleteExistingFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    public static void deleteFileOrDirectory(File file) {
        if (file.isDirectory()) {
            deleteExistingDirectory(file);
        } else {
            deleteExistingFile(file);
        }
    }
}
