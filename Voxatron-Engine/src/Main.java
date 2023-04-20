import assets.Asset;
import engine.EngineForm;
import input.Input;
import render.Renderer;
import window.Window;

import java.awt.*;


public class Main {
    public static boolean DEVELOPER_MODE = true;

    public static void main(String[] args) {
        new Input();
        Point p = new Point(0, 0);
        Dimension dim = new Dimension(0, 0);

        if (DEVELOPER_MODE) {
            EngineForm form = new EngineForm();
            form.setVisible(true);
            p = form.Game.getLocationOnScreen();
            dim = form.Game.getSize();
        }

        //init assets and create a test asset
        if (args.length > 0 && !args[0].equals("")) {
            Asset.ASSET_DIR = args[0];
        }
        Asset.init();


        p.move(p.x * 2 + 1, p.y * 2 + 1);
        dim = new Dimension(dim.width * 2, dim.height * 2);
        Window window = new Window();

        window.init(p, dim);

        Renderer renderer = new Renderer();
        renderer.begin();
    }
}