package math;

import com.raylib.Raylib;

public class LerpUtil {

    public static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    public static Raylib.Vector3 lerp(Raylib.Vector3 a, Raylib.Vector3 b, float t) {
        Raylib.Vector3 result = new Raylib.Vector3();
        result.x(lerp(a.x(), b.x(), t));
        result.y(lerp(a.y(), b.y(), t));
        result.z(lerp(a.z(), b.z(), t));
        return result;
    }

    public static float inverseLerp(float a, float b, float t) {
        return (t - a) / (b - a);
    }

    public static float slerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    // a lerp cubic in and out
    public static float cubic(float a, float b, float t) {
        return a + (b - a) * t * t * (3 - 2 * t);
    }
}
