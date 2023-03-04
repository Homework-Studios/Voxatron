package render;

import com.raylib.Raylib;
import render.scene.SceneManager;
import render.task.RenderTask;


public class Renderer {

    public static Renderer instance;

    SceneManager sceneManager = new SceneManager();

    boolean isRunning = false;

    public Renderer() {
        instance = this;
    }

    public void begin() {
        isRunning = true;
        loop();
    }

    public void loop() {
        while (isRunning && !Raylib.WindowShouldClose()) {
            Raylib.BeginDrawing();
            Raylib.ClearBackground(Raylib.ColorFromHSV(0,0,0));

            sceneManager.update();
            sceneManager.render();

            Raylib.EndDrawing();
        }
    }

    public void execute(RenderTask renderable) {
        renderable.render();
    }
}
