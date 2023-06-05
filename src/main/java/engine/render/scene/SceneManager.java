package engine.render.scene;

import engine.assets.Asset;
import engine.render.Renderer;
import spiel.visuellen.szenen.EbenenAuswaehlerSzene;
import spiel.visuellen.szenen.EinstellungsSzene;
import spiel.visuellen.szenen.KarteEinsSzene;
import spiel.visuellen.szenen.TitelSzene;

import java.util.ArrayList;

public class SceneManager {

    public static SceneManager instance;
    public ArrayList<Scene> scenes;
    public Scene activeScene;

    public SceneManager() {
        instance = this;

        scenes = new ArrayList<>();

        addScene(new TitelSzene());
        addScene(new EinstellungsSzene());
        addScene(new EbenenAuswaehlerSzene());
        addScene(new KarteEinsSzene());
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

    public Scene getActiveScene() {
        return activeScene;
    }

    public void setActiveScene(Scene scene) {
        Asset.clearAssets();
        activeScene = scene;
    }

    public void setActiveScene(Class sceneName) {
        Asset.clearAssets();
        for (Scene scene : scenes) {
            if (scene.getClass() == sceneName) {
                activeScene = scene;
                Renderer.instance.mode3d = false;
            }
        }
    }
}
