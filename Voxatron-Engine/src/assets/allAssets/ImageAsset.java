package assets.allAssets;

import assets.Asset;
import assets.Assets;
import com.raylib.Raylib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageAsset extends Asset {
    public List<String> images = new ArrayList<>();
    HashMap<String, Raylib.Image> loadedImages = new HashMap<>();
    HashMap<String, Raylib.Texture> loadedTextures = new HashMap<>();

    public ImageAsset(String assetName, String assetPath) {
        super(assetName, assetPath, Assets.IMAGE_ASSET);
    }


    public void onLoad() {
        for (String file : relatedAssets) {
            if (file.endsWith(".png")) {
                images.add(file.replace(".png", ""));
            }
        }
        FOLDER_PATH = FOLDER_PATH.replace("\\\\", "\\");
    }

    @Override
    public void onCreateAsset() {

    }

    Raylib.Image loadImage(String name) {
        Raylib.Image image = Raylib.LoadImage(FOLDER_PATH + "\\" + name + ".png");
        loadedImages.put(name, image);
        return image;
    }

    Raylib.Texture loadTexture(String name) {
        Raylib.Texture image = Raylib.LoadTexture(FOLDER_PATH + "\\" + name + ".png");
        loadedTextures.put(name, image);
        return image;
    }


    public Raylib.Texture getTexture(String name) {
        if (loadedTextures.containsKey(name)) return loadedTextures.get(name);
        return loadTexture(name);
    }


    public Raylib.Image getImage(String name) {
        if (loadedImages.containsKey(name)) return loadedImages.get(name);
        return loadImage(name);
    }
}
