package engine.assets.basic;

import com.raylib.Raylib;
import engine.assets.Asset;

import java.nio.IntBuffer;
import java.util.HashMap;

import static com.raylib.Raylib.*;

public class ModelAsset extends Asset {
    public static IntBuffer animationsCount = IntBuffer.allocate(10);
    HashMap<String, Model> loadedModels = new HashMap<>();
    HashMap<String, ModelAnimation> loadedAnimations = new HashMap<>();

    //TODO: implement good animation/model system to easily load and use models/animations
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

    public ModelAnimation getAnimation(String name) {
        if (loadedAnimations.containsKey(name)) return loadedAnimations.get(name);
        return loadAnimation(name);
    }

    public ModelAnimation getAnimation() {
        return getAnimation(getName());
    }

    ModelAnimation loadAnimation(String name) {
        System.out.println("Loading animation: " + name);
        ModelAnimation animation = LoadModelAnimations(getDirectory().getAbsolutePath() + "\\" + name + ".obj", animationsCount);
        loadedAnimations.put(name, animation);
        return animation;
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
