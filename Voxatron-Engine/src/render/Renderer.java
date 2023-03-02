package render;

import render.Scene.SceneManager;
import com.raylib.java.Raylib;
import com.raylib.java.core.Color;

public class Renderer {

    Raylib raylib;
    SceneManager sceneManager = new SceneManager();

    boolean isRunning = false;

    public Renderer(Raylib raylib) {
        this.raylib = raylib;
    }

    public void begin() {
        isRunning = true;
        loop();
    }

    public void loop() {
        while (isRunning && !raylib.core.WindowShouldClose()) {
            raylib.core.BeginDrawing();
            raylib.core.ClearBackground(Color.BLACK);

            sceneManager.render(raylib);

            raylib.core.EndDrawing();
        }
    }

}
