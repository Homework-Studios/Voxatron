package render.scene.scenes.uiElements;

import com.raylib.Raylib;
import math.Vector2;
import render.scene.Element;
import util.UiUtil;

import static com.raylib.Raylib.*;

public abstract class ToggleElement extends Element implements Runnable {

    public final Raylib.Color color;
    public final Raylib.Color hlColor;
    public final Raylib.Color onColor;
    public final Raylib.Color offColor;
    public Vector2 position;
    public Vector2 size;
    public String text;
    public float textSize;
    public boolean isHovered;
    public boolean toggle;
    public float scaleFactor = .95f;
    public boolean isPressed;

    private Raylib.Rectangle toggleRectangle = new Raylib.Rectangle();


    //TODO: Make something like color scheme e.g. ColorPattern.STANDARD or new ColorPattern(Color.WHITE, Color.BLACK, Color.GREEN, Color.RED, Color.BLUE)
    //TODO: Move constructor Elements to fit standard pattern
    public ToggleElement(Vector2 position, Vector2 size, String text, float textSize, boolean defaultValue, Raylib.Color color, Raylib.Color hlColor, Raylib.Color onColor, Raylib.Color offColor) {
        this.position = position;
        this.size = size;
        this.text = text;
        this.textSize = textSize;
        this.toggle = defaultValue;
        this.color = color;
        this.hlColor = hlColor;
        this.onColor = onColor;
        this.offColor = offColor;
    }

    @Override
    public void update() {
        // check if mouse button left is down
        isPressed = isHovered && IsMouseButtonDown(MOUSE_BUTTON_LEFT);

        // create the toggle rectangle
        toggleRectangle = new Raylib.Rectangle().x(position.x - size.x / 2).y(position.y - size.y / 2).width(size.x).height(size.y);

        // check if mouse is hovering over button
        isHovered = Raylib.CheckCollisionPointRec(Raylib.GetMousePosition(), toggleRectangle);

        // check if mouse button left is up
        if (isHovered && Raylib.IsMouseButtonPressed(Raylib.MOUSE_BUTTON_LEFT)) {
            toggle = !toggle;
            run();
        }
    }

    @Override
    public void render() {

        if (isHovered) {
            if (isPressed) {
                UiUtil.scaleButtonRectangle(size, position, scaleFactor, toggle ? onColor : offColor, toggleRectangle);
            } else {
                DrawRectangleRoundedLines(toggleRectangle, 0.3f, 5, 5, hlColor);
            }
        } else {
            DrawRectangleRoundedLines(toggleRectangle, 0.3f, 5, 5, toggle ? onColor : offColor);
        }

        // draw the text
        Raylib.DrawText(text, (int) (position.x - Raylib.MeasureText(text, (int) textSize) / 2), (int) (position.y - textSize / 2), (int) textSize, color);
    }
}
