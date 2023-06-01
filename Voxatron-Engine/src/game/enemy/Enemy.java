package game.enemy;

import engine.math.Vector3;
import engine.render.scene.Element;
import game.GameManager;

public abstract class Enemy extends Element {

    public final int weight;
    public int health;
    public int maxHealth;

    public float walkSpeed;
    public float positionOnPath = 0;

    public Vector3 position = new Vector3();
    public boolean isAlive = true;

    public Enemy(int health, float walkSpeed, int weight) {
        maxHealth = health;
        this.health = health;
        this.walkSpeed = walkSpeed;
        this.weight = weight;

    }

    public void stepOnPath() {
        positionOnPath += (walkSpeed * 0.05f * GameManager.gameSpeed);

        position = GameManager.instance.pathManager.getTravel(positionOnPath);
        if (positionOnPath >= GameManager.instance.pathManager.totalDistance) {
            GameManager.instance.removeLives(health);
            GameManager.instance.killEnemy(this);
        }
    }

    public void damage(int damage) {
        health -= damage;
        if (health <= 0) {
            isAlive = false;
            kill();
            GameManager.instance.addEnergy(maxHealth);
            GameManager.instance.killEnemy(this);
        }
    }

    public void gameTick() {

    }

    public abstract void kill();

    @Override
    public void update() {
        stepOnPath();
    }

    public void spawnEnemy(Enemy enemy) {
        enemy.positionOnPath = positionOnPath;
        GameManager.instance.addEnemy(enemy);
    }
}
