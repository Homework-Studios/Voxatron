package spiel.turm;

public abstract class Fabrik extends Turm {

    public int gibEnergieProSchlag = 20;
    public int momentanenSchlag = 0;

    public float momentaneEnergie = 0;
    public int maximaleEnergie = 1000;

    public Fabrik(Type typ) {
        super(typ);
    }

    public float provideEnergy(int menge) {
        EnergieVerbraucher[] verbraucher = spielManager.getNaechstenEnergieVerbraucher(position, reichweite);

        spielManager.addEnergy(menge);

        if (verbraucher.length > 5) {
            verbraucher = new EnergieVerbraucher[]{verbraucher[0], verbraucher[1], verbraucher[2], verbraucher[3], verbraucher[4]};
        }
        int energyPerConsumer = menge / (verbraucher.length == 0 ? 1 : verbraucher.length);

        float leftoverEnergy = 0;
        for (EnergieVerbraucher energieVerbraucher : verbraucher) {
            leftoverEnergy += energieVerbraucher.addEnergy(energyPerConsumer);
        }

        return leftoverEnergy;
    }

    // add energy to the factory as long as there is space
    public void addEnergie(float menge) {
        momentaneEnergie += menge;
        if (momentaneEnergie > maximaleEnergie) {
            momentaneEnergie = maximaleEnergie;
        }
    }
}
