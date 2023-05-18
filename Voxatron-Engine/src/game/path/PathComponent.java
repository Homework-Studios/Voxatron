package game.path;

import com.raylib.Raylib;
import math.LerpUtil;

public class PathComponent {

    public Raylib.Vector3 start;
    public Raylib.Vector3 end;

    public PathComponent(Raylib.Vector3 start, Raylib.Vector3 end) {
        this.start = start;
        this.end = end;
    }

    public Raylib.Vector3 getStart() {
        return start;
    }

    public Raylib.Vector3 getEnd() {
        return end;
    }

    public Raylib.Vector3 getLerp(float i) {
        return LerpUtil.lerp(start, end, i);
    }
}
