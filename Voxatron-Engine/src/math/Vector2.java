package math;

import com.raylib.Raylib;

public class Vector2 extends Raylib.Vector2 {

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


    public Vector2(String string) {
        String[] split = string.split(",");
        this.x = Float.parseFloat(split[0]);
        this.y = Float.parseFloat(split[1]);
    }

    public Vector2(Raylib.Vector2 vector2) {
        this.x = vector2.x();
        this.y = vector2.y();
    }

    public Vector2 normalize() {
        float len = (float) Math.sqrt(x * x + y * y);
        if (len != 0) {
            return new Vector2(x / len, y / len);
        } else {
            return new Vector2(0, 0);
        }
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 add(float other) {
        return new Vector2(x + other, y + other);
    }

    public Vector2 add(int x, int y) {
        return new Vector2(this.x + x, this.y + y);
    }

    public Vector2 add(float x, float y) {
        return new Vector2(this.x + x, this.y + y);
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    public Vector2 subtract(int other) {
        return new Vector2(x - other, y - other);
    }

    public Vector2 subtract(float other) {
        return new Vector2(x - other, y - other);
    }

    public Vector2 multiply(Vector2 other) {
        return new Vector2(x * other.x, y * other.y);
    }

    public Vector2 multiply(int other) {
        return new Vector2(x * other, y * other);
    }

    public Vector2 multiply(float other) {
        return new Vector2(x * other, y * other);
    }

    public Vector2 divide(Vector2 other) {
        return new Vector2(x / other.x, y / other.y);
    }

    public Vector2 divide(int other) {
        return new Vector2(x / other, y / other);
    }

    public Vector2 divide(float other) {
        return new Vector2(x / other, y / other);
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
