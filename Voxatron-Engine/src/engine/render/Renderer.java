package engine.render;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.debug.DebugDraw;
import engine.render.camera.Camera;
import engine.render.scene.SceneManager;
import engine.render.shader.ShaderManager;
import engine.window.Window;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.Vector3;
import static com.raylib.Raylib.rlDisableDepthTest;


public class Renderer {

    public static Renderer instance;

    public static SceneManager sceneManager;
    public static Camera camera;
    public boolean isDebugOverlay = true;
    public boolean mode3d = false;
    boolean isRunning = false;

    public Renderer() {
        instance = this;

        camera = new Camera(new Vector3().x(300).y(200), new Vector3(), new Vector3().y(1), 45, Camera.PERSPECTIVE);
        ShaderManager shaderManager = new ShaderManager();
        shaderManager.init();

        sceneManager = new SceneManager();
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
            camera.update();
            ShaderManager.instance.update();

            Raylib.BeginDrawing();
            Raylib.ClearBackground(sceneManager.activeScene.clearColor);

            sceneManager.render();

            Raylib.EndDrawing();
        }
        Window.instance.finish();
    }

    public void toggleMode3d() {
        mode3d = !mode3d;

        if (mode3d) {
            Raylib.rlEnableDepthTest();
            Raylib.BeginMode3D(camera.getCamera());
        } else {
            Raylib.EndMode3D();
            Raylib.rlDisableDepthTest();
        }
    }

    //public static void updateCamera() {
    //    Matrix rotation = Raylib.MatrixRotate(camera.up(), 0);
    //    Raylib.Vector3 view = Jaylib.Vector3Subtract(camera._position(), camera.target());
    //    view = Jaylib.Vector3Transform(view, rotation);
    //    camera._position(Jaylib.Vector3Add(camera.target(), view));
    //}
}
