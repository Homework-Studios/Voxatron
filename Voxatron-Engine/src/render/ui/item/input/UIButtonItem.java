package render.ui.item.input;

import com.raylib.Raylib;
import math.Vector2;
import render.task.ui.UiRoundBoxRenderTask;
import render.task.ui.UiTextureRenderTask;
import render.ui.box.BoxFilter;
import render.ui.item.UIItem;
import util.BoxLayoutUtil;

import static com.raylib.Jaylib.DARKGRAY;

public class UIButtonItem extends UIItem {

    public Vector2 position;
    public Vector2 size;
    public BoxFilter filter;

    public UIButtonItem(Vector2 position, Vector2 size, BoxFilter filter) {
        this.position = position;
        this.size = size;
        this.filter = filter;

        addTask(new UiRoundBoxRenderTask(position, size, 0.2f, 10, DARKGRAY));
    }

    @Override
    public void update() {
        Vector2 posOnScreen = BoxLayoutUtil.applyFilter(screen.position, screen.size, filter);
        Vector2 movedPosition = position.add(posOnScreen);

        ((UiRoundBoxRenderTask)tasks.get(0)).position = movedPosition;
    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {
        this.position = position.add(this.position);
    }
}
