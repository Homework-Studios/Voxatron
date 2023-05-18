package render.scene.scenes;

import math.Vector2;
import render.scene.Element;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.scenes.uiElements.ButtonElement;

import static util.ColorPalette.ColorPalettes.STANDARD_BUTTON;

public class IngameUIDevScene extends Scene {
    ElementBatch elementBatch;

    public IngameUIDevScene() {
        super();
    }

    @Override
    public void init() {


        elementBatch = new ElementBatch(new Element[]{

                new ButtonElement(
                        Vector2.byScreenPercent(94, 5),
                        Vector2.byScreenPercent(10, 7),
                        "Close",
                        40f,
                        STANDARD_BUTTON
                ) {
                    @Override
                    public void run() {
                        SceneManager.instance.setActiveScene(LevelSelectorScene.class);
                    }
                },
        }, true);

        addElement(elementBatch);
    }

    @Override
    public void update() {
        for (Element element : getIterableElements()) {
            element.update();
        }
    }

    @Override
    public void render() {
        for (Element element : getIterableElements()) {
            element.render();
        }
    }

}
