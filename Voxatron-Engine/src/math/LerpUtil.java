package math;

public class LerpUtil {

    public static float slerp(float a, float b, float t) {
        return a + (b - a) * t;
    }
}
