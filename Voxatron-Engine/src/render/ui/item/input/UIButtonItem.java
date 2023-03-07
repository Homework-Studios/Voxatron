package render.ui.item.input;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import math.LerpUtil;
import math.Vector2;
import render.task.ui.UIRoundBoxRenderTask;
import render.task.ui.UITextureRenderTask;
import render.ui.box.BoxFilter;
import render.ui.item.UIItem;
import util.BoxLayoutUtil;

import static com.raylib.Jaylib.DARKGRAY;

public class UIButtonItem extends UIItem {

    public Raylib.Texture texture;
    public Vector2 position;
    public Vector2 size;
    public Vector2 currentSize;
    public BoxFilter filter;
    public int minWidth;
    public int minHeight;

    public Runnable onClick;

    private float hoverTime = 0;
    private float pushedTime = 0;

    public UIButtonItem(Raylib.Texture texture, Vector2 position, Vector2 size, BoxFilter filter, int minWidth, int minHeight, Runnable onClick) {
        this.texture = texture;
        this.position = position;
        this.size = size;
        this.currentSize = size;
        this.filter = filter;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.onClick = onClick;

        addTask(new UIRoundBoxRenderTask(position, currentSize, 0.2f, 10, DARKGRAY));
        ((UIRoundBoxRenderTask) tasks.get(0)).lines = true;
        addTask(new UITextureRenderTask(position, texture));
    }

    @Override
    public void update() {
        Vector2 posOnScreen = BoxLayoutUtil.applyFilter(screen.position, screen.size, filter);
        Vector2 movedPosition = position.add(posOnScreen);

        if (isMouseOver(movedPosition, currentSize)) {
            if (Jaylib.IsMouseButtonPressed(0)) {
                pushedTime = 1f;
                onClick.run();
            }
            if (pushedTime > 0) {
                pushedTime -= 0.1f;
                if (pushedTime < 0) pushedTime = 0;
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
        currentSize = new Vector2(LerpUtil.cubic(size.x, minWidth, hoverTime - pushedTime), LerpUtil.cubic(size.y, minHeight, hoverTime));
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
