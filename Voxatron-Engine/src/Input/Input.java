package Input;

import Input.Map.Mapping;
import com.raylib.java.Raylib;
import com.raylib.java.core.input.Keyboard;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Input {

    Raylib raylib;

    public static Input instance;
    public Map<Mapping, Integer> mappings = new HashMap<>();

    public Input(Raylib raylib) {
        this.raylib = raylib;
        instance = this;
        loadMappings();
    }

    public void loadMappings() {
        mappings.put(Mapping.TOGGLE_SCENE, Keyboard.KEY_SPACE);
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

        return raylib.core.IsKeyPressed(key);
    }
}
