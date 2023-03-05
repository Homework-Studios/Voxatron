package level.types;

import actor.Actor;
import level.Level;
import level.LevelType;
import render.Renderer;
import render.task.RenderTask;

import java.util.List;

public class DefaultLevel extends Level {

    public DefaultLevel() {
        super(LevelType.DEFAULT);
    }

    @Override
    public void update() {
        for (Actor actor : actors) {
            actor.update();
        }
    }

    @Override
    public void render() {
        List<RenderTask> tasks = new java.util.ArrayList<>(List.of());

        for (Actor actor : actors) {
            tasks.addAll(actor.tasks);
        }

        for (RenderTask task : tasks) {
            Renderer.instance.execute(task);
        }
    }
}
