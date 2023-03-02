package level;

import actor.Actor;

import java.util.List;

public abstract class Level {

    public LevelType levelType;

    public List<Actor> actors = List.of();

    public Level(LevelType type) {
        this.levelType = type;
    }

    // Add an actor to the level
    public void addActor(Actor actor) {
        actors.add(actor);
    }

    // Remove an actor from the level
    public void removeActor(Actor actor) {
        actors.remove(actor);
    }

    public abstract void update();
}
