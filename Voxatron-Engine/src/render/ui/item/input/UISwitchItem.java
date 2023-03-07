package render.ui.item.input;

import com.raylib.Jaylib;
import math.LerpUtil;
import math.Vector2;
import render.task.RenderTask;
import render.task.ui.UIRoundBoxRenderTask;
import render.task.ui.UITextureRenderTask;
import render.ui.box.BoxFilter;
import render.ui.item.UIItem;
import util.BoxLayoutUtil;

import static com.raylib.Jaylib.*;

public class UISwitchItem extends UIItem {

    private final RenderTask toggledTask;
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

    public UISwitchItem(Texture texture, Vector2 position, Vector2 size, BoxFilter filter, int minWidth, int minHeight, boolean toggled, Runnable onClick) {
        this.texture = texture;
        this.position = position;
        this.size = size;
        this.currentSize = size;
        this.filter = filter;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.toggled = toggled;
        this.onClick = onClick;

        this.toggledTask = new UIRoundBoxRenderTask(position, currentSize, 0.2f, 10, DARKGRAY);
        if (toggled) ((UIRoundBoxRenderTask) toggledTask).color = GREEN;
        addTask(toggledTask);
        ((UIRoundBoxRenderTask) tasks.get(0)).lines = true;
        addTask(new UITextureRenderTask(position, texture));
    }

    @Override
    public void update() {
        Vector2 posOnScreen = BoxLayoutUtil.applyFilter(screen.position, screen.size, filter);
        Vector2 movedPosition = position.add(posOnScreen);

        if (isMouseOver(movedPosition, currentSize)) {
            if (Jaylib.IsMouseButtonPressed(0)) {
                toggled = !toggled;
                if (toggled) ((UIRoundBoxRenderTask) toggledTask).color = GREEN;
                else ((UIRoundBoxRenderTask) toggledTask).color = DARKGRAY;
                onClick.run();
            }
            // give it a small boost on the first frame
            if (hoverTime == 0) hoverTime = 0.48f;
            // add hovertime until it reaches 1
            hoverTime += 0.12f;
            if (hoverTime > 1) {
                hoverTime = 1;
            }
        } else {
            // give it a small boost on the first frame
            if (hoverTime == 1) hoverTime = 0.76f;
            // remove hovertime until it reaches 0
            hoverTime -= 0.06f;
            if (hoverTime < 0) {
                hoverTime = 0;
            }
        }

        // slerp between the min and max size
        currentSize = new Vector2(LerpUtil.cubic(size.x, minWidth, hoverTime), LerpUtil.cubic(size.y, minHeight, hoverTime));
        ((UIRoundBoxRenderTask) tasks.get(0)).position = movedPosition;
        ((UIRoundBoxRenderTask) tasks.get(0)).size = currentSize;

        // change the size of the texture
        texture = texture.width((int) currentSize.x).height((int) currentSize.y);

        ((UITextureRenderTask) tasks.get(1)).position = movedPosition.subtract(currentSize.divide(new Vector2(2, 2)));
    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {
        this.position = position.add(this.position);
    }
}
