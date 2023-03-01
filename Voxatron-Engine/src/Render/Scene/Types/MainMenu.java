package Render.Scene.Types;

import Input.Input;
import Input.Map.Mapping;
import Render.Scene.Scene;
import Render.Scene.SceneManager;
import Render.Scene.SceneType;
import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;

public class MainMenu extends Scene {

    public MainMenu() {
        super(SceneType.MAIN_MENU);
    }

    @Override
    public void render(Raylib raylib) {

        if(Input.instance.isKeyPressed(Mapping.TOGGLE_SCENE)){
            SceneManager.instance.setCurrentScene(SceneType.CREDITS);
        }

        raylib.text.DrawText("Main Menu!", 10, 10, 20, Color.WHITE);
    }
}
