package level.types;

import actor.Actor;
import level.Level;
import level.LevelType;
import render.Renderer;

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
    }
}
