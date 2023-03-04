package render.task.ui;


import static com.raylib.Jaylib.*;

import math.Vector2;
import render.task.RenderTask;

import static com.raylib.Raylib.Color;
import static com.raylib.Jaylib.WHITE;

public class UiTextRenderTask extends RenderTask {

    public String text;
    public Vector2 position;
    public float scale;
    public float rotation;
    public Color color;
    
    public UiTextRenderTask(String text, Vector2 position, float scale, float rotation, Color color) {
        this.text = text;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        this.color = color;
    }

    @Override
    public void render() {
        DrawText(text, (int)position.x, (int)position.y, (int)(20 * scale), WHITE);
    }
}
