package render;

import com.raylib.Raylib;
import render.scene.SceneManager;
import render.task.RenderTask;

import static com.raylib.Jaylib.RED;


public class Renderer {

    public static Renderer instance;

    public boolean isDebugOverlay = false;

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

            // Debug overlay
            if(isDebugOverlay) {
                // Draw a red dot in the middle of the screen
                Raylib.DrawCircle(Raylib.GetScreenWidth() / 2, Raylib.GetScreenHeight() / 2, 5, RED);
            }

            Raylib.EndDrawing();
        }
    }

    public void execute(RenderTask renderable) {
        renderable.render();
    }
}
