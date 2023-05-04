import engine.DevelopmentConstants;
import engine.EngineForm;
import engine.assets.Asset;
import engine.scripting.ScriptingManager;
import input.Input;
import render.Renderer;
import window.Window;

import javax.script.ScriptException;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 0) DevelopmentConstants.DEVELOPMENT_MODE = Objects.equals(args[0], "1");
        new Input();
        Point p = new Point(0, 0);
        Dimension dim = new Dimension(0, 0);
        Asset.ASSET_DIR = DevelopmentConstants.ASSET_DIR;
        try {
            new ScriptingManager();
        } catch (ScriptException | IOException e) {
            throw new RuntimeException(e);
        }

        //Testing used to skip the engine and only test scripts if(true) return; cause of intellij errors
//        if (true) return;

        if (DevelopmentConstants.DEVELOPMENT_MODE) {
            DevelopmentConstants.ENGINE_FORM = new EngineForm();
            EngineForm form = DevelopmentConstants.ENGINE_FORM;
            form.setVisible(true);
            Thread.sleep(100);
            p = form.Game.getLocationOnScreen();
            dim = form.Game.getSize();
            System.out.println(p + " " + dim);
        }
        if (DevelopmentConstants.DEVELOPMENT_MODE)
            ScriptingManager.instance.updateScripts();

        //TODO: remove this
        p.move(p.x + 94, p.y + 7);
        dim = new Dimension(dim.width + 212, dim.height + 159);
        //init engine.assets and create a test asset
        Asset.init();
        Window window = new Window();
        window.init(p, dim);

        Renderer renderer = new Renderer();
        renderer.begin();
    }
}