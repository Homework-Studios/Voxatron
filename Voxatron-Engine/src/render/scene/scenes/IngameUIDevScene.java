package render.scene.scenes;

import com.raylib.Jaylib;
import math.Vector2;
import render.scene.Element;
import render.scene.Scene;
import render.scene.scenes.uiElements.levelButtons.TowerPanel;
import render.scene.scenes.uiElements.levelButtons.TowerSelector;

public class IngameUIDevScene extends Scene {
    ElementBatch elementBatch;

    public IngameUIDevScene() {
        super();
    }

    @Override
    public void init() {
        elementBatch = new ElementBatch(new Element[]{
                new TowerSelector(Vector2.byScreenPercent(50, 85), Vector2.byScreenPercent(85, 25), new TowerPanel[]{
                        new TowerPanel("Test1", Jaylib.RED),
                        new TowerPanel("Test2", Jaylib.BLUE),
                        new TowerPanel("Test3", Jaylib.GREEN),
                        new TowerPanel("Test4", Jaylib.YELLOW),
                        new TowerPanel("Test5", Jaylib.PURPLE),
                        new TowerPanel("Test6", Jaylib.ORANGE),
                        new TowerPanel("Test7", Jaylib.BROWN),
                        new TowerPanel("Test8", Jaylib.PINK),
                        new TowerPanel("Test9", Jaylib.LIME),
                        new TowerPanel("Test10", Jaylib.DARKGREEN),
                        new TowerPanel("Test11", Jaylib.SKYBLUE),
                        new TowerPanel("Test12", Jaylib.DARKBLUE)
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
