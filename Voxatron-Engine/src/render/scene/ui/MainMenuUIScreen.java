package render.scene.ui;

import com.raylib.Raylib;
import debug.DebugDraw;
import math.Vector2;
import render.ui.UIScreen;
import render.ui.box.BoxFilter;
import render.ui.item.basic.UiRoundBoxItem;
import render.ui.item.image.UiImageItem;
import render.ui.item.input.UIButtonItem;
import render.ui.item.text.UITextBoxItem;
import window.Window;

import static com.raylib.Jaylib.*;

public class MainMenuUIScreen extends UIScreen {

    public MainMenuUIScreen() {
        super(new Vector2(), new Vector2(GetScreenWidth(), GetScreenHeight()));

        String path = System.getProperty("user.dir") + "\\Voxatron-Engine\\src\\resources\\";

        Raylib.Texture texture = LoadTexture(path + "VTBanner.png");
        texture = texture.width(1300);
        texture = texture.height(600);

        // load the Play.png image
        Raylib.Texture play = LoadTexture(path + "Play.png");

        // load the Credits.png image
        Raylib.Texture credits = LoadTexture(path + "Credits.png");

        // load the Leave.png image
        Raylib.Texture leave = LoadTexture(path + "Leave.png");

        addItem(new UiImageItem(texture, new Vector2(0, -300), BoxFilter.CENTER));

        addItem(new UIButtonItem(play, new Vector2(0, 20), new Vector2(950, 100), BoxFilter.CENTER, 840, 95, () -> {
            DebugDraw.instance.print("Clicked Play");
        }));

        addItem(new UIButtonItem(credits, new Vector2(0, 140), new Vector2(950, 100), BoxFilter.CENTER, 840, 95, () -> {
            DebugDraw.instance.print("Clicked Credits");
        }));

        addItem(new UIButtonItem(leave, new Vector2(0, 260), new Vector2(950, 100), BoxFilter.CENTER, 840, 95, () -> {
            Window.instance.stop();
        }));
    }
}
