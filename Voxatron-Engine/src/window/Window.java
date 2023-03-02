package window;

import com.raylib.java.Config;
import com.raylib.java.Raylib;
import com.raylib.java.core.rCore;
import com.raylib.java.textures.Image;
import com.raylib.java.textures.rTextures;
import com.raylib.java.utils.Tracelog;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Window {

    Raylib raylib;

    public boolean devMode = true;

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

        // Get the resource path
        String resourcePath = System.getProperty("user.dir") + "\\resources";

        // Dev Flag
        if(devMode){
            resourcePath = System.getProperty("user.dir") + "\\Voxatron-Engine\\src\\resources";
        }

        Image icon = rTextures.LoadImage(resourcePath + "\\icon.png");

        // Fix weird error
        //raylib.core.SetWindowIcon(icon);
        raylib.core.InitWindow(rCore.GetScreenWidth(), rCore.GetScreenHeight(), "Voxatron");
        raylib.core.SetTargetFPS(60);
    }

}
