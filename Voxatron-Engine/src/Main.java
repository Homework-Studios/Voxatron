import engine.DevelopmentConstants;
import engine.assets.Asset;
import game.GameManager;
import input.Input;
import render.Renderer;
import testing.TestingValues;
import window.Window;

import java.awt.*;
import java.util.Objects;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 0) DevelopmentConstants.DEVELOPMENT_MODE = Objects.equals(args[0], "1");
        new Input();
        Point p = new Point(0, 0);
        Dimension dim = new Dimension(0, 0);
        Asset.ASSET_DIR = System.getProperty("user.dir") + "\\Voxatron\\Assets";

        // if (true) return;
/*
        if (DevelopmentConstants.DEVELOPMENT_MODE) {
            DevelopmentConstants.ENGINE_FORM = new EngineForm();
            EngineForm form = DevelopmentConstants.ENGINE_FORM;
            form.setVisible(true);
            Thread.sleep(100);
            p = form.Game.getLocationOnScreen();
            dim = form.Game.getSize();
            System.out.println(p + " " + dim);
        }

        p.move(p.x * 2 + 1, p.y * 2 + 1);
        dim = new Dimension(dim.width * 2, dim.height * 2);
        //init engine.assets and create a test asset


 */

        //initializing
        new GameManager();
        Asset.init();
        new TestingValues();
        Window window = new Window();
        window.init(p, dim);

        Renderer renderer = new Renderer();
        renderer.begin();
    }
}