package render.ui;

import com.raylib.Jaylib;
import debug.DebugDraw;
import math.Vector2;
import render.task.RenderTask;
import render.task.ui.UIDebugSurfaceRenderTask;
import render.ui.item.UIItem;

import java.util.ArrayList;
import java.util.List;

public class UIScreen {

    public Vector2 position;
    public Vector2 size;

    public boolean debugSize = true;
    public UIDebugSurfaceRenderTask dsrt = new UIDebugSurfaceRenderTask();

    public List<UIItem> items = new ArrayList<>();

    public UIScreen(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
    }

    public void addItem(UIItem item) {
        item.loadUIValues(position, size);
        item.loadScreen(this);
        items.add(item);
    }

    public void removeItem(UIItem item) {
        items.remove(item);
    }

    public void update() {
        for (UIItem item : items) {
            item.update();
        }
    }

    public void render() {
        for (UIItem item : items) {
            if (!item.hidden)
                for (RenderTask task : item.tasks) {
                    task.render();
                }
        }

        if(debugSize){
            dsrt.pos1 = position;
            dsrt.pos2 = position.add(size);
            dsrt.render();
        }
    }
}
