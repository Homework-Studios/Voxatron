package engine.assets.basic;

import com.raylib.Raylib;
import engine.assets.Asset;

import java.util.HashMap;

public class TextureAsset extends Asset {

    private final HashMap<String, Raylib.Texture> loadedTextures = new HashMap<>();

    public TextureAsset(String name, String path, AssetType type, boolean createAsset) {
        super(name, path, type, createAsset);
    }

    Raylib.Texture loadTexture(String name) {
        System.out.println("loading texture: " + name);
        Raylib.Texture image = Raylib.LoadTexture(getDirectory().getAbsolutePath() + "\\" + name + ".png");
        loadedTextures.put(name, image);
        return image;
    }


    public Raylib.Texture getTexture(String name) {
        if (loadedTextures.containsKey(name)) return loadedTextures.get(name);
        return loadTexture(name);
    }

    @Override
    public void load() {

    }
}
