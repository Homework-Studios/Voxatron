package render.ui.item.input;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import math.LerpUtil;
import math.Vector2;
import render.task.RenderTask;
import render.task.ui.UiRoundBoxRenderTask;
import render.task.ui.UiTextureRenderTask;
import render.ui.box.BoxFilter;
import render.ui.item.UIItem;
import util.BoxLayoutUtil;

import static com.raylib.Jaylib.*;

public class UISwitchItem extends UIItem {

    public Texture texture;
    public Vector2 position;
    public Vector2 size;
    public Vector2 currentSize;
    public BoxFilter filter;
    public int minWidth;
    public int minHeight;

    public boolean toggled = false;
    public Runnable onClick;

    private float hoverTime = 0;
    private RenderTask toggledTask;

    public UISwitchItem(Texture texture, Vector2 position, Vector2 size, BoxFilter filter, int minWidth, int minHeight,boolean toggled, Runnable onClick) {
        this.texture = texture;
        this.position = position;
        this.size = size;
        this.currentSize = size;
        this.filter = filter;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.toggled = toggled;
        this.onClick = onClick;

        this.toggledTask = new UiRoundBoxRenderTask(position, currentSize, 0.2f, 10, DARKGRAY);
        addTask(toggledTask);
        ((UiRoundBoxRenderTask)tasks.get(0)).lines = true;
        addTask(new UiTextureRenderTask(position, texture));
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
            if(Jaylib.IsMouseButtonPressed(0)) {
                toggled = !toggled;
                if(toggled) ((UiRoundBoxRenderTask)toggledTask).color = GREEN;
                    else((UiRoundBoxRenderTask)toggledTask).color = DARKGRAY;
                onClick.run();
            }
            // give it a small boost on the first frame
            if (hoverTime == 0) hoverTime = 0.48f;
            // add hovertime until it reaches 1
            hoverTime += 0.12f;
            if(hoverTime > 1) {
                hoverTime = 1;
            }
        } else {
            // give it a small boost on the first frame
            if (hoverTime == 1) hoverTime = 0.76f;
            // remove hovertime until it reaches 0
            hoverTime -= 0.06f;
            if(hoverTime < 0) {
                hoverTime = 0;
            }
        }

        // slerp between the min and max size
        currentSize = new Vector2(LerpUtil.cubic(size.x, minWidth, hoverTime), LerpUtil.cubic(size.y, minHeight, hoverTime));
        ((UiRoundBoxRenderTask)tasks.get(0)).position = movedPosition;
        ((UiRoundBoxRenderTask)tasks.get(0)).size = currentSize;

        // change the size of the texture
        texture = texture.width((int)currentSize.x).height((int)currentSize.y);

        ((UiTextureRenderTask)tasks.get(1)).position = movedPosition.subtract(currentSize.divide(new Vector2(2, 2)));
    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {
        this.position = position.add(this.position);
    }
}
