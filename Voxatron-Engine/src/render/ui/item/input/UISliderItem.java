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

import static com.raylib.Jaylib.DARKGRAY;
import static com.raylib.Jaylib.Texture;

public class UISliderItem extends UIItem {

    public Vector2 position;
    public Vector2 size;
    public Vector2 currentSize;
    public BoxFilter filter;
    public int minWidth = 1;
    public int minHeight = 1;

    public Vector2 sliderPosition;
    public RenderTask slider;
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
        ((UiRoundBoxRenderTask)tasks.get(0)).lines = true;
        Vector2 sliderSize = size.multiply(new Vector2(0.1f, 1f));
        this.minWidth = (int)sliderSize.x * 2;
        slider = new UiRoundBoxRenderTask(sliderPosition,sliderSize, 0.2f, 10, DARKGRAY);
        addTask(slider);
        ((UiRoundBoxRenderTask)slider).lines = true;

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
        Vector2 movedSliderPosition = sliderPosition.add(posOnScreen);

        if(isHovered(new Vector2(Raylib.GetMouseX(), Raylib.GetMouseY()))) {
            if(Jaylib.IsMouseButtonPressed(0)) {
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
        currentSize = new Vector2(LerpUtil.cubic(size.x, minWidth,1- hoverTime), LerpUtil.cubic(size.y, minHeight, hoverTime));
        ((UiRoundBoxRenderTask)tasks.get(0)).position = movedPosition;
        ((UiRoundBoxRenderTask)slider).position = movedSliderPosition;
        ((UiRoundBoxRenderTask)slider).size = currentSize;

    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {
        this.position = position.add(this.position);
    }
}
