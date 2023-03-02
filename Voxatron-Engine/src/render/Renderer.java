package render;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import render.scene.SceneManager;


public class Renderer {

    SceneManager sceneManager = new SceneManager();

    boolean isRunning = false;

    public Renderer() {

    }

    public void begin() {
        isRunning = true;
        loop();
    }

    public void loop() {
        while (isRunning && !Raylib.WindowShouldClose()) {
            Raylib.BeginDrawing();
            Raylib.ClearBackground(Raylib.ColorFromHSV(0,0,0));

            sceneManager.render();

            Raylib.EndDrawing();
        }
    }

}
