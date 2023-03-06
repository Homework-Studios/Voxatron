package testing;

import math.Vector2;
import render.task.ui.UIRoundBoxRenderTask;
import render.ui.item.view.UIListViewItemItem;

import static com.raylib.Jaylib.DARKGRAY;

public class SettingsItem extends UIListViewItemItem {

    public SettingsItem() {
        super(new Vector2(50, 50), new Vector2(750, 100));

        UIRoundBoxRenderTask ui = new UIRoundBoxRenderTask(new Vector2(0, 0), new Vector2(750, 100), 0.2f, 10, DARKGRAY);
        addTask(ui);
    }

    @Override
    public void update() {
//TODO: implement
    }

}
