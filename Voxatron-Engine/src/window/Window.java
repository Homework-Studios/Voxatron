package window;


import com.raylib.Raylib;
import render.Renderer;
import render.task.RenderTask;

import static com.raylib.Raylib.*;

public class Window {

    public static Window instance;

    public Window() {
        instance = this;
    }

    public void init() {

        SetConfigFlags(FLAG_MSAA_4X_HINT);
        SetConfigFlags(FLAG_WINDOW_MAXIMIZED);
        SetConfigFlags(FLAG_WINDOW_UNDECORATED);

        // Init window
        InitWindow(GetScreenWidth(), GetScreenHeight(), "Voxatron");
        SetTargetFPS(60);

        String path = System.getProperty("user.dir") + "\\Voxatron-Engine\\src\\window\\icon.png";
        SetWindowIcon(LoadImage(path));

        //WARNING: HERE IS NOT THE PLACE TO RENDER ANYTHING. NO WHILE LOOP OR ANYTHING
    }

    public void stop() {
        Renderer.instance.stop();
    }

    public void finish() {
        CloseWindow();
    }
}
