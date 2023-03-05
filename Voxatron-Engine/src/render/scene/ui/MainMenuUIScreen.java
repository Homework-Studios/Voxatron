package render.scene.ui;

import math.Vector2;
import render.ui.UIScreen;
import render.ui.box.BoxFilter;
import render.ui.item.basic.UiRoundBoxItem;
import render.ui.item.text.UITextBoxItem;

import static com.raylib.Jaylib.*;

public class MainMenuUIScreen extends UIScreen {

    public MainMenuUIScreen() {
        super(new Vector2(), new Vector2(GetScreenWidth(), GetScreenHeight()));

        Vector2 full = new Vector2(GetScreenWidth() - 10, GetScreenHeight() - 10);
    }
}
