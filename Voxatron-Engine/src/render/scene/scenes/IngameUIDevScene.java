package render.scene.scenes;

import com.raylib.Jaylib;
import math.Vector2;
import render.scene.Element;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.scenes.uiElements.ButtonElement;
import render.scene.scenes.uiElements.levelButtons.TowerPanel;
import render.scene.scenes.uiElements.levelButtons.TowerSelector;

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
                new TowerSelector(Vector2.byScreenPercent(50, 100), Vector2.byScreenPercent(85, 20), new TowerPanel[]{
                        new TowerPanel("Test1", Jaylib.RED),
                        new TowerPanel("Test2", Jaylib.BLUE),
                        new TowerPanel("Test3", Jaylib.GREEN),
                        new TowerPanel("Test1", Jaylib.RED),
                        new TowerPanel("Test2", Jaylib.BLUE),
                        new TowerPanel("Test3", Jaylib.GREEN),
                        new TowerPanel("Test1", Jaylib.RED),
                        new TowerPanel("Test2", Jaylib.BLUE),
                        new TowerPanel("Test3", Jaylib.GREEN),
                        new TowerPanel("Test1", Jaylib.RED),
                        new TowerPanel("Test2", Jaylib.BLUE),
                        new TowerPanel("Test3", Jaylib.GREEN),
                }) {
                    @Override
                    public void run() {

                    }
                }
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
