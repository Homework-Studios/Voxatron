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


public class Main {
    public static void main(String[] args) {
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
        if (false) return;

        if (DevelopmentConstants.DEVELOPMENT_MODE) {
            EngineForm form = new EngineForm();
            form.setVisible(true);
            p = form.Game.getLocationOnScreen();
            dim = form.Game.getSize();
        }

        //init engine.assets and create a test asset
        Asset.init();


        p.move(p.x * 2 + 1, p.y * 2 + 1);
        dim = new Dimension(dim.width * 2, dim.height * 2);
        Window window = new Window();

        window.init(p, dim);

        Renderer renderer = new Renderer();
        renderer.begin();
    }
}