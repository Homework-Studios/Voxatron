package render.scene.scenes;

import com.raylib.Jaylib;
import math.Vector2;
import render.scene.Element;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.scenes.uiElements.ButtonElement;
import render.scene.scenes.uiElements.LevelSelector;
import render.scene.scenes.uiElements.TextElement;
import render.scene.scenes.uiElements.levelSelector.LevelSelectorTab;
import testing.TestingValues;

import static util.ColorPalette.ColorPalettes.STANDARD_BUTTON;

public class LevelSelectorScene extends Scene {

    public ElementBatch levelSelectorBatch;

    public LevelSelectorScene() {
        super();
    }

    @Override
    public void init() {

        TestingValues.instance.value = 5;

        levelSelectorBatch = new ElementBatch(new Element[]{
                new TextElement(
                        Vector2.byScreenPercent(0, 0),
                        Vector2.byScreenPercent(100, 15),
                        "Levels",
                        50f,
                        Jaylib.LIGHTGRAY
                ),
                new ButtonElement(
                        Vector2.byScreenPercent(94, 5),
                        Vector2.byScreenPercent(10, 7),
                        "Close",
                        40f,
                        STANDARD_BUTTON
                ) {
                    @Override
                    public void run() {
                        SceneManager.instance.setActiveScene(TitleScene.class);
                    }
                },
                new LevelSelector(
                        Vector2.byScreenPercent(50, 50),
                        Vector2.byScreenPercent(40, 60),
                        new LevelSelectorTab[]{
                                new LevelSelectorTab("The Bluelands", Jaylib.BLUE, false),
                                new LevelSelectorTab("The Depths", Jaylib.RED, true),
                                new LevelSelectorTab("The Generator", Jaylib.GREEN, true),
                                new LevelSelectorTab("The Mainframe", Jaylib.YELLOW, true),
                        }
                ) {
                    @Override
                    public void run() {
                        System.out.println("Level " + (this.hoveringTab + 1) + " selected!");
                    }
                }
        }, true);

        addElement(levelSelectorBatch);
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
