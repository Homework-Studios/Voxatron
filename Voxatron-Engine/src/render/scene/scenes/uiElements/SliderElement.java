package render.scene.scenes.uiElements;

import static com.raylib.Raylib.*;

import com.raylib.Jaylib;
import math.Vector2;
import render.scene.Element;

public class SliderElement extends Element {

    public Vector2 position;
    public Vector2 size;
    public Vector2 handlePosition;
    public Vector2 handleSize;

    public Color color;
    public Color bgColor;
    public Color hlColor;
    public Color hlpColor;

    // 0 - 100
    public int sliderValue;

    public boolean isHovering = false;
    public boolean isHoveringHandle = false;
    public boolean isDragging = false;

    private Rectangle sliderRectangle = new Rectangle();
    private Rectangle handleRectangle = new Rectangle();

    private Runnable onValueChanged;

    private Vector2 calcHandlePosition() {
        // keep the handle size in the slider
        return new Vector2(position.x - size.x / 2 + handleSize.x / 2 + (size.x - handleSize.x) * sliderValue / 100, position.y);
    }

    private int calcSliderValue() {
        // keep the handle size in the slider
        return (int) ((handlePosition.x - position.x + size.x / 2 - handleSize.x / 2) * 100 / (size.x - handleSize.x));
    }

    private int clampHandleX(int x) {
        return (int) Clamp(x, position.x - size.x / 2 + handleSize.x / 2, position.x + size.x / 2 - handleSize.x / 2);
    }

    public SliderElement(Vector2 position, Vector2 size, Vector2 handleSize, int sliderValue, Color color, Color bgColor, Color hlColor, Color hlpColor, Runnable onValueChanged) {
        this.position = position;
        this.size = size;
        this.handleSize = handleSize;
        this.sliderValue = sliderValue;
        this.color = color;
        this.bgColor = bgColor;
        this.hlColor = hlColor;
        this.hlpColor = hlpColor;
        this.handlePosition = calcHandlePosition();
        this.onValueChanged = onValueChanged;
    }

    @Override
    public void update() {
        // create slider rectangle
        sliderRectangle = new Rectangle().x(position.x - size.x / 2).y(position.y - size.y / 2).width(size.x).height(size.y);
        // create handle rectangle
        handleRectangle = new Rectangle().x(handlePosition.x - handleSize.x / 2).y(handlePosition.y - handleSize.y / 2).width(handleSize.x).height(handleSize.y);

        // check if hovering over slider
        isHovering = CheckCollisionPointRec(GetMousePosition(), sliderRectangle);

        // check if hovering over handle
        isHoveringHandle = CheckCollisionPointRec(GetMousePosition(), handleRectangle);

        // check if mouse button left is down and hovering over handle
        if (isHoveringHandle && IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
            isDragging = true;
        }

        // check if mouse button left is up
        if (IsMouseButtonReleased(MOUSE_BUTTON_LEFT)) {
            isDragging = false;
        }

        // check if dragging
        if (isDragging) {
            // update handle position
            handlePosition = new Vector2(clampHandleX((int) GetMousePosition().x()), position.y);
            // update slider value
            sliderValue = calcSliderValue();

            // run onValueChanged
            onValueChanged.run();
        }

    }

    @Override
    public void render() {
        // draw slider
        DrawRectangleRoundedLines(sliderRectangle, 0.1f, 5, 5, color);
        // draw handle
        DrawRectangleRounded(handleRectangle, 0.3f, 5, isDragging ? hlpColor : isHoveringHandle ? hlColor : color);
    }
}
