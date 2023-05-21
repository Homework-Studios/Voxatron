package math;

import com.raylib.Raylib;

public class Vector3 {

    public float x;
    public float y;
    public float z;

    public Vector3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public Vector3(Raylib.Vector3 vector) {
        this.x = vector.x();
        this.y = vector.y();
        this.z = vector.z();
    }

    public Vector3 add(Vector3 vector) {
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;
        return this;
    }

    public Vector3 sub(Vector3 vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        this.z -= vector.z;
        return this;
    }

    public Vector3 mul(Vector3 vector) {
        this.x *= vector.x;
        this.y *= vector.y;
        this.z *= vector.z;
        return this;
    }

    public Vector3 div(Vector3 vector) {
        this.x /= vector.x;
        this.y /= vector.y;
        this.z /= vector.z;
        return this;
    }

    public Vector3 add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3 sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vector3 mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    public Vector3 div(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    public Vector3 mul(float a) {
        this.x *= a;
        this.y *= a;
        return this;
    }

    public Vector3 div(float a) {
        this.x /= a;
        this.y /= a;
        return this;
    }

    public Raylib.Vector3 toRaylibVector3() {
        return new Raylib.Vector3().x(this.x).y(this.y).z(this.z);
    }
}
