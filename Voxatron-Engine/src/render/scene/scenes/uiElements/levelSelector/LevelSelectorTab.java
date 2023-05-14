package render.scene.scenes.uiElements.levelSelector;

import com.raylib.Raylib;

public class LevelSelectorTab {

    public String name;
    public Raylib.Color color;
    public boolean locked;

    public LevelSelectorTab(String name, Raylib.Color color, boolean locked) {
        this.name = name;
        this.color = color;
        this.locked = locked;
    }
}
