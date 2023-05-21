package game.visuals;

/**
 * The AudioManager class manages the audio settings of the game.
 */
public class AudioManager {

    private float masterVolume;

    /**
     * Constructs an AudioManager object with the specified master volume.
     *
     * @param masterVolume the master volume of the game
     */
    public AudioManager(float masterVolume) {
        this.masterVolume = masterVolume;
    }

    /**
     * Returns the current master volume of the game.
     *
     * @return the master volume of the game
     */
    public float getMasterVolume() {
        return masterVolume;
    }

    /**
     * Sets the master volume of the game.
     *
     * @param masterVolume the new master volume of the game
     */
    public void setMasterVolume(float masterVolume) {
        this.masterVolume = masterVolume;
    }
}
