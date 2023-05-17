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
        for (Element element : getIterableElements()) element.update();
        for (Element element : getIterableElements3d()) element.update();

        Vector2 move = new Vector2(0, 0);

        if(Raylib.IsKeyDown(Raylib.KEY_W)) move.x(-1);
        if(Raylib.IsKeyDown(Raylib.KEY_S)) move.x(1);
        if(Raylib.IsKeyDown(Raylib.KEY_A)) move.y(1);
        if(Raylib.IsKeyDown(Raylib.KEY_D)) move.y(-1);
        if(Raylib.IsKeyDown(Raylib.KEY_SPACE)) moveCameraV(1);
        if(Raylib.IsKeyDown(Raylib.KEY_LEFT_SHIFT)) moveCameraV(-1);

        moveCamera((int) move.x(), (int) move.y());

        Renderer.updateCamera();
    }

    private void moveCamera(int x, int z) {
        Renderer.camera._position(Renderer.camera._position().x(Renderer.camera._position().x() + x));
        //Renderer.camera.target(Renderer.camera.target().x(Renderer.camera.target().x() + x));
        Renderer.camera._position(Renderer.camera._position().z(Renderer.camera._position().z() + z));
        //Renderer.camera.target(Renderer.camera.target().z(Renderer.camera.target().z() + z));
    }

    private void moveCameraV(int y) {
        Renderer.camera._position(Renderer.camera._position().y(Renderer.camera._position().y() + y));
        //Renderer.camera.target(Renderer.camera.target().y(Renderer.camera.target().y() + y));
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
