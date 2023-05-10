package window;


import engine.DevelopmentConstants;
import render.Renderer;

import javax.swing.*;
import java.awt.*;

import static com.raylib.Raylib.*;

public class Window {

    public static Window instance;

    private Point position;
    private Dimension size;

    boolean isFullscreen = false;

    public Window() {
        instance = this;
    }

    public void init(Point position, Dimension size) {

        new SettingsManager();

        this.position = position;
        this.size = size;

        if(SettingsManager.instance.getSetting("aa").equals("1")){
            SetConfigFlags(FLAG_MSAA_4X_HINT);
        }
        SetConfigFlags(FLAG_WINDOW_MAXIMIZED);
        SetConfigFlags(FLAG_WINDOW_UNDECORATED);


        // Init window
        if (size.width == 0 || size.height == 0)
            size = new Dimension(GetScreenWidth(), GetScreenHeight());
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

    // Will be called after the last frame is rendered so it's the place to clean up things
    public void finish() {
        CloseWindow();
    }

    public void resize() {
        if (DevelopmentConstants.DEVELOPMENT_MODE) {
            System.out.println("Resizing window  " + isFullscreen);
            JPanel panel = DevelopmentConstants.ENGINE_FORM.Game;
            if (isFullscreen) {
                SetWindowSize(panel.getWidth(), panel.getHeight());
                SetWindowPosition(0, 0);
                isFullscreen = false;
            } else {
                SetWindowSize(GetMonitorWidth(0), GetMonitorHeight(0));
                SetWindowPosition(0, 0);
                isFullscreen = true;
            }
        }
    }

    public void reopenWindow() {
        CloseWindow();
        init(this.position, this.size);
    }
}
