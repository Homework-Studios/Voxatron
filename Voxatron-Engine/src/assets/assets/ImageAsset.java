package assets.assets;

import assets.Asset;
import com.raylib.Raylib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageAsset extends Asset {
    List<String> images = new ArrayList<>();
    HashMap<String, Raylib.Image> loadedImages = new HashMap<>();
    HashMap<String, Raylib.Texture> loadedTextures = new HashMap<>();

    public ImageAsset(String assetName, String assetDir) {
        super(assetName, assetDir);

    }


    @Override
    public void onCreate() {

    }

    @Override
    public void afterCreate() {

    }

    @Override
    public void onLoad() {

    }


    @Override
    public void afterLoad() {
        for (String file : relatedAssets) {
            if (file.endsWith(".png")) {
                images.add(file.replace(".png", ""));
            }
        }
    }

    @Override
    public void onUnload() {

    }

    @Override
    public void afterUnload() {
        loadedTextures.clear();
        loadedImages.clear();
    }

    public void onCreateAsset() {

    }

    Raylib.Image loadImage(String name) {
        System.out.println("loading image: " + name);
        Raylib.Image image = Raylib.LoadImage(dir + name + ".png");
        loadedImages.put(name, image);
        return image;
    }

    Raylib.Texture loadTexture(String name) {
        System.out.println("loading texture: " + name);
        Raylib.Texture image = Raylib.LoadTexture(dir + name + ".png");
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
