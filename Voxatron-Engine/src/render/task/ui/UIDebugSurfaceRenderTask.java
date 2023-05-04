package render.task.ui;

import com.raylib.Jaylib;
import render.task.RenderTask;

import static com.raylib.Jaylib.YELLOW;
import static com.raylib.Raylib.*;


public class UIDebugSurfaceRenderTask extends RenderTask {

    public Vector2 pos1;
    public Vector2 pos2;

    public void UIRoundBoxRenderTask(Vector2 pos1, Vector2 pos2){
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    @Override
    public void render() {
        // TODO: FIX
        DrawRectangle((int)pos1.x(), (int)pos1.y(), (int)pos2.x(), (int)pos2.y(), YELLOW);
    }
}