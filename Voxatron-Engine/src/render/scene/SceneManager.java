package render.scene;

import render.scene.types.CreditsScene;
import render.scene.types.MainMenuScene;
import com.raylib.java.Raylib;

import java.util.ArrayList;
import java.util.List;

public class SceneManager {

    public List<Scene> scenes = new ArrayList<>();
    public Scene currentScene;

    public static SceneManager instance;

    public SceneManager() {
        instance = this;
        addScene(new MainMenuScene());
        addScene(new CreditsScene());
    }

    public void addScene(Scene scene) {
        scenes.add(scene);

        if (currentScene == null) {
            currentScene = scene;
        }
    }

    public void removeScene(Scene scene) {
        scenes.remove(scene);
    }

    public void setCurrentScene(Scene scene) {
        currentScene = scene;
    }

    public void setCurrentScene(SceneType sceneType) {
        if (findScene(sceneType) != null) {
            currentScene = findScene(sceneType);
        }
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public Scene findScene(SceneType sceneType) {
        for (Scene scene : scenes) {
            if (scene.sceneType == sceneType) {
                return scene;
            }
        }
        return null;
    }

    public void render(Raylib raylib) {
        currentScene.render(raylib);
    }
}
