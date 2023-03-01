package Render.Scene;

import com.raylib.java.Raylib;

public abstract class Scene {

    public SceneType sceneType;

    public Scene(SceneType sceneType) {
        this.sceneType = sceneType;
    }

    public abstract void render(Raylib raylib);
}
