package game;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.path.Path;
import game.path.PathComponent;

public class PathManager {

    public static PathManager instance;

    public Path currentPath;

    public PathManager() {
        instance = this;
    }

    public void genPath(Raylib.Vector3 start, Raylib.Vector3 end, Raylib.Vector3[] nodes){
        PathComponent[] components = new PathComponent[nodes.length + 1];
        components[0] = new PathComponent(start, nodes[0]);
        for (int i = 0; i < nodes.length - 1; i++) {
            components[i + 1] = new PathComponent(nodes[i], nodes[i + 1]);
        }
        components[nodes.length] = new PathComponent(nodes[nodes.length - 1], end);
        currentPath = new Path(components);
    }

    public Raylib.Vector3 getLerp(float i) {
        return currentPath.getLerp(i);
    }

    public void debugDraw() {
        // start is green sphere
        // end is red sphere
        // node is blue sphere
        // line is green

        Raylib.Vector3 offset = new Raylib.Vector3().y(2);

        Raylib.Vector3 start = currentPath.getComponent(0).getStart();
        Raylib.Vector3 end = currentPath.getComponent(currentPath.getComponents().length - 1).getEnd();

        // add offset
        start = Raylib.Vector3Add(start, offset);
        end = Raylib.Vector3Add(end, offset);

        Raylib.DrawSphere(start, 2, Jaylib.GREEN);
        Raylib.DrawSphere(end, 2, Jaylib.RED);

        for (int i = 0; i < currentPath.getComponents().length; i++) {

            if (i == currentPath.getComponents().length - 1) {
                break;
            }

            Raylib.DrawSphere(Raylib.Vector3Add(currentPath.getComponent(i).getEnd(), offset), 2, Jaylib.BLUE);
        }

        for (int i = 0; i < currentPath.getComponents().length; i++) {
            Raylib.DrawLine3D(Raylib.Vector3Add(currentPath.getComponent(i).getStart(), offset), Raylib.Vector3Add(currentPath.getComponent(i).getEnd(), offset), Jaylib.GREEN);
        }

    }
}
