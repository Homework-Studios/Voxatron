package game.path;

import engine.math.Vector3;

public class Path {

    private final PathComponent[] components;

    public Path(PathComponent[] components) {
        this.components = components;
    }

    public PathComponent[] getComponents() {
        return components;
    }

    public PathComponent getComponent(int i) {
        return components[i];
    }

    public PathComponent getComponent(float i) {
        return components[(int) i];
    }

    public Vector3 getTravel(float distance) {
        int componentIndex = 0;
        float traveledDistance = distance;
        while (componentIndex < components.length - 1 && traveledDistance > components[componentIndex].length) {
            traveledDistance -= components[componentIndex].length;
            componentIndex++;
        }
        return components[componentIndex].getTravel(traveledDistance);
    }

}