package game;

import game.tower.Tower;

public class GameManager {
    public static GameManager instance;
    private int round = 0;
    private int money = 0;
    private int lives = 0;
    private Tower[] towers = new Tower[100];

    public GameManager() {
        instance = this;
    }

    public Tower[] getTowers() {
        return towers;
    }

    public void setTowers(Tower[] towers) {
        this.towers = towers;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }


}
