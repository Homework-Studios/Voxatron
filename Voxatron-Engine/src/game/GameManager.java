package game;

import game.tower.Tower;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    public static GameManager instance;
    private int round = 0;
    private boolean roundEnded = true;
    private boolean gameShouldEnd = false;
    private int energy = 500;
    private int lives = 100;
    private List<Tower> towers = new ArrayList<>();
    public GameManager() {
        instance = this;
    }

    public static GameManager getInstance() {
        return instance;
    }

    public void setGameShouldEnd(boolean gameShouldEnd) {
        this.gameShouldEnd = gameShouldEnd;
    }

    public void setRoundEnded(boolean roundEnded) {
        this.roundEnded = roundEnded;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public void setTowers(List<Tower> towers) {
        this.towers = towers;
    }

    public void addTower(Tower tower) {
        towers.add(tower);
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void nextRound() {
        round++;
    }

    public void addMoney(int money) {
        this.energy += money;
    }

    public void removeMoney(int money) {
        this.energy -= money;
    }

    public boolean buy(int cost) {
        if (energy >= cost) {
            removeMoney(cost);
            return true;
        }
        return false;
    }

    public void start() {
        while (!gameShouldEnd) {
            if (roundEnded) {
                nextRound();
                roundEnded = false;
            }
        }
    }

}
