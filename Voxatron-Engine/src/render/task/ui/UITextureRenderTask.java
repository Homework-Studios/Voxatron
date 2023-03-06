package render.task.ui;

import com.raylib.Raylib;
import math.Vector2;
import render.task.RenderTask;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.DrawTexture;

public class UITextureRenderTask extends RenderTask {

    public Vector2 position;
    public Raylib.Texture texture;

    public UITextureRenderTask(Vector2 position, Raylib.Texture texture) {
        this.position = position;
        this.texture = texture;
    }

    @Override
    public void render() {
        DrawTexture(texture, (int) position.x, (int) position.y, WHITE);
    }
}
