package assets;

import java.io.*;
import java.nio.file.Files;


public abstract class Asset {
    public final String ASSET_NAME;
    public final String FOLDER_PATH;

    public File dir;
    public int version = 0;
    public Assets assetType = Assets.NULL;

    public String file_content = "";

    public Asset(String assetName, String assetPath, Assets assetType) {
        ASSET_NAME = assetName;
        FOLDER_PATH = getPath(assetPath + "\\" + assetName);
        this.assetType = assetType;
        //loadAsset();
    }

    public abstract void onLoad();

    public void loadAsset() {
        dir = new File(FOLDER_PATH);
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
                version = getAssetValueAsInt("version");
                assetType = Assets.valueOf(getAssetValue("asset_type"));
                onLoad();
            } catch (IOException e) {
                System.out.println("\u001B[31m" + "File " + ASSET_NAME + " read error!" + "\u001B[0m");
            }
        } else {
            System.out.println("\u001B[31m" + "File " + ASSET_NAME + " does not exist! or path: " + FOLDER_PATH + " is wrong!\u001B[0m");
        }
    }

    public void setVersion(int version) {
        this.version = version;
        setAssetValueAsInt("version", version);
    }

    public int getAssetValueAsInt(String key) {
        if (getAssetValue(key) != null)
            return Integer.parseInt(getAssetValue(key));
        return 0;
    }

    public String getAssetValue(String key) {
        String[] lines = file_content.split(";");
        for (String line : lines) {
            String[] split = line.split("=");
            if (split[0].equals(key)) {
                return split[1];
            }
        }
        return null;
    }

    public void setAssetValue(String key, String value) {
        String[] lines = file_content.split(";");
        boolean found = false;
        for (int i = 0; i < lines.length; i++) {
            String[] split = lines[i].split("=");
            if (split[0].equals(key)) {
                lines[i] = key + "=" + value;
                found = true;
            }
        }
        file_content = "";
        for (String line : lines) {
            if (!line.equals(""))
                file_content += line + ";";
        }
        if (!found) {
            file_content += key + "=" + value + ";";
        }
    }

    public void setAssetValueAsInt(String key, int value) {
        setAssetValue(key, String.valueOf(value));
    }

    public String getPath(String path) {
        return Assets.assetPath + path;
    }

    public void saveAsset() {
        File dir = new File(FOLDER_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File asset = new File(FOLDER_PATH + "\\" + ASSET_NAME + ".asset");
        try {
            if (!asset.exists()) asset.createNewFile();
            FileWriter writer = new FileWriter(asset, true);
            writer.write(file_content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean exists() {
        return Files.exists(new File(FOLDER_PATH + "\\" + ASSET_NAME + ".asset").toPath());
    }

    public void createAsset() {
        if (exists()) {
            System.out.println("\u001B[31m" + "Asset: " + ASSET_NAME + " already exists!" + "\u001B[0m");
            return;
        }
        onCreateAsset();
    }

    public abstract void onCreateAsset();
}
