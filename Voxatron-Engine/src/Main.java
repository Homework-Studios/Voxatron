import assets.Asset;
import input.Input;
import render.Renderer;
import window.Window;

import java.awt.*;


public class Main {

    public static void main(String[] args) {
        new Input();

        EngineForm form = new EngineForm();
        form.setVisible(true);
        //init assets and create a test asset
        if (args.length > 0 && !args[0].equals("")) {
            Asset.ASSET_DIR = args[0];
        }
        Asset.init();

        Point p = form.Game.getLocationOnScreen();
        Dimension dim = form.Game.getSize();
        p.move(p.x * 2 + 1, p.y * 2 + 1);
        dim = new Dimension(dim.width * 2, dim.height * 2);
        Window window = new Window();

        window.init(p, dim);

        Renderer renderer = new Renderer();
        renderer.begin();
    }
}