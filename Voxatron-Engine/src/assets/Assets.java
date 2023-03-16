package assets;

import assets.allAssets.ImageAsset;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public enum Assets {
    NULL,
    IMAGE_ASSET,
    UI_ASSET;

    public static final HashMap<String, Asset> stringAssetHashMap = new HashMap<>();
    public static final String assetPath = System.getenv("APPDATA") + "\\Voxatron\\Assets\\";
    public static final String hostedServer = "https://raw.githubusercontent.com/Homework-Studios/github-storage/main/Voxatron/Assets/";
    static File assetFolder;


    public static void init() {
        assetFolder = new File(assetPath);
        if (!assetFolder.exists()) {
            assetFolder.mkdirs();
        }
        loadAssets();
        updateAssets();
    }

    public static void updateAssets() {
        HashMap<String, Integer> existingAssets = new HashMap<>();
        for (Asset asset : stringAssetHashMap.values()) {
            existingAssets.put(asset.ASSET_NAME, asset.version);
        }
        //TODO: add path in asset master file
        HashMap<String, Integer> hostedAssets = getHostedAssets();
        List<String> assetsToUpdate = new ArrayList<>();

        for (String assetName : hostedAssets.keySet()) {
            if (existingAssets.containsKey(assetName)) {
                if (existingAssets.get(assetName) < hostedAssets.get(assetName)) {
                    assetsToUpdate.add(assetName);
                }
            } else {
                assetsToUpdate.add(assetName);
            }
        }

        System.out.println("assets need to be updated: " + assetsToUpdate.size());
        System.out.println("assets need to be updated: " + assetsToUpdate);

        for (String assetName : assetsToUpdate) {
            System.out.println("updating asset: " + assetName);
            //todo pull not existing assets
            Asset asset = stringAssetHashMap.get(assetName);
            asset.pullFromHost();
            asset.loadAsset();
            asset.pullFromHost();
        }


    }

    public static void loadAssets() {
        stringAssetHashMap.clear();
        List<File> files = getAllSubFiles(assetFolder);
        for (int i = files.size() - 1; i >= 0; i--) {
            File file = files.get(i);
            if (file.getName().endsWith(".asset")) {
                String name = file.getName().replace(".asset", "");
                String path = file.getParentFile().getAbsolutePath().replace(assetPath, "").replace(file.getName().replace(".asset", ""), "");
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
            System.out.println("\u001B[31m" + "File " + ASSET_NAME + " does not exist! or path: " + FOLDER_PATH + " is wrong! in loading\u001B[0m");
        }
        return Assets.NULL;
    }

    public static ImageAsset getImageAsset(String name) {
        return (ImageAsset) stringAssetHashMap.get(name);
    }

    public static HashMap<String, Integer> getHostedAssets() {
        HashMap<String, Integer> hostedAssets = new HashMap<>();
        try {
            //get content of https://raw.githubusercontent.com/Homework-Studios/github-storage/main/Voxatron/Assets/AssetMaster
            URL url = new URL(hostedServer + "AssetMaster");
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
}
