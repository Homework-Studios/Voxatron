package spiel;

import engine.math.Vector3;
import spiel.weg.Weg;
import spiel.weg.WegTeil;

public class WegManager {

    public static WegManager instance;

    public float totaleDistanz;
    public Weg momentanerWeg;

    public WegManager() {
        instance = this;
    }

    public void generiereWeg(Vector3 start, Vector3 ende, Vector3[] punkte) {
        System.out.println("generating Path");
        WegTeil[] WegTeile = new WegTeil[punkte.length + 1];
        WegTeile[0] = new WegTeil(start, punkte[0]);
        for (int i = 0; i < punkte.length - 1; i++) {
            WegTeil component = new WegTeil(punkte[i], punkte[i + 1]);
            WegTeile[i + 1] = component;
            totaleDistanz += component.laenge;
        }
        WegTeile[punkte.length] = new WegTeil(punkte[punkte.length - 1], ende);
        momentanerWeg = new Weg(WegTeile);
    }

    public Vector3 getLaufen(float distance) {
        return momentanerWeg.getLaufen(distance);
    }
}
