package game.path;

import com.raylib.Raylib;
import math.LerpUtil;
import math.Vector3;

public class PathComponent {

    public Vector3 start;
    public Vector3 end;

    public PathComponent(Vector3 start, Vector3 end) {
        this.start = start;
        this.end = end;
    }

    public Vector3 getStart() {
        return start;
    }

    public Vector3 getEnd() {
        return end;
    }

    public Vector3 getLerp(float i) {
        return LerpUtil.lerp(start, end, i);
    }
}
