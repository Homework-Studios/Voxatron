package util;

import com.raylib.Raylib;

public class VectorUtils {

    public static Raylib.Vector3 copy(Raylib.Vector3 vector) {
        return new Raylib.Vector3().x(vector.x()).y(vector.y()).z(vector.z());
    }
}
