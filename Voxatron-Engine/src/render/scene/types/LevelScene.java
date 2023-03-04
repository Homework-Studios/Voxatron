package render.scene.types;

import level.LevelManager;
import render.scene.Scene;
import render.scene.SceneType;

public class LevelScene extends Scene {

    LevelManager levelManager = new LevelManager();

    public LevelScene() {
        super(SceneType.LEVEL);
    }

    @Override
    public void update() {
        levelManager.update();
    }

    @Override
    public void render() {
        levelManager.render();
    }
}
