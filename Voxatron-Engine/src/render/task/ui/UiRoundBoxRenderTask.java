package render.task.ui;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import math.Vector2;
import render.task.RenderTask;

import static com.raylib.Jaylib.RED;
import static com.raylib.Raylib.*;

public class UiRoundBoxRenderTask extends RenderTask {

    public Vector2 sPosition;
    public Vector2 ePosition;
    public float v;
    public int i;
    public Raylib.Color color;

    public UiRoundBoxRenderTask(Vector2 sPosition, Vector2 ePosition, float v, int i, Raylib.Color color) {
        this.sPosition = sPosition;
        this.ePosition = ePosition;
        this.v = v;
        this.i = i;
        this.color = color;
    }

    @Override
    public void render() {
        Jaylib.Rectangle rectangle = new Jaylib.Rectangle(sPosition.x, sPosition.y, ePosition.x, ePosition.y);
        DrawRectangleRounded(rectangle, v, i, color);
    }
}