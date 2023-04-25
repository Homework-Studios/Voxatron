package assets.assets.UI;

import assets.Asset;
import com.raylib.Raylib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageAsset extends Asset {
    List<String> images = new ArrayList<>();
    HashMap<String, Raylib.Image> loadedImages = new HashMap<>();
    HashMap<String, Raylib.Texture> loadedTextures = new HashMap<>();

    public ImageAsset(String name, String path, AssetType type, boolean create) {
        super(name, path, type, create);
    }

    Raylib.Image loadImage(String name) {
        System.out.println("loading image: " + name);
        Raylib.Image image = Raylib.LoadImage(getDirectory() + "/" + name + ".png");
        loadedImages.put(name, image);
        return image;
    }

    Raylib.Texture loadTexture(String name) {
        System.out.println("loading texture: " + name);
        Raylib.Texture image = Raylib.LoadTexture(getDirectory().getAbsolutePath() + "\\" + name + ".png");
        loadedTextures.put(name, image);
        return image;
    }


    public Raylib.Texture getTexture(String name) {
        ensureMapsInitialized();
        if (loadedTextures.containsKey(name)) return loadedTextures.get(name);
        return loadTexture(name);
    }


    public Raylib.Image getImage(String name) {
        ensureMapsInitialized();
        if (loadedImages.containsKey(name)) return loadedImages.get(name);
        return loadImage(name);
    }

    @Override
    public void load() {
    }

    public void ensureMapsInitialized() {
        if (loadedImages == null) {
            loadedImages = new HashMap<>();
        }
        if (loadedTextures == null) {
            loadedTextures = new HashMap<>();
        }
    }
}
