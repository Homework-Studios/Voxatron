package render;

import com.raylib.Raylib;
import debug.DebugDraw;
import render.scene.SceneManager;
import testing.TestingValues;
import window.Window;

import static com.raylib.Jaylib.BLACK;


public class Renderer {

    public static Renderer instance;

    public static SceneManager sceneManager = new SceneManager();

    public boolean isDebugOverlay = true;

    boolean isRunning = false;

    public Renderer() {
        instance = this;
    }

    public void begin() {
        isRunning = true;

        if (isDebugOverlay) {
            new DebugDraw();
        }

        loop();
    }

    public void stop() {
        isRunning = false;
    }

    public void loop() {
        while (isRunning && !Raylib.WindowShouldClose()) {

            sceneManager.update();
            TestingValues.instance.update();

            Raylib.BeginDrawing();
            Raylib.ClearBackground(BLACK);

            sceneManager.render();

            Raylib.EndDrawing();
        }
        Window.instance.finish();
    }
}
