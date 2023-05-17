package render;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import debug.DebugDraw;
import math.Vector2;
import render.scene.SceneManager;
import testing.TestingValues;
import window.Window;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.*;


public class Renderer {

    public static Renderer instance;

    public static SceneManager sceneManager = new SceneManager();

    public boolean isDebugOverlay = true;

    boolean isRunning = false;

    public static Raylib.Camera3D camera = new Raylib.Camera3D();

    public Renderer() {
        instance = this;
    }

    public void begin() {
        isRunning = true;

        if (isDebugOverlay) {
            new DebugDraw();
        }

        camera._position(new Vector3().y(60))
                .target(new Vector3().x(0).y(0).z(0))
                .up(new Vector3().x(0).y(1).z(0))
                .fovy(45).projection(CAMERA_PERSPECTIVE);
        Jaylib.SetCameraMode(camera, CAMERA_CUSTOM);

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

    public static void updateCamera() {
        Matrix rotation = Raylib.MatrixRotate(camera.up(), 0);
        Raylib.Vector3 view = Jaylib.Vector3Subtract(camera._position(), camera.target());
        view = Jaylib.Vector3Transform(view, rotation);
        camera._position(Jaylib.Vector3Add(camera.target(), view));
    }
}
