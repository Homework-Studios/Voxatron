package game.tower;

import com.raylib.Raylib;

/**
 * The abstract class representing a tower in the game.
 */
public abstract class Tower {
    private String name;
    private Raylib.Color color;
    private boolean isUnlocked = true;
    private int damage;
    private int range;
    private int fireRate;
    private int cost;

    /**
     * Constructs a new Tower object with the specified properties.
     *
     * @param name     the name of the tower
     * @param color    the color of the tower
     * @param damage   the damage dealt by the tower
     * @param range    the range of the tower
     * @param fireRate the fire rate of the tower
     * @param cost     the cost of the tower
     */
    public Tower(String name, Raylib.Color color, int damage, int range, int fireRate, int cost) {
        this.name = name;
        this.color = color;
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.cost = cost;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }

    /**
     * Returns the name of the tower.
     *
     * @return the name of the tower
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the tower.
     *
     * @param name the new name of the tower
     * @return the updated Tower object
     */
    public Tower setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Returns the color of the tower.
     *
     * @return the color of the tower
     */
    public Raylib.Color getColor() {
        return color;
    }

    /**
     * Sets the color of the tower.
     *
     * @param color the new color of the tower
     * @return the updated Tower object
     */
    public Tower setColor(Raylib.Color color) {
        this.color = color;
        return this;
    }

    /**
     * Returns the damage of the tower.
     *
     * @return the damage of the tower
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the damage of the tower.
     *
     * @param damage the new damage of the tower
     * @return the updated Tower object
     */
    public Tower setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    /**
     * Returns the range of the tower.
     *
     * @return the range of the tower
     */
    public int getRange() {
        return range;
    }

    /**
     * Sets the range of the tower.
     *
     * @param range the new range of the tower
     * @return the updated Tower object
     */
    public Tower setRange(int range) {
        this.range = range;
        return this;
    }

    /**
     * Returns the fire rate of the tower.
     *
     * @return the fire rate of the tower
     */
    public int getFireRate() {
        return fireRate;
    }

    /**
     * Sets the fire rate of the tower.
     *
     * @param fireRate the new fire rate of the tower
     * @return the updated Tower object
     */
    public Tower setFireRate(int fireRate) {
        this.fireRate = fireRate;
        return this;
    }

    /**
     * Returns the cost of the tower.
     *
     * @return the cost of the tower
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets the cost of the tower.
     *
     * @param cost the new cost of the tower
     * @return the updated Tower object
     */
    public Tower setCost(int cost) {
        this.cost = cost;
        return this;
    }
}
