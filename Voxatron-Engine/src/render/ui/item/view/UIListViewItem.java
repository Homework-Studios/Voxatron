package render.ui.item.view;

import math.Vector2;
import render.task.ui.UIRoundBoxRenderTask;
import render.ui.box.BoxFilter;
import render.ui.item.UIItem;
import util.BoxLayoutUtil;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Jaylib.DARKGRAY;

public class UIListViewItem extends UIItem {
    public Vector2 position;
    public Vector2 size;
    public BoxFilter filter;

    //TODO: switch to executable task
    public UIRoundBoxRenderTask frame;

    public UIListViewItem(Vector2 position, Vector2 size, BoxFilter filter) {
        this.position = position;
        this.size = size;
        this.filter = filter;

        frame = new UIRoundBoxRenderTask(position, size, 0.2f, 10, BLACK);
        addTask(frame);
    }

    @Override
    public void update() {
        Vector2 posOnScreen = BoxLayoutUtil.applyFilter(screen.position, screen.size, filter);
        Vector2 movedPosition = position.add(posOnScreen);
        Vector2 topLeft = movedPosition.subtract(frame.size.divide(new Vector2(2, 2)));

        UIRoundBoxRenderTask item = new UIRoundBoxRenderTask(topLeft, new Vector2(200, 200), 0.2f, 10, DARKGRAY);
        addTask(item);
        item.lines = true;

        frame.position = movedPosition;
    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {

    }
}
