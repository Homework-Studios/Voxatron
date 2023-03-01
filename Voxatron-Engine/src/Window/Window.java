package Window;

import com.raylib.java.Config;
import com.raylib.java.Raylib;
import com.raylib.java.core.rCore;

public class Window {

    Raylib raylib;

    public Window(Raylib raylib) {
        this.raylib = raylib;
    }

    public void init() {
        // VSync
        rCore.SetConfigFlags(Config.ConfigFlag.FLAG_VSYNC_HINT);
        // MSAA 4x
        rCore.SetConfigFlags(Config.ConfigFlag.FLAG_MSAA_4X_HINT);
        // Fullscreen
        rCore.SetConfigFlags(Config.ConfigFlag.FLAG_FULLSCREEN_MODE);

        raylib.core.InitWindow(rCore.GetScreenWidth(), rCore.GetScreenHeight(), "Voxatron");
        raylib.core.SetTargetFPS(60);
    }

}
