package render.ui.item;

import math.Vector2;
import render.task.RenderTask;
import util.UUIDUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class UIItem {

    public String uuid;

    public List<RenderTask> renderables = new ArrayList<>();

    public void addRenderable(RenderTask renderable) {
        renderables.add(renderable);
    }

    public void removeRenderable(RenderTask renderable) {
        renderables.remove(renderable);
    }

    public UIItem() {
        uuid = UUIDUtil.generateUUID();
    }

    public abstract void update();

    public abstract void loadUIValues(Vector2 position, Vector2 size);
}
