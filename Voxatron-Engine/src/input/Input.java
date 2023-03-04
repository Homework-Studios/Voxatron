package input;

import com.raylib.Raylib;
import input.map.Mapping;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;
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
    }

    public int findMapping(Mapping mapping) {
        if(mappings.containsKey(mapping)) {
            return mappings.get(mapping);
        }

        return -1;
    }

    public void remap(Mapping mapping, int key) {
        mappings.put(mapping, key);
    }

    public boolean isKeyPressed(Mapping mapping) {
        int key = findMapping(mapping);

        if(key == -1) return false;

        return Raylib.IsKeyPressed(key);
    }
}