package assets;

import assets.allAssets.ImageAsset;
import engine.EngineForm;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class Asset {
    public static Asset instance = new ImageAsset("test", "test");
    public static List<String> allAssets = new ArrayList<>();
    public static HashMap<String, String> assetLoadData = new HashMap<>();
    public static HashMap<String, Asset> loadedAssets = new HashMap<>();
    public static HashMap<String, String> assetPaths = new HashMap<>();
    public static String ASSET_DIR = System.getenv("APPDATA") + "\\Voxatron\\Assets\\";

    public String name;
    public String dir;
    public boolean isLoaded = false;
    public List<String> relatedAssets = new ArrayList<>();

    public Asset(String name, String dir) {
        this.name = name;
        this.dir = dir;
    }

    public static void init() {
        for (File file : getAllSubFiles(new File(ASSET_DIR))) {
            if (file.getName().endsWith(".asset")) {
                allAssets.add(file.getName().replace(".asset", ""));
                String assetData = "";
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        assetData += line;
                    }
                    reader.close();
                    String name = file.getName().replace(".asset", "");
                    assetLoadData.put(name, assetData);
                    assetPaths.put(name, file.getAbsolutePath().replace(file.getName(), ""));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        DefaultMutableTreeNode root = EngineForm.root;
        root.add(new DefaultMutableTreeNode("Test1"));
        root.add(new DefaultMutableTreeNode("Test2"));

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

    public static HashMap<String, Integer> getHostedAssets() {
        HashMap<String, Integer> hostedAssets = new HashMap<>();
        try {
            //get content of https://raw.githubusercontent.com/Homework-Studios/github-storage/main/Voxatron/Assets/AssetMaster
            URL url = new URL("hostedServer" + "AssetMaster");
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(":");
                hostedAssets.put(split[0], Integer.parseInt(split[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return hostedAssets;
    }

    /**
     * @see #getAsset(String)
     */
    public static ImageAsset getImageAsset(String name) {
        return (ImageAsset) instance.getAsset(name);
    }

    /**
     * use {@link #load()} to load all the data e.g. images
     *
     * @return the asset if it is loaded, else it loads the asset and returns it
     */
    public Asset getAsset(String name) {
        if (loadedAssets.containsKey(name))
            return loadedAssets.get(name);
        else return loadAsset(name);
    }

    /**
     * Loads the assetFile into ram
     * use {@link #load()} to load all the data e.g. images
     *
     * @param name
     * @return
     */
    public Asset loadAsset(String name) {
        if (assetLoadData.containsKey(name)) {
            String assetData = assetLoadData.get(name);
            //TODO: extract
            for (String s : assetData.replace(System.lineSeparator(), "").split(";;")) {
                String[] split = s.split("=");
                if (split[0].equals("asset_type")) {
                    String type = split[1];
                    switch (type) {
                        case "ImageAsset":
                            ImageAsset asset = new ImageAsset(name, assetPaths.get(name));
                            loadedAssets.put(name, asset);
                            return asset;
                        case "UIAsset":

                            break;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Loads the asset and calls the methods in the order of:
     * <li>{@link #onLoad()}</li>
     * <li>{@link #midLoad()} (in MasterAsset)</li>
     * <li>{@link #afterLoad()}</li>
     */
    public void load() {
        if (isLoaded) return;
        onLoad();
        midLoad();
        afterLoad();
        isLoaded = true;
    }

    /**
     * loads {@link #relatedAssets} identifiers into ram
     *
     * @see #load()
     */
    void midLoad() {
        for (File file : Objects.requireNonNull(new File(dir).listFiles())) {
            if (!file.getName().endsWith(".asset")) relatedAssets.add(file.getName());
        }
    }

    /**
     * gets all the assets that end with the given ending
     * <p>
     * example:
     * <li>getRelatedAssetsByEnding(".png")</li>
     * <li>getRelatedAssetsByEnding(".txt")</li>
     *
     * @param ending
     * @return all assets that end with the given ending excluding the ending
     * @see #relatedAssets
     */
    public List<String> getRelatedAssetsByEnding(String ending) {
        List<String> assets = new ArrayList<>();
        for (String asset : relatedAssets) {
            if (asset.endsWith(ending)) {
                assets.add(asset.replace(ending, ""));
            }
        }
        return assets;
    }

    public abstract void onCreate();

    public abstract void afterCreate();

    /**
     * @see #load()
     */
    public abstract void onLoad();

    /**
     * is called after {@link #load()}
     *
     * @see #load()
     */

    public abstract void afterLoad();

    public abstract void onUnload();

    public void midUnload() {
        loadedAssets.remove(name);
    }

    public abstract void afterUnload();
}
