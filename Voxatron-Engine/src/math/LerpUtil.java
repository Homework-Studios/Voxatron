package math;

public class LerpUtil {

    public static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
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
