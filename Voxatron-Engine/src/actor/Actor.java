package actor;

import render.task.RenderTask;
import util.UUIDUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class Actor {

    public String uuid;

    public List<RenderTask> tasks = new ArrayList<>();

    public void addTask(RenderTask task) {
        tasks.add(task);
    }

    public void removeTask(RenderTask task) {
        tasks.remove(task);
    }

    public Actor() {
        uuid = UUIDUtil.generateUUID();
    }

    public abstract void update();
}
