package engine.assets.basic;

import com.raylib.Raylib;
import engine.assets.Asset;

import java.nio.IntBuffer;
import java.util.HashMap;

import static com.raylib.Raylib.*;

public class ModelAsset extends Asset {
    HashMap<String, Model> loadedModels = new HashMap<>();

    public ModelAsset(String name, String path, AssetType type, boolean createAsset) {
        super(name, path, type, createAsset);
    }

    public Model getModel(String name) {
        if (loadedModels.containsKey(name)) return loadedModels.get(name);
        return loadModel(name);
    }

    public Model getModel() {
        return getModel(getName());
    }

    Model loadModel(String name) {
        System.out.println("Loading model: " + name);
        Model model = LoadModel(getDirectory().getAbsolutePath() + "\\" + name + ".obj");
        loadedModels.put(name, model);
        return model;
    }

    @Override
    public void unload() {
        loadedModels.values().forEach(Raylib::UnloadModel);
        loadedModels.clear();
    }

    @Override
    public void load() {

    }
}
