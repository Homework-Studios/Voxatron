package render.scene;

import render.scene.scenes.*;

import java.util.ArrayList;

public class SceneManager {

    public ArrayList<Scene> scenes;

    public Scene activeScene;

    public static SceneManager instance;

    public SceneManager() {
        instance = this;

        scenes = new ArrayList<>();

        addScene(new TitleScene());
        addScene(new SettingsScene());
        addScene(new LevelSelectorScene());
        addScene(new IngameUIDevScene());
        addScene(new IngameDevScene());
        setActiveScene(scenes.get(0));
    }

    public void update() {
        activeScene.update();
    }

    public void render() {
        activeScene.render();
    }

    public void addScene(Scene scene) {
        scenes.add(scene);
    }

    public void removeScene(Scene scene) {
        scenes.remove(scene);
    }

    public void clearScenes() {
        scenes.clear();
    }

    public void setActiveScene(Scene scene) {
        activeScene = scene;
    }

    public void setActiveScene(Class sceneName) {
        for (Scene scene : scenes) {
            if (scene.getClass() == sceneName) {
                activeScene = scene;
            }
        }
    }

    public Scene getActiveScene() {
        return activeScene;
    }
}
