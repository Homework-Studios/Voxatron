package game.tower;

import com.raylib.Raylib;

public abstract class Tower {
    private String name;
    private Raylib.Color color;

    private int damage;
    private int range;
    private int fireRate;
    private int cost;

    public String getName() {
        return name;
    }

    public Tower setName(String name) {
        this.name = name;
        return this;
    }

    public Raylib.Color getColor() {
        return color;
    }

    public Tower setColor(Raylib.Color color) {
        this.color = color;
        return this;
    }

    public int getDamage() {
        return damage;
    }

    public Tower setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    public int getRange() {
        return range;
    }

    public Tower setRange(int range) {
        this.range = range;
        return this;
    }

    public int getFireRate() {
        return fireRate;
    }

    public Tower setFireRate(int fireRate) {
        this.fireRate = fireRate;
        return this;
    }

    public int getCost() {
        return cost;
    }

    public Tower setCost(int cost) {
        this.cost = cost;
        return this;
    }
}
