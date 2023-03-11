package assets;

import assets.allAssets.ImageAsset;
import assets.allAssets.LoadingAsset;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public enum Assets {
    NULL(new HashMap<>()),
    IMAGE_ASSET(new HashMap<>());

    static final String assetPath = System.getenv("APPDATA") + "\\Voxatron\\Assets\\";
    static File assetFolder;
    public final HashMap<String, Asset> stringAssetHashMap;

    Assets(HashMap<String, Asset> stringAssetHashMap) {
        this.stringAssetHashMap = stringAssetHashMap;
    }

    public static void init() {
        assetFolder = new File(assetPath);
        if (!assetFolder.exists()) {
            assetFolder.mkdirs();
        }
        loadAssets();
    }

    public static void loadAssets() {
        List<File> files = getAllSubFiles(assetFolder);
        for (int i = files.size() - 1; i >= 0; i--) {
            File file = files.get(i);
            if (file.getName().endsWith(".asset")) {
                Asset asset = new LoadingAsset(file.getName().replace(".asset", ""), file.getParentFile().getAbsolutePath().replace(assetPath + file.getName().replace(".asset", ""), ""));
                asset.loadAsset();
                asset.assetType.stringAssetHashMap.put(asset.ASSET_NAME, asset);
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

    public ImageAsset getImageAsset(String name) {
        return (ImageAsset) stringAssetHashMap.get(name);
    }
}
