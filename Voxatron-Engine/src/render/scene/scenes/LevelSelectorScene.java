package render.scene.scenes;

import render.scene.Element;
import render.scene.Scene;

public class LevelSelectorScene extends Scene {

    public ElementBatch levelSelectorBatch;

    public LevelSelectorScene() {
        super();
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        for (Element element : getIterableElements()) {
            element.update();
        }
    }

    @Override
    public void render() {
        for (Element element : elements) {
            element.render();
        }
    }
}
