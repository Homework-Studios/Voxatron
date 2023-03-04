package level;

import level.types.DefaultLevel;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    public List<Level> levels = new ArrayList<>();
    public Level currentLevel;

    public static LevelManager instance;

    public LevelManager() {
        instance = this;
        addLevel(new DefaultLevel());
    }

    public void addLevel(Level level) {
        levels.add(level);

        if (currentLevel == null) {
            currentLevel = level;
        }
    }

    public void removeLevel(Level level) {
        levels.remove(level);
    }

    public void setCurrentLevel(Level level) {
        currentLevel = level;
    }

    public void setCurrentLevel(LevelType levelType) {
        if (findLevel(levelType) != null) {
            currentLevel = findLevel(levelType);
        }
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public Level findLevel(LevelType levelType) {
        for (Level level : levels) {
            if (level.levelType == levelType) {
                return level;
            }
        }
        return null;
    }

    public void update() {
        currentLevel.update();
    }

    public void render() {
        currentLevel.render();
    }
}
