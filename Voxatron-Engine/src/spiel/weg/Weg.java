package spiel.weg;

import engine.math.Vector3;

public class Weg {

    private final WegTeil[] wegTeile;

    public Weg(WegTeil[] wegTeile) {
        // Konstruktor für die Weg-Klasse
        // Initialisiert die WegTeil-Array
        this.wegTeile = wegTeile;
    }

    public WegTeil[] getWegTeile() {
        // Gibt die WegTeil-Array zurück
        return wegTeile;
    }

    public WegTeil getWeg(int i) {
        // Gibt das WegTeil-Objekt an der i-ten Position zurück
        return wegTeile[i];
    }

    public WegTeil getWeg(float i) {
        // Gibt das WegTeil-Objekt an der i-ten Position zurück (als float)
        return wegTeile[(int) i];
    }

    public Vector3 getLaufen(float distance) {
        // Gibt die Position des Spielers zurück, der eine bestimmte Entfernung auf dem Weg zurückgelegt hat
        int componentIndex = 0;
        float traveledDistance = distance;
        while (componentIndex < wegTeile.length - 1 && traveledDistance > wegTeile[componentIndex].laenge) {
            traveledDistance -= wegTeile[componentIndex].laenge;
            componentIndex++;
        }
        return wegTeile[componentIndex].getLaufen(traveledDistance);
    }

}