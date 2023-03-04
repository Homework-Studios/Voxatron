package actor;

import render.task.RenderTask;
import util.UUIDUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class Actor {

    public String uuid;

    public List<RenderTask> renderables = new ArrayList<>();

    public void addRenderable(RenderTask renderable) {
        renderables.add(renderable);
    }

    public void removeRenderable(RenderTask renderable) {
        renderables.remove(renderable);
    }

    public Actor() {
        uuid = UUIDUtil.generateUUID();
    }

    public abstract void update();
}
