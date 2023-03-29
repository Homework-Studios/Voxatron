package window;


import render.Renderer;

import java.awt.*;

import static com.raylib.Raylib.*;

public class Window {

    public static Window instance;

    public Window() {
        instance = this;
    }

    public void init(Point position, Dimension size) {

        SetConfigFlags(FLAG_MSAA_4X_HINT);
        SetConfigFlags(FLAG_WINDOW_MAXIMIZED);
        SetConfigFlags(FLAG_WINDOW_UNDECORATED);
        //TODO: Remove Focus when needed
        SetConfigFlags(FLAG_WINDOW_TOPMOST);

        // Init window
        System.out.println("Initializing window with size: " + size + " and position: " + position);
        InitWindow(size.width, size.height, "Voxatron");
        SetWindowPosition(position.x, position.y);
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
