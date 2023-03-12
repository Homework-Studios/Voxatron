package assets;

import assets.allAssets.ImageAsset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public enum Assets {
    NULL,
    IMAGE_ASSET,
    UI_ASSET;

    public static final HashMap<String, Asset> stringAssetHashMap = new HashMap<>();
    static final String assetPath = System.getenv("APPDATA") + "\\Voxatron\\Assets\\";
    static File assetFolder;

    public static void init() {
        assetFolder = new File(assetPath);
        if (!assetFolder.exists()) {
            assetFolder.mkdirs();
        }
        loadAssets();
    }

    public static void loadAssets() {
        stringAssetHashMap.clear();
        List<File> files = getAllSubFiles(assetFolder);
        for (int i = files.size() - 1; i >= 0; i--) {
            File file = files.get(i);
            if (file.getName().endsWith(".asset")) {
                String name = file.getName().replace(".asset", "");
                String path = file.getParentFile().getAbsolutePath().replace(assetPath + file.getName().replace(".asset", ""), "");
                Assets assetType = getType(name, path);
                if (Objects.requireNonNull(assetType) == Assets.IMAGE_ASSET) {
                    ImageAsset imageAsset = new ImageAsset(name, path);
                    stringAssetHashMap.put(name, imageAsset);
                    System.out.println("Loaded asset: " + stringAssetHashMap.get(name).ASSET_NAME);
                    imageAsset.loadAsset();
                }

            }
        }
    }


    public static List<File> getAllSubFiles(File file) {
        List<File> files = new ArrayList<>();
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (f.isDirectory()) {
                files.addAll(getAllSubFiles(f));
            } else {
                files.add(f);
            }
        }
        return files;
    }

    public static Assets getType(String ASSET_NAME, String FOLDER_PATH) {
        String file_content = "";
        FOLDER_PATH = assetPath + FOLDER_PATH + "\\" + ASSET_NAME;
        File dir = new File(FOLDER_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File asset = new File(FOLDER_PATH + "\\" + ASSET_NAME + ".asset");
        if (asset.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(asset));
                String line;
                while ((line = reader.readLine()) != null) {
                    file_content += line;
                }
                reader.close();
                String[] lines = file_content.split(";");
                for (String l : lines) {
                    String[] split = l.split("=");
                    if (split[0].equals("asset_type")) {
                        return Assets.valueOf(split[1]);
                    }
                }
            } catch (IOException e) {
                System.out.println("\u001B[31m" + "File " + ASSET_NAME + " read error!" + "\u001B[0m");
            }
        } else {
            System.out.println("\u001B[31m" + "File " + ASSET_NAME + " does not exist! or path: " + FOLDER_PATH + " is wrong!\u001B[0m");
        }
        return Assets.NULL;
    }

    public static ImageAsset getImageAsset(String name) {
        return (ImageAsset) stringAssetHashMap.get(name);
    }
}
