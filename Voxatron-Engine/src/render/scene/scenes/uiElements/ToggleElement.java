package render.scene.scenes.uiElements;

import com.raylib.Raylib;
import math.Vector2;
import render.scene.Element;

public class ToggleElement extends Element {

    public Vector2 position;
    public Vector2 size;
    public String text;
    public float textSize;
    public Raylib.Color color;
    public Raylib.Color hlColor;
    public Raylib.Color onColor;
    public Raylib.Color offColor;

    public boolean isHovered;
    public boolean toggle;

    private Raylib.Rectangle toggleRectangle = new Raylib.Rectangle();

    private Runnable onToggle = () -> {};

    public ToggleElement(Vector2 position, Vector2 size, String text, float textSize, Raylib.Color color, Raylib.Color hlColor, Raylib.Color onColor, Raylib.Color offColor, Runnable onToggle) {
        this.position = position;
        this.size = size;
        this.text = text;
        this.textSize = textSize;
        this.color = color;
        this.hlColor = hlColor;
        this.onColor = onColor;
        this.offColor = offColor;
        this.onToggle = onToggle;
    }

    @Override
    public void update() {
        // create the toggle rectangle
        toggleRectangle = new Raylib.Rectangle().x(position.x - size.x / 2).y(position.y - size.y / 2).width(size.x).height(size.y);

        // check if mouse is hovering over button
        isHovered = Raylib.CheckCollisionPointRec(Raylib.GetMousePosition(), toggleRectangle);

        // check if mouse button left is up
        if (isHovered && Raylib.IsMouseButtonPressed(Raylib.MOUSE_BUTTON_LEFT)) {
            toggle = !toggle;
            onToggle.run();
        }
    }

    @Override
    public void render() {
        // draw the button
        Raylib.DrawRectangleRounded(toggleRectangle, 0.3f, 5, toggle ? onColor : offColor);

        // draw the outline
        Raylib.DrawRectangleRoundedLines(toggleRectangle, 0.3f, 0, 5, isHovered ? hlColor : color);

        // draw the text
        Raylib.DrawText(text, (int) (position.x - Raylib.MeasureText(text, (int) textSize) / 2), (int) (position.y - textSize / 2), (int) textSize, color);
    }
}
