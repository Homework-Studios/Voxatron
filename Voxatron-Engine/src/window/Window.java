package window;


import com.raylib.Raylib;
import engine.assets.Asset;
import render.Renderer;
import render.scene.Scene;
import render.scene.SceneManager;

import java.awt.*;

import static com.raylib.Raylib.*;

public class Window {

    public static Window instance;

    boolean isFullscreen = false;


    private Point position;
    private Dimension size;

    public Window() {
        instance = this;
    }

    public void init(Point position, Dimension size) {
        new SettingsManager();

        this.position = position;
        this.size = size;

        if (SettingsManager.instance.getSetting("aa").equals("1")) {
            SetConfigFlags(FLAG_MSAA_4X_HINT);
        }
        SetConfigFlags(FLAG_MSAA_4X_HINT);
        SetConfigFlags(FLAG_WINDOW_MAXIMIZED);
        SetConfigFlags(FLAG_WINDOW_UNDECORATED);
        SetConfigFlags(FLAG_VSYNC_HINT);


        // Init window
        if (size.width == 0 || size.height == 0)
            size = new Dimension(GetScreenWidth(), GetScreenHeight());
        System.out.println("Initializing window with size: " + size + " and position: " + position);
        InitWindow(size.width, size.height, "Voxatron");
        SetWindowPosition(position.x, position.y);
        SetTargetFPS(60);

        // Rebixender Rebix, bitte neachstes mal richtige Indents bei den Comments machen!
        ToggleFullscreen();

        String path = System.getProperty("user.dir") + "\\Voxatron-Engine\\src\\window\\icon.png";
        SetWindowIcon(LoadImage(path));
        InitAudioDevice();

        // TODO: Remove this shit WARNING!
        Raylib.SetMasterVolume(0);

        //WARNING: HERE IS NOT THE PLACE TO RENDER ANYTHING. NO WHILE LOOP OR ANYTHING
    }

    public void stop() {
        Renderer.instance.stop();
    }

    // Will be called after the last frame is rendered so it's the place to clean up things
    public void finish() {
        CloseWindow();
    }

    public void reopenWindow() {
        finish();
        Asset.clearAssets();
        init(this.position, this.size);
        for (Scene scene : SceneManager.instance.scenes) {
            scene.init();
        }
    }

}
