package render.ui.item.input;

import com.raylib.Raylib;
import math.Vector2;
import render.task.ui.UiRoundBoxRenderTask;
import render.task.ui.UiTextureRenderTask;
import render.ui.box.BoxFilter;
import render.ui.item.UIItem;
import util.BoxLayoutUtil;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.DrawRectangle;

public class UIButtonItem extends UIItem {

    public Vector2 position;
    public Vector2 size;
    public BoxFilter filter;
    public int minWidth;
    public int minHeight;

    public UIButtonItem(Vector2 position, Vector2 size, BoxFilter filter, int minWidth, int minHeight) {
        this.position = position;
        this.size = size;
        this.filter = filter;
        this.minWidth = minWidth;
        this.minHeight = minHeight;

        addTask(new UiRoundBoxRenderTask(position, size, 0.2f, 10, DARKGRAY));
    }

    public boolean isHovered(Vector2 mousePosition) {
        Vector2 posOnScreen = BoxLayoutUtil.applyFilter(screen.position, screen.size, filter);
        Vector2 movedPosition = position.add(posOnScreen);
        movedPosition = movedPosition.subtract(size.divide(new Vector2(2, 2)));

        return mousePosition.x > movedPosition.x && mousePosition.x < movedPosition.x + size.x && mousePosition.y > movedPosition.y && mousePosition.y < movedPosition.y + size.y;
    }

    @Override
    public void update() {
        Vector2 posOnScreen = BoxLayoutUtil.applyFilter(screen.position, screen.size, filter);
        Vector2 movedPosition = position.add(posOnScreen);

        if(isHovered(new Vector2(Raylib.GetMouseX(), Raylib.GetMouseY()))) {
            ((UiRoundBoxRenderTask)tasks.get(0)).color = LIGHTGRAY;
        } else {
            ((UiRoundBoxRenderTask)tasks.get(0)).color = DARKGRAY;
        }

        ((UiRoundBoxRenderTask)tasks.get(0)).position = movedPosition;
        ((UiRoundBoxRenderTask)tasks.get(0)).size = size;
    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {
        this.position = position.add(this.position);
    }
}
