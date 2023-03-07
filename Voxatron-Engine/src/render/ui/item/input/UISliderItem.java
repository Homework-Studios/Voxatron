package render.ui.item.input;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import math.LerpUtil;
import math.Vector2;
import render.task.ui.UIRoundBoxRenderTask;
import render.ui.box.BoxFilter;
import render.ui.item.UIItem;
import util.BoxLayoutUtil;

import static com.raylib.Jaylib.*;

public class UISliderItem extends UIItem {

    public Vector2 position;
    public Vector2 size;
    public Vector2 currentSize;
    public BoxFilter filter;
    public int minWidth = 2;
    public int minHeight = 2;

    public Vector2 sliderSize;
    public Vector2 sliderPosition;
    public UIRoundBoxRenderTask slider;
    public float value;
    public Runnable onClick;
    public boolean isInEdit = false;
    private float hoverTime = 0;

    public UISliderItem(Vector2 position, Vector2 size, BoxFilter filter, float value, Runnable onClick) {
        this.position = position;
        this.size = size;
        this.currentSize = size;
        this.filter = filter;
        this.value = value + 0.05f;
        this.onClick = onClick;

        this.sliderPosition = position;

        addTask(new UIRoundBoxRenderTask(position, currentSize, 0.2f, 10, DARKGRAY));
        ((UIRoundBoxRenderTask) tasks.get(0)).lines = true;
        sliderSize = size.multiply(new Vector2(0.1f, 1f));
        this.minWidth = (int) (sliderSize.x * 1.5f);
        this.minHeight = (int) sliderSize.y;
        slider = new UIRoundBoxRenderTask(sliderPosition, sliderSize, 0.5f, 10, WHITE);
        addTask(slider);
        slider.lines = true;
    }

    @Override
    public void update() {
        Vector2 screenPositionWithFilter = BoxLayoutUtil.applyFilter(screen.position, screen.size, filter);
        Vector2 movedPosition = position.add(screenPositionWithFilter);
        Vector2 movedSliderPosition = sliderPosition.add(screenPositionWithFilter).add(size.x * value - size.x * 0.5f, 0);

        Vector2 mp = new Vector2(Raylib.GetMouseX(), Raylib.GetMouseY());

        if (isMouseOver(movedPosition, currentSize) || isMouseOver(movedSliderPosition, sliderSize) || isInEdit) {
            if (Jaylib.IsMouseButtonDown(0)) {
                slider.color = GREEN;
                float relativeMouseX = movedPosition.x - Raylib.GetMouseX();
                relativeMouseX *= -1;
                relativeMouseX /= size.x;
                value = Clamp(relativeMouseX + 0.5f, 0.05f, 0.95f);
                isInEdit = true;
            } else {
                slider.color = WHITE;
                isInEdit = false;
            }

            // Increase hoverTime until it reaches 1
            hoverTime = Math.min(hoverTime + 0.8f, 1);
        } else {
            slider.color = GRAY;

            // Decrease hoverTime until it reaches 0
            hoverTime = Math.max(hoverTime - 0.06f, 0);
        }

        // Interpolate between the min and max size using cubic interpolation
        float slerpAmount = LerpUtil.cubic(0, 1, hoverTime);
        float sliderWidth = LerpUtil.cubic(size.x / 10, minWidth, slerpAmount);
        float sliderHeight = LerpUtil.cubic(size.y, minHeight, slerpAmount);
        currentSize = new Vector2(sliderWidth, sliderHeight);

        ((UIRoundBoxRenderTask) tasks.get(0)).position = movedPosition;
        slider.position = movedSliderPosition;
        slider.size = currentSize;
    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {
        this.position = position.add(this.position);
    }
}
