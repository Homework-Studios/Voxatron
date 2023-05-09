package render.scene.scenes.uiElements;

import math.Vector2;
import render.scene.Element;

import static com.raylib.Jaylib.GRAY;
import static com.raylib.Jaylib.LIGHTGRAY;
import static com.raylib.Raylib.*;

public class ButtonElement extends Element {

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

    private Runnable onClick;

    public ButtonElement(Vector2 position, Vector2 size, String text, float textSize, Color color, Color bgColor, Color hlColor, Color hlpColor, Runnable onClick) {
        this.position = position;
        this.size = size;
        this.text = text;
        this.textSize = textSize;
        this.color = color;
        this.bgColor = bgColor;
        this.hlColor = hlColor;
        this.hlpColor = hlpColor;
        this.onClick = onClick;
    }

    @Override
    public void update() {

        buttonRectangle = new Rectangle().x(position.x - size.x / 2).y(position.y - size.y / 2).width(size.x).height(size.y);

        // check if mouse is hovering over button
        isHovered = CheckCollisionPointRec(GetMousePosition(), buttonRectangle);

        // check if mouse button left is down
        if (isHovered && IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
            isPressed = true;
        } else {
            isPressed = false;
        }

        // check if mouse button left is up
        if (isHovered && IsMouseButtonReleased(MOUSE_BUTTON_LEFT)) {
            onClick.run();
        }
    }

    @Override
    public void render() {
        // draw a gray rectangle and if it is hovered draw a light gray rectangle instead
        // the origin of the button is at the center of the button
        if (isHovered) {
            if(isPressed){
                DrawRectangleRoundedLines(buttonRectangle, 0.3f, 5, 5, hlpColor);
            }else{
                DrawRectangleRoundedLines(buttonRectangle, 0.3f, 5, 5, hlColor);
            }
        } else {
            DrawRectangleRoundedLines(buttonRectangle, 0.3f, 5, 5, color);
        }

        // draw the bg color of the button
        DrawRectangleRounded(buttonRectangle, 0.3f, 5, bgColor);

        // draw the text of the button in the center of it
        // also measure the size of the text
        // check if pressed or hoverd and draw the text in the right color
        DrawText(text, (int)( position.x - MeasureText(text, (int)textSize) / 2), (int) position.y - (int)(textSize/2),
                (int)textSize, isPressed ? hlpColor : isHovered ? hlColor : color);
    }
}
