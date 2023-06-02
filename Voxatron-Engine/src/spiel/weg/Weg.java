package spiel.weg;

import engine.math.Vector3;

public class Weg {

    private final WegTeil[] wegTeile;

    public Weg(WegTeil[] wegTeile) {
        this.wegTeile = wegTeile;
    }

    public WegTeil[] getWegTeile() {
        return wegTeile;
    }

    public WegTeil getWeg(int i) {
        return wegTeile[i];
    }

    public WegTeil getWeg(float i) {
        return wegTeile[(int) i];
    }

    public Vector3 getLaufen(float distance) {
        int componentIndex = 0;
        float traveledDistance = distance;
        while (componentIndex < wegTeile.length - 1 && traveledDistance > wegTeile[componentIndex].laenge) {
            traveledDistance -= wegTeile[componentIndex].laenge;
            componentIndex++;
        }
        return wegTeile[componentIndex].getLaufen(traveledDistance);
    }

}