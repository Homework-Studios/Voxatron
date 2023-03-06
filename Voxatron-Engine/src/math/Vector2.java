package math;

public class Vector2 {

    public float x;
    public float y;

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    public Vector2 multiply(Vector2 other) {
        return new Vector2(x * other.x, y * other.y);
    }

    public Vector2 multiply(float other) {
        return new Vector2(x * other, y * other);
    }
    public Vector2 divide(Vector2 other) {
        return new Vector2(x / other.x, y / other.y);
    }
}
