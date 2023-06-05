package engine.window;


import com.raylib.Raylib;
import engine.assets.Asset;
import engine.render.Renderer;
import engine.render.scene.Scene;
import engine.render.scene.SceneManager;

import static com.raylib.Raylib.*;

public class Window {

    public static Window instance;

    boolean isFullscreen = false;

    public Window() {
        instance = this;
    }

    public void init() {
        new SettingsManager();

        // Set configuration flags for window creation
        if (SettingsManager.instance.getSetting("aa").equals("1")) {
            SetConfigFlags(FLAG_MSAA_4X_HINT);
        }
        SetConfigFlags(FLAG_MSAA_4X_HINT);
        SetConfigFlags(FLAG_WINDOW_MAXIMIZED);
        SetConfigFlags(FLAG_WINDOW_UNDECORATED);
        SetConfigFlags(FLAG_VSYNC_HINT);


        // Init window
        InitWindow(GetScreenWidth(), GetScreenHeight(), "Voxatron");
        SetTargetFPS(60);

        ToggleFullscreen();

        String path = System.getProperty("user.dir") + "\\Voxatron-Engine\\src\\window\\icon.png";
        SetWindowIcon(LoadImage(path));
        InitAudioDevice();

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
        init();
        for (Scene scene : SceneManager.instance.scenes) {
            scene.init();
        }
    }

}
