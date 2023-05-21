package game.path;

import com.raylib.Raylib;
import math.Vector3;

public class Path {

    public PathComponent[] components;

    public Path(PathComponent[] components) {
        this.components = components;
    }

    public PathComponent[] getComponents() {
        return components;
    }

    public PathComponent getComponent(int i) {
        return components[i];
    }

    public PathComponent getComponent(float i) {
        return components[(int) i];
    }

    public Vector3 getLerp(float i) {
        // get the lerp over all the components
        // i is the lerp over the components 0 is the start 1 is the end

        // map i
        i *= components.length;

        return components[(int) i].getLerp(i % 1);
    }
}
