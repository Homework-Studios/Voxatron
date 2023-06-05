package spiel.weg;

import engine.math.LerpUtil;
import engine.math.Vector3;

public class WegTeil {

    public Vector3 start; // Startpunkt des Wegteils
    public Vector3 ende; // Endpunkt des Wegteils
    public float laenge; // Länge des Wegteils

    public WegTeil(Vector3 start, Vector3 ende) {
        this.start = start.add(0, 1, 0); // Startpunkt um 1 in der Y-Achse erhöhen
        this.ende = ende.add(0, 1, 0); // Endpunkt um 1 in der Y-Achse erhöhen
        this.laenge = start.distance(ende); // Länge des Wegteils berechnen
    }

    public Vector3 getStart() {
        return start; // Startpunkt des Wegteils zurückgeben
    }

    public Vector3 getEnde() {
        return ende; // Endpunkt des Wegteils zurückgeben
    }

    public Vector3 getLaufen(float distance) {
        float t = distance / laenge; // Verhältnis der zurückgelegten Strecke zur Gesamtlänge des Wegteils berechnen
        return LerpUtil.lerp(start, ende, t); // Lineare Interpolation zwischen Start- und Endpunkt anhand des Verhältnisses durchführen
    }
}
