package render.task.ui;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import math.Vector2;
import render.task.RenderTask;

import static com.raylib.Jaylib.RED;
import static com.raylib.Raylib.*;

public class UiRoundBoxRenderTask extends RenderTask {

    public Vector2 position;
    public Vector2 size;
    public float v;
    public int i;
    public Raylib.Color color;

    public boolean lines = false;

    public UiRoundBoxRenderTask(Vector2 position, Vector2 size, float v, int i, Raylib.Color color) {
        this.position = position;
        this.size = size;
        this.v = v;
        this.i = i;
        this.color = color;
    }

    @Override
    public void render() {
        Vector2 halfSize = size.divide(new Vector2(2, 2));

        // Jaylib.Rectangle rectangle = new Jaylib.Rectangle(position.x - halfSize.x, position.y - halfSize.y, position.x + halfSize.x, position.y + halfSize.y);
        Jaylib.Rectangle rectangle = new Jaylib.Rectangle(position.x - halfSize.x, position.y - halfSize.y, size.x, size.y);

        if (lines)
            DrawRectangleRoundedLines(rectangle, v, i, 5, color);
        else
            DrawRectangleRounded(rectangle, v, i, color);
    }
}