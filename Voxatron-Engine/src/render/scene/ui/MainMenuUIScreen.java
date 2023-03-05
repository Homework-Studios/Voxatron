package render.scene.ui;

import com.raylib.Raylib;
import math.Vector2;
import render.ui.UIScreen;
import render.ui.box.BoxFilter;
import render.ui.item.basic.UiRoundBoxItem;
import render.ui.item.image.UiImageItem;
import render.ui.item.input.UIButtonItem;
import render.ui.item.text.UITextBoxItem;

import static com.raylib.Jaylib.*;

public class MainMenuUIScreen extends UIScreen {

    public MainMenuUIScreen() {
        super(new Vector2(), new Vector2(GetScreenWidth(), GetScreenHeight()));

        String path = System.getProperty("user.dir") + "\\Voxatron-Engine\\src\\resources\\VTBanner.png";

        Raylib.Texture texture = LoadTexture(path);
        texture = texture.width(1000);
        texture = texture.height(500);

        addItem(new UiImageItem(texture, new Vector2(0, -300), BoxFilter.CENTER));

        addItem(new UIButtonItem(new Vector2(0, 0), new Vector2(1000, 100), BoxFilter.CENTER, 950, 70));
        addItem(new UIButtonItem(new Vector2(0, 110), new Vector2(1000, 100), BoxFilter.CENTER, 950, 70));
        addItem(new UIButtonItem(new Vector2(0, 220), new Vector2(1000, 100), BoxFilter.CENTER, 950, 70));
    }
}
