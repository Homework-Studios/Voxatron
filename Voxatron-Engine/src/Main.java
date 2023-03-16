import assets.Asset;
import input.Input;
import render.Renderer;
import window.Window;

public class Main {
    public static boolean isOnline = true;

    public static void main(String[] args) {
        new Input();

        //init assets and create a test asset
        if (args.length > 0 && !args[0].equals("")) {
            isOnline = false;
            Asset.ASSET_DIR = args[0];
        }
        Asset.init();

        Window window = new Window();
        window.init();

        Renderer renderer = new Renderer();
        renderer.begin();
    }
}