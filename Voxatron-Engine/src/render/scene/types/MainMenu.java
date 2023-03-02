package render.scene.types;

import input.Input;
import input.map.Mapping;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.SceneType;
import com.raylib.java.Raylib;
import com.raylib.java.core.Color;

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
