package game.tower;

import com.raylib.Jaylib;
import com.raylib.Raylib;

public interface TowerInterface {
    boolean placeTower();
    Raylib.Vector3 getDropVector();
}
