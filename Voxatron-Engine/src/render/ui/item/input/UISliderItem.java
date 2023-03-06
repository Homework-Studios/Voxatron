package render.ui.item.input;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import debug.DebugDraw;
import math.LerpUtil;
import math.Vector2;
import render.task.ui.UiRoundBoxRenderTask;
import render.ui.box.BoxFilter;
import render.ui.item.UIItem;
import util.BoxLayoutUtil;

import static com.raylib.Jaylib.*;

public class UISliderItem extends UIItem {

    public Vector2 position;
    public Vector2 size;
    public Vector2 currentSize;
    public BoxFilter filter;
    public int minWidth = 1;
    public int minHeight = 1;

    public Vector2 sliderSize;
    public Vector2 sliderPosition;
    public UiRoundBoxRenderTask slider;
    public float value;
    public Runnable onClick;

    private float hoverTime = 0;

    public UISliderItem(Vector2 position, Vector2 size, BoxFilter filter, float value, Runnable onClick) {
        this.position = position;
        this.size = size;
        this.currentSize = size;
        this.filter = filter;
        this.value = value;
        this.onClick = onClick;

        this.sliderPosition = position;

        addTask(new UiRoundBoxRenderTask(position, currentSize, 0.2f, 10, DARKGRAY));
        ((UiRoundBoxRenderTask) tasks.get(0)).lines = true;
        sliderSize = size.multiply(new Vector2(0.1f, 1f));
        this.minWidth = (int) sliderSize.x * 2;
        this.minHeight = (int) sliderSize.y;
        slider = new UiRoundBoxRenderTask(sliderPosition, sliderSize, 0.2f, 10, WHITE);
        addTask(slider);
        slider.lines = true;

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
        Vector2 movedSliderPosition = sliderPosition.add(posOnScreen).add(size.x * value - size.x * 0.5f, 0);

        if (isHovered(new Vector2(Raylib.GetMouseX(), Raylib.GetMouseY()))) {
            if (Jaylib.IsMouseButtonDown(0)) {
                slider.color = GREEN;
                float relativeMouseX = movedPosition.x - Raylib.GetMouseX();
                relativeMouseX *= -1;
                relativeMouseX /= size.x;
                value = Clamp(relativeMouseX + 0.5f, 0, 1);
                DebugDraw.instance.print(value + " " + relativeMouseX);
            } else slider.color = WHITE;
            // give it a small boost on the first frame
            if (hoverTime == 0) hoverTime = 0.24f;
            // add hovertime until it reaches 1
            hoverTime += 0.8f;
            if (hoverTime > 1) {
                hoverTime = 1;
            }
        } else {
            slider.color = WHITE;
            // give it a small boost on the first frame
            if (hoverTime == 1) hoverTime = 0.76f;
            // remove hovertime until it reaches 0
            hoverTime -= 0.06f;
            if (hoverTime < 0) {
                hoverTime = 0;
            }
        }

        // slerp between the min and max size
        currentSize = new Vector2(LerpUtil.cubic(sliderSize.x, minWidth, hoverTime), LerpUtil.cubic(size.y, minHeight, hoverTime));
        ((UiRoundBoxRenderTask) tasks.get(0)).position = movedPosition;
        slider.position = movedSliderPosition;
        slider.size = currentSize;

    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {
        this.position = position.add(this.position);
    }
}
