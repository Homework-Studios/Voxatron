package entities;

import com.raylib.java.raymath.Vector3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Entity {
    private final String name;
    private Vector3 location;
    private boolean clickable = false;
    private final UUID uuid = UUID.randomUUID();

    static HashMap<UUID,Entity> entityMap = new HashMap<>();
    public Entity(String name, Vector3 location) {
        this.location = location;
        this.name = name;

        //registers entity
        entityMap.put(uuid,this);
    }

    public void remove(){
        entityMap.remove(uuid);
    }
    public Vector3 getLocation() {
        return location;
    }

    public void setLocation(Vector3 location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }
}
