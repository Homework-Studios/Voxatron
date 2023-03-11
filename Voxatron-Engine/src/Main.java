import assets.Asset;
import assets.Assets;
import assets.allAssets.ImageAsset;
import input.Input;
import render.Renderer;
import window.Window;

import java.awt.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        new Input();

        Assets.init();
        Asset asset = new ImageAsset("test", "");
        asset.createAsset();
        asset.saveAsset();

        List<Image> images = Assets.IMAGE_ASSET.getImageAsset("test").images;

        Window window = new Window();
        window.init();

        Renderer renderer = new Renderer();
        renderer.begin();
    }
}