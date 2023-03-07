package render;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import debug.DebugDraw;
import input.Input;
import input.map.Mapping;
import render.scene.SceneManager;
import render.task.RenderTask;
import window.Window;


public class Renderer {

    public static Renderer instance;

    public boolean isDebugOverlay = true;

    SceneManager sceneManager = new SceneManager();

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
            if (Input.instance.isKeyPressed(Mapping.TOGGLE_DEBUG)) {
                isDebugOverlay = !isDebugOverlay;
                DebugDraw.instance.print("Debug overlay is now " + (isDebugOverlay ? "enabled" : "disabled"));
            }

            sceneManager.update();

            Raylib.BeginDrawing();
            Raylib.ClearBackground(Raylib.ColorFromHSV(0, 0, 0));

            sceneManager.render();

            // Debug overlay
            if (isDebugOverlay) {
                DebugDraw.instance.render();
            } else DebugDraw.instance.debugStrings.clear();

            Raylib.EndDrawing();
        }
        Window.instance.finish();
    }

    public void execute(RenderTask task) {
        task.render();
    }
}
