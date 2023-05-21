package engine.input;

import com.raylib.Jaylib;
import engine.input.map.Mapping;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Input {
    public static Input instance;
    public Map<Mapping, Integer> mappings = new HashMap<>();

    public Input() {
        instance = this;
        loadMappings();
    }

    public void loadMappings() {
        mappings.put(Mapping.TOGGLE_SCENE, KeyEvent.VK_SPACE);
        mappings.put(Mapping.TOGGLE_DEBUG, KeyEvent.VK_P);
        mappings.put(Mapping.RELOAD_ASSETS, KeyEvent.VK_F9);
    }

    public int findMapping(Mapping mapping) {
        return mappings.getOrDefault(mapping, -1);
    }

    public void remap(Mapping mapping, int key) {
        mappings.put(mapping, key);
    }

    public boolean isKeyPressed(Mapping mapping) {
        return Jaylib.IsKeyPressed(findMapping(mapping));
    }
}
