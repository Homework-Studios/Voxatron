package spiel.weg;

import engine.math.LerpUtil;
import engine.math.Vector3;

public class WegTeil {

    public Vector3 start;
    public Vector3 ende;
    public float laenge;

    public WegTeil(Vector3 start, Vector3 ende) {
        this.start = start;
        this.ende = ende;
        this.laenge = start.distance(ende);
    }

    public Vector3 getStart() {
        return start;
    }

    public Vector3 getEnde() {
        return ende;
    }

    public Vector3 getLaufen(float distance) {
        float t = distance / laenge;
        return LerpUtil.lerp(start, ende, t);
    }
}
