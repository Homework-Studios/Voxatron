package window;


import com.raylib.Raylib;

import static com.raylib.Raylib.*;

public class Window {

    public boolean devMode = true;

    public Window() {
    }

    public void init() {

        // SetConfigFlags(FLAG_MSAA_4X_HINT);

        // Init window
        InitWindow(GetScreenWidth(), GetScreenHeight(), "Voxatron");
        SetTargetFPS(60);
        ToggleFullscreen();

        String path = System.getProperty("user.dir") + "\\Voxatron-Engine\\src\\window\\icon.png";
        SetWindowIcon(LoadImage(path));

        //WARNING: HERE IS NOT THE PLACE TO RENDER ANYTHING. NO WHILE LOOP OR ANYTHING
    }

}
