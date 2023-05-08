package render.scene;

import render.scene.scenes.TitleScene;

import java.util.ArrayList;

public class SceneManager {

    public ArrayList<Scene> scenes;

    public Scene activeScene;

    public SceneManager() {
        scenes = new ArrayList<>();

        addScene(new TitleScene());
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

    public Scene getActiveScene() {
        return activeScene;
    }
}
