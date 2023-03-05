package render.scene.ui;

import math.Vector2;
import render.ui.UIScreen;
import render.ui.box.BoxFilter;
import render.ui.item.basic.UiRoundBoxItem;
import render.ui.item.image.UiImageItem;
import render.ui.item.text.UITextBoxItem;

import static com.raylib.Jaylib.*;

public class MainMenuUIScreen extends UIScreen {

    public MainMenuUIScreen() {
        super(new Vector2(), new Vector2(GetScreenWidth(), GetScreenHeight()));

        String path = System.getProperty("user.dir") + "\\Voxatron-Engine\\src\\window\\icon.png";
        addItem(new UiImageItem(LoadTexture(path), new Vector2(0, 0), BoxFilter.CENTER));
    }
}
