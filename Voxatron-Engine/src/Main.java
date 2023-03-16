import assets.Asset;
import assets.Assets;
import assets.allAssets.ImageAsset;
import input.Input;
import render.Renderer;
import window.Window;

public class Main {

    public static void main(String[] args) {
        new Input();

        //init assets and create a test asset
        Assets.init();

        Asset asset = new ImageAsset("test", "");
        asset.createAsset();
        asset.saveAsset();

        Window window = new Window();
        window.init();

        Renderer renderer = new Renderer();
        renderer.begin();
    }
}