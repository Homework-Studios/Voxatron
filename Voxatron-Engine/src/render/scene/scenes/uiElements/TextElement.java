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

    public TextElement(Vector2 position, Vector2 size, String text, float textSize, Raylib.Color color) {
        this.position = position;
        this.size = size;
        this.text = text;
        this.textSize = textSize;
        this.color = color;
    }

    @Override
    public void update() {
        // Do nothing because text elements don't need to be updated
    }

    @Override
    public void render() {
        // There is a imaginary rectangle around the text, the text is centered in the rectangle
        Raylib.DrawText(text, (int) (position.x - size.x / 2), (int) (position.y - size.y / 2), (int) textSize, color);
    }
}
