package level.types;

import level.Level;
import level.LevelType;

public class DefaultLevel extends Level {

    public DefaultLevel() {
        super(LevelType.DEFAULT);
    }

    @Override
    public void update() {
        System.out.println("Default level update");
    }
}
