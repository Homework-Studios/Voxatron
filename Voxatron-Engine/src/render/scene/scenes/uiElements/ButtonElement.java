package render.scene.scenes.uiElements;

import math.Vector2;
import render.scene.Element;
import util.UiUtil;

import static com.raylib.Raylib.*;

public abstract class ButtonElement extends Element implements Runnable {

    private final float scaleFactor = .95f;
    public Vector2 position;
    public Vector2 size;
    public String text;
    public float textSize;
    public Color color;
    public Color bgColor;
    public Color hlColor;
    public Color hlpColor;
    public boolean isHovered;
    public boolean isPressed;
    private Rectangle buttonRectangle = new Rectangle();

    /**
     * Creates a new ButtonElement.
     *
     * @param position The position of the button.
     * @param size     The size of the button.
     * @param text     The text of the button.
     * @param textSize The size of the text of the button.
     * @param color    The color of the text.
     * @param bgColor  The color of the background.
     * @param hlColor  The color of the text when highlighted.
     * @param hlpColor The color of the text when highlighted and pressed.
     * @param onClick  The action that is executed when the button is clicked.
     */

    public ButtonElement(Vector2 position, Vector2 size, String text, float textSize, Color color, Color bgColor, Color hlColor, Color hlpColor) {
        this.position = position;
        this.size = size;
        this.text = text;
        this.textSize = textSize;
        this.color = color;
        this.bgColor = bgColor;
        this.hlColor = hlColor;
        this.hlpColor = hlpColor;
    }


    public void update() {
        buttonRectangle = new Rectangle().x(position.x - size.x / 2).y(position.y - size.y / 2).width(size.x).height(size.y);

        // check if mouse is hovering over button
        isHovered = CheckCollisionPointRec(GetMousePosition(), buttonRectangle);

        // check if mouse button left is down
        isPressed = isHovered && IsMouseButtonDown(MOUSE_BUTTON_LEFT);

        // check if mouse button left is up
        if (isHovered && IsMouseButtonReleased(MOUSE_BUTTON_LEFT)) {
            run();
        }
    }

    @Override
    public void render() {
        // draw a gray rectangle and if it is hovered draw a light gray rectangle instead
        // the origin of the button is at the center of the button

        if (isHovered) {
            if (isPressed) {
                UiUtil.scaleButtonRectangle(size, position, scaleFactor, hlpColor, buttonRectangle);
            } else {
                DrawRectangleRoundedLines(buttonRectangle, 0.3f, 5, 5, hlColor);
            }
        } else {
            DrawRectangleRoundedLines(buttonRectangle, 0.3f, 5, 5, color);
        }

// draw the bg color of the button
        if (isPressed) {
            UiUtil.scaleButtonRectangle(size, position, scaleFactor, hlpColor, buttonRectangle);
        } else {
            DrawRectangleRounded(buttonRectangle, 0.3f, 5, bgColor);
        }

        // draw the text of the button in the center of it
        // also measure the size of the text
        // check if pressed or hoverd and draw the text in the right color
        DrawText(text, (int) (position.x - MeasureText(text, (int) textSize) / 2), (int) position.y - (int) (textSize / 2),
                (int) textSize, isPressed ? hlpColor : isHovered ? hlColor : color);
    }
}
