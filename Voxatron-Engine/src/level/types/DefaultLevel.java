package level.types;

import actor.Actor;
import actor.ui.TestTextActor;
import level.Level;
import level.LevelType;
import render.Renderer;
import render.task.RenderTask;

import java.util.List;

public class DefaultLevel extends Level {

    public DefaultLevel() {
        super(LevelType.DEFAULT);

        addActor(new TestTextActor());
    }

    @Override
    public void update() {
        for (Actor actor : actors) {
            actor.update();
        }
    }

    @Override
    public void render() {
        List<RenderTask> renderables = new java.util.ArrayList<>(List.of());

        for (Actor actor : actors) {
            renderables.addAll(actor.renderables);
        }

        for (RenderTask renderable : renderables) {
            Renderer.instance.execute(renderable);
        }
    }
}
