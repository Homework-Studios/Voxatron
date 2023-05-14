package render.scene.scenes.uiElements.levelSelector;

import com.raylib.Raylib;

public class LevelSelectorTab {

    public String name;
    public Raylib.Color color;
    public Raylib.Texture thumbnail;
    public boolean locked;

    public LevelSelectorTab(String name, Raylib.Color color, Raylib.Texture thumbnail, boolean locked) {
        this.name = name;
        this.color = color;
        this.thumbnail = thumbnail;
        this.locked = locked;
    }
}
