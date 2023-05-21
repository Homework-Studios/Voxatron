package game;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.path.Path;
import game.path.PathComponent;
import math.Vector3;

public class PathManager {

    public static PathManager instance;

    public Path currentPath;

    public PathManager() {
        instance = this;
    }

    public void genPath(Vector3 start, Vector3 end, Vector3[] nodes){
        PathComponent[] components = new PathComponent[nodes.length + 1];
        components[0] = new PathComponent(start, nodes[0]);
        for (int i = 0; i < nodes.length - 1; i++) {
            components[i + 1] = new PathComponent(nodes[i], nodes[i + 1]);
        }
        components[nodes.length] = new PathComponent(nodes[nodes.length - 1], end);
        currentPath = new Path(components);
    }

    public Vector3 getLerp(float i) {
        return currentPath.getLerp(i);
    }

    public void debugDraw() {
        // start is green sphere
        // end is red sphere
        // node is blue sphere
        // line is green

        Vector3 offset = new Vector3(0, 2, 0);

        Vector3 start = currentPath.getComponent(0).getStart();
        Vector3 end = currentPath.getComponent(currentPath.getComponents().length - 1).getEnd();

        // add offset
        Vector3 offsetStart = new Vector3(start).add(offset);
        Vector3 offsetEnd = new Vector3(end).add(offset);

        Raylib.DrawSphere(offsetStart.toRaylibVector3(), 2, Jaylib.GREEN);
        Raylib.DrawSphere(offsetEnd.toRaylibVector3(), 2, Jaylib.RED);

        for (int i = 0; i < currentPath.getComponents().length; i++) {

            if (i == currentPath.getComponents().length - 1) {
                break;
            }

            Raylib.DrawSphere(new Vector3(currentPath.getComponent(i).getEnd()).add(offset).toRaylibVector3(), 2, Jaylib.BLUE);
        }

        for (int i = 0; i < currentPath.getComponents().length; i++) {
            Raylib.DrawLine3D(new Vector3(currentPath.getComponent(i).getStart()).add(offset).toRaylibVector3(), new Vector3(currentPath.getComponent(i).getEnd()).add(offset).toRaylibVector3(), Jaylib.GREEN);
        }

    }
}
