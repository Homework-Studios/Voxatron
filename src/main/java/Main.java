import com.raylib.Raylib;
import engine.assets.Asset;
import engine.input.Input;
import engine.render.Renderer;
import engine.testing.TestingValues;
import engine.window.Window;


public class Main {

    public static void main(String[] args) {
        new Input();
        //Initializing Assets
        Asset.ASSET_DIR = System.getProperty("user.dir") + "\\Voxatron\\Assets";

        Raylib.SetTraceLogLevel(Raylib.LOG_WARNING);

        //initializing
        Asset.init();
        Window window = new Window();
        window.init();
        new TestingValues();

        Renderer renderer = new Renderer();
        renderer.begin();
    }
}