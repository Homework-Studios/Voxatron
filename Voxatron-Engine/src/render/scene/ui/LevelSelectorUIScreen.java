package render.scene.ui;

import math.Vector2;
import render.ui.UIScreen;
import render.ui.box.BoxFilter;
import render.ui.item.custom.UILevelSelector;

import static com.raylib.Jaylib.*;

public class LevelSelectorUIScreen extends UIScreen {

    public LevelSelectorUIScreen() {
        super(new Vector2(), new Vector2(GetScreenWidth(), GetScreenHeight()));

        addItem(new UILevelSelector(new Vector2(), new Vector2(600, 900), BoxFilter.CENTER, 600, 900, () -> {}));
    }



}
