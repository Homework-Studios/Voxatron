package game.visuals.scenes;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.math.Vector2;
import engine.render.scene.Element;
import engine.render.scene.ElementBatch;
import engine.render.scene.Scene;
import engine.render.scene.SceneManager;
import engine.testing.TestingValues;
import game.GameManager;
import game.visuals.elements.uiElements.ButtonElement;
import game.visuals.elements.uiElements.TextElement;
import game.visuals.elements.uiElements.levelSelector.LevelSelector;
import game.visuals.elements.uiElements.levelSelector.LevelSelectorTab;

import static engine.util.ColorPalette.ColorPalettes.STANDARD_BUTTON;

public class LevelSelectorScene extends Scene {

    public ElementBatch levelSelectorBatch;

    public LevelSelectorScene() {
    }

    @Override
    public void init() {

        TestingValues.instance.value = 5;

        Raylib.Texture baseThumbnail = Raylib.LoadTextureFromImage(Jaylib.GenImageChecked(1000, 1000, 10, 10, Jaylib.PINK, Jaylib.BLACK));

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
                                // TODO: Implement image assets!
                                new LevelSelectorTab("Tron", Jaylib.BLUE, baseThumbnail, false),
                                new LevelSelectorTab("The Depths", Jaylib.RED, baseThumbnail, false),
                                new LevelSelectorTab("The Generator", Jaylib.GREEN, baseThumbnail, true),
                                new LevelSelectorTab("The Mainframe", Jaylib.YELLOW, baseThumbnail, true),
                        }
                ) {
                    @Override
                    public void run() {
                        System.out.println("Level " + (hoveringTab + 1) + " selected!");

                        if (hoveringTab == 1) {
                            SceneManager.instance.setActiveScene(IngameDevScene.class);
                            //TODO: move
                            GameManager.getInstance().start();
                            return;
                        }

                        SceneManager.instance.setActiveScene(MapOneScene.class);
                        GameManager.getInstance().start();
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
