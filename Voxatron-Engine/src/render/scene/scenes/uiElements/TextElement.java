package render.scene.scenes.uiElements;

import com.raylib.Raylib;
import math.Vector2;
import render.scene.Element;

public class TextElement extends Element {

    public Vector2 position;
    public Vector2 size;
    public String text;
    public float textSize;
    public Raylib.Color color;

    private Raylib.Rectangle textRectangle = new Raylib.Rectangle();
    private int textWidth = 0;
    private int textHeight = 0;

    public TextElement(Vector2 position, Vector2 size, String text, float textSize, Raylib.Color color) {
        this.position = position;
        this.size = size;
        this.text = text;
        this.textSize = textSize;
        this.color = color;
    }

    @Override
    public void update() {
        textRectangle = new Raylib.Rectangle().x(position.x).y(position.y).width(size.x).height(size.y);
        textWidth = Raylib.MeasureText(text, (int) textSize);
        textHeight = (int) textSize;
    }

    @Override
    public void render() {
        // draw the text at the center of the rectangle
        int centerX = (int) (textRectangle.x() + textRectangle.width() / 2);
        int centerY = (int) (textRectangle.y() + textRectangle.height() / 2);
        Raylib.DrawText(text, centerX - (textWidth / 2), centerY - (textHeight / 2), (int) textSize, color);
    }
}
