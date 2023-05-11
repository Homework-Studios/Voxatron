package engine.assets.basic;

import com.raylib.Raylib;
import engine.assets.Asset;

import java.util.HashMap;

public class ImageAsset extends Asset {

    private final HashMap<String, Raylib.Texture> loadedTextures = new HashMap<>();
    private final HashMap<String, Raylib.Image> loadedImages = new HashMap<>();


    public ImageAsset(String name, String path, AssetType type, boolean createAsset) {
        super(name, path, type, createAsset);
    }

    Raylib.Texture loadTexture(String name) {
        System.out.println("loading texture: " + name);
        Raylib.Texture image = Raylib.LoadTexture(getDirectory().getAbsolutePath() + "\\" + name + ".png");
        loadedTextures.put(name, image);
        return image;
    }

    Raylib.Image loadImage(String name) {
        System.out.println("loading image: " + name);
        Raylib.Image image = Raylib.LoadImage(getDirectory().getAbsolutePath() + "\\" + name + ".png");
        loadedImages.put(name, image);
        return image;
    }

    public Raylib.Image getImage() {
        return getImage(getName());
    }

    public Raylib.Texture getTexture() {
        return getTexture(getName());
    }

    public Raylib.Image getImage(String name) {
        if (loadedImages.containsKey(name)) return loadedImages.get(name);
        return loadImage(name);
    }

    public Raylib.Texture getTexture(String name) {
        if (loadedTextures.containsKey(name)) return loadedTextures.get(name);
        return loadTexture(name);
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {
        loadedTextures.values().forEach(Raylib::UnloadTexture);
        loadedImages.values().forEach(Raylib::UnloadImage);
        loadedTextures.clear();
        loadedImages.clear();
    }
}
