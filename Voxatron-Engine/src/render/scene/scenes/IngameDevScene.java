package render.scene.scenes;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import math.Vector2;
import render.Renderer;
import render.scene.Element;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.scenes.tdElements.CubeElement;
import render.scene.scenes.uiElements.ButtonElement;
import render.scene.scenes.uiElements.TextElement;

import static com.raylib.Raylib.CAMERA_ORBITAL;
import static util.ColorPalette.ColorPalettes.STANDARD_BUTTON;

public class IngameDevScene extends Scene {

    @Override
    public void init() {
        addElement3d(new CubeElement(new Jaylib.Vector3(0, 0, 0), new Jaylib.Vector3(5, 5, 5)));

        ElementBatch levelSelectorBatch = new ElementBatch(new Element[]{
                new TextElement(
                        Vector2.byScreenPercent(0, 0),
                        Vector2.byScreenPercent(100, 15),
                        "Ingame Dev",
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
                        SceneManager.instance.setActiveScene(LevelSelectorScene.class);
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

        for (Element element : getIterableElements3d()) {
            element.update();
        }

        Renderer.updateCamera();
    }

    @Override
    public void render() {

        Jaylib.BeginMode3D(Renderer.camera);
        for (Element element : elements3d) {
            element.render();
        }
        Jaylib.EndMode3D();

        for (Element element : elements) {
            element.render();
        }
    }
}
