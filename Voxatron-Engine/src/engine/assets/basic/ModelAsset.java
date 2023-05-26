package engine.assets.basic;

import com.raylib.Raylib;
import engine.assets.Asset;
import engine.render.shader.ShaderManager;

import java.util.HashMap;

import static com.raylib.Raylib.*;

public class ModelAsset extends Asset {
    private final HashMap<String, Texture> loadedTextures = new HashMap<>();
    private final HashMap<String, Model> loadedModels = new HashMap<>();

    public ModelAsset(String name, String path, AssetType type, boolean createAsset) {
        super(name, path, type, createAsset);
    }

    Texture loadTexture(String name) {
        System.out.println("Loading texture: " + name);
        Texture image = LoadTexture(getDirectory().getAbsolutePath() + "\\" + name + ".png");
        loadedTextures.put(name, image);
        return image;
    }

    public Model getModel(String name) {
        if (loadedModels.containsKey(name)) return loadedModels.get(name);
        return loadModel(name);
    }

    public Model getModel() {
        return getModel(getName());
    }

    public Mesh getMesh(String name) {
        return getModel(name).meshes();
    }

    public Mesh getMesh() {
        return getMesh(getName());
    }

    Model loadModel(String name) {
        System.out.println("Loading model: " + name);
        Model model = LoadModel(getDirectory().getAbsolutePath() + "\\" + name + ".obj");
        Shader lightShader = ShaderManager.instance.lightShader;
        model.materials().shader(lightShader);
        loadedModels.put(name, model);
        return model;
    }

    public Texture getTexture() {
        return getTexture(getName());
    }


    public Texture getTexture(String name) {
        if (loadedTextures.containsKey(name)) return loadedTextures.get(name);
        return loadTexture(name);
    }

    public Model getNewModel(String name) {
        Model model = LoadModel(getDirectory().getAbsolutePath() + "\\" + name + ".obj");
        Shader lightShader = ShaderManager.instance.lightShader;
        model.materials().shader(lightShader);
        return model;
    }

    //TODO: own model system
    public Model getNewModel() {
        return getNewModel(getName());
    }

    @Override
    public void unload() {
        loadedModels.values().forEach(Raylib::UnloadModel);
        loadedTextures.values().forEach(Raylib::UnloadTexture);
        loadedModels.clear();
        loadedTextures.clear();
    }

    @Override
    public void load() {

    }

    @Override
    public String toString() {
        return "ModelAsset{" +
                "loadedTextures=" + loadedTextures +
                ", loadedModels=" + loadedModels +
                '}';
    }
}
