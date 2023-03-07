package render.scene.types;

import render.scene.Scene;
import render.scene.SceneType;
import render.scene.ui.LevelSelectorUIScreen;
import render.ui.UIScreen;

public class LevelSelectorScene extends Scene {

    public UIScreen screen;

    public LevelSelectorScene() {
        super(SceneType.LEVEL_SELECTOR);
        screen = new LevelSelectorUIScreen();
    }

    @Override
    public void update() {
        screen.update();
    }

    @Override
    public void render() {
        screen.render();
    }
}
