package game.path;

import engine.math.LerpUtil;
import engine.math.Vector3;

public class PathComponent {

    public Vector3 start;
    public Vector3 end;
    public float length;

    public PathComponent(Vector3 start, Vector3 end) {
        this.start = start;
        this.end = end;
        this.length = start.distance(end);
    }

    public Vector3 getStart() {
        return start;
    }

    public Vector3 getEnd() {
        return end;
    }

    public Vector3 getTravel(float distance) {
        float t = distance / length;
        return LerpUtil.lerp(start, end, t);
    }
}
