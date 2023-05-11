package engine.assets.basic;

import com.raylib.Raylib;
import engine.assets.Asset;

import java.util.HashMap;

public class SoundAsset extends Asset {
    private final HashMap<String, Raylib.Sound> loadedSounds = new HashMap<>();

    public SoundAsset(String name, String path, AssetType type, boolean createAsset) {
        super(name, path, type, createAsset);
    }

    Raylib.Sound loadSound(String path) {
        Raylib.Sound sound = Raylib.LoadSound(getDirectory().getAbsolutePath() + "\\" + path + ".wav");
        System.out.println("Loaded Sound: " + getDirectory().getAbsolutePath() + "\\" + path + ".wav");
        loadedSounds.put(path, sound);
        return sound;
    }

    public Raylib.Sound getSound() {
        return getSound(getName());
    }

    public Raylib.Sound getSound(String path) {
        if (loadedSounds.containsKey(path)) {
            return loadedSounds.get(path);
        } else {
            return loadSound(path);
        }
    }

    @Override
    public void unload() {
        loadedSounds.values().forEach(Raylib::UnloadSound);
        loadedSounds.clear();
    }

    @Override
    public void load() {

    }
}
