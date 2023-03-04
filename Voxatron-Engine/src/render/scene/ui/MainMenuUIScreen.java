package render.scene.ui;

import math.Vector2;
import render.ui.UIScreen;
import render.ui.box.BoxFiler;
import render.ui.item.text.UITextBoxItem;

import static com.raylib.Jaylib.*;

public class MainMenuUIScreen extends UIScreen {

    public MainMenuUIScreen() {
        super(new Vector2(10, 10), new Vector2(GetScreenWidth() - 10, GetScreenHeight() - 10));

        Vector2 full = new Vector2(GetScreenWidth() - 10, GetScreenHeight() - 10);

        addItem(new UITextBoxItem(new Vector2(0,0), full, "Voxatron Main Menu", BoxFiler.CENTER));
    }
}
