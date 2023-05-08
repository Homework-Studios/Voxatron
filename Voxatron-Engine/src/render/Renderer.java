package render;

import com.raylib.Raylib;
import debug.DebugDraw;
import window.Window;

import static com.raylib.Jaylib.BLACK;


public class Renderer {

    public static Renderer instance;

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
            Raylib.BeginDrawing();
            Raylib.ClearBackground(BLACK);

            Raylib.EndDrawing();
        }
        Window.instance.finish();
    }
}
