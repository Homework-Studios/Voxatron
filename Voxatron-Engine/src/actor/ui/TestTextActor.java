package actor.ui;

import actor.Actor;
import render.task.ui.UiTextRenderTask;
import math.Vector2;

import static com.raylib.Jaylib.WHITE;

public class TestTextActor extends Actor {

    UiTextRenderTask textRenderer;

    public TestTextActor() {
        super();

        textRenderer = new UiTextRenderTask("Hello World", new Vector2(10, 10), 1, 0, WHITE);

        addRenderable(textRenderer);
    }

    @Override
    public void update() {
        textRenderer.position.x += 1;
    }
}
