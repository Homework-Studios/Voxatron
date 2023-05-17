package render.scene.scenes;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import math.Vector2;
import render.Renderer;
import render.scene.Element;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.scenes.tdElements.CubeElement;
import render.scene.scenes.tdElements.FloorElement;
import render.scene.scenes.uiElements.ButtonElement;
import render.scene.scenes.uiElements.TextElement;

import static util.ColorPalette.ColorPalettes.STANDARD_BUTTON;

public class IngameDevScene extends Scene {

    @Override
    public void init() {
        // addElement3d(new CubeElement(new Jaylib.Vector3(0, 0, 0), new Jaylib.Vector3(50, 5, 50)));

        addElement3d(new FloorElement());

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

        if(Jaylib.IsKeyDown(Raylib.KEY_W)){
            Renderer.camera._position(Renderer.camera._position().x(Renderer.camera._position().x() + 1));
            Renderer.camera.target(Renderer.camera.target().x(Renderer.camera.target().x() + 1));
        }

        if(Jaylib.IsKeyDown(Raylib.KEY_S)){
            Renderer.camera._position(Renderer.camera._position().x(Renderer.camera._position().x() - 1));
            Renderer.camera.target(Renderer.camera.target().x(Renderer.camera.target().x() - 1));
        }

        if(Jaylib.IsKeyDown(Raylib.KEY_A)){
            Renderer.camera._position(Renderer.camera._position().z(Renderer.camera._position().z() - 1));
            Renderer.camera.target(Renderer.camera.target().z(Renderer.camera.target().z() - 1));
        }

        if(Jaylib.IsKeyDown(Raylib.KEY_D)){
            Renderer.camera._position(Renderer.camera._position().z(Renderer.camera._position().z() + 1));
            Renderer.camera.target(Renderer.camera.target().z(Renderer.camera.target().z() + 1));
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
