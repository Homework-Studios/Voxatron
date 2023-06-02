package engine.render.scene.elements;

import com.raylib.Raylib;
import engine.math.Vector2;
import engine.render.scene.Element;
import engine.util.ColorPalette;
import engine.util.UiUtils;

import static com.raylib.Raylib.*;

public abstract class ToggleElement extends Element implements Runnable {

    public final ColorPalette cp;
    public Vector2 position;
    public Vector2 size;
    public String text;
    public float textSize;
    public boolean isHovered;
    public boolean toggle;
    public float scaleFactor = .95f;
    public boolean isPressed;
    private Raylib.Rectangle toggleRectangle = new Raylib.Rectangle();


    public ToggleElement(Vector2 position, Vector2 size, String text, float textSize, boolean defaultValue, ColorPalette colorPalette) {
        this.position = position;
        this.size = size;
        this.text = text;
        this.textSize = textSize;
        this.toggle = defaultValue;
        this.cp = colorPalette;
    }

    public ToggleElement(Vector2 position, Vector2 size, String text, float textSize, boolean defaultValue, ColorPalette.ColorPalettes colorPalette) {
        this.position = position;
        this.size = size;
        this.text = text;
        this.textSize = textSize;
        this.toggle = defaultValue;
        this.cp = colorPalette.getColorPalette();
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
                UiUtils.scaleButtonRectangle(size, position, scaleFactor, toggle ? cp.on : cp.off, toggleRectangle);
            } else {
                DrawRectangleRoundedLines(toggleRectangle, 0.3f, 5, 5, cp.hover);
            }
        } else {
            DrawRectangleRoundedLines(toggleRectangle, 0.3f, 5, 5, toggle ? cp.on : cp.off);
        }

        // draw the text
        Raylib.DrawText(text, (int) (position.x - Raylib.MeasureText(text, (int) textSize) / 2), (int) (position.y - textSize / 2), (int) textSize, cp.text);
    }
}
