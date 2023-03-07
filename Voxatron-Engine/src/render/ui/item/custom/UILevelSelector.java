package render.ui.item.custom;

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

public class UILevelSelector extends UIItem {

    public Vector2 position;
    public Vector2 size;
    public Vector2 currentSize;
    public BoxFilter filter;
    public int minWidth;
    public int minHeight;

    public Runnable onClick;

    private float hoverTime = 0;

    public UILevelSelector(Vector2 position, Vector2 size, BoxFilter filter, int minWidth, int minHeight, Runnable onClick) {
        this.position = position;
        this.size = size;
        this.currentSize = size;
        this.filter = filter;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.onClick = onClick;

        addTask(new UIRoundBoxRenderTask(position, currentSize, 0.1f, 10, DARKGRAY));
        ((UIRoundBoxRenderTask) tasks.get(0)).lines = true;

        // Now two rendertasks for the lastLevel and the nextLevel
        addTask(new UIRoundBoxRenderTask(position, currentSize.subtract(new Vector2(currentSize.x / 2, currentSize.y / 2)), 0.1f, 10, DARKGRAY));
        ((UIRoundBoxRenderTask) tasks.get(1)).lines = true;
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

        if (isHovered(new Vector2(Raylib.GetMouseX(), Raylib.GetMouseY()))) {
            if (Jaylib.IsMouseButtonPressed(0)) {
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
    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {
        this.position = position.add(this.position);
    }
}
