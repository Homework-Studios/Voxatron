package engine.render.scene;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.math.Vector3;
import engine.render.shader.ShaderManager;

public class LightElement extends Element{

    Vector3 position;
    Vector3 color;
    float intensity;

    public boolean hasChanges = false;
    public int id;

    public LightElement(Vector3 position, Vector3 color, float intensity) {
        id = ShaderManager.instance.addLightSource(position, new Vector3(0, 0, 0), color, intensity);
    }

    @Override
    public void update() {
        if(hasChanges()){
            ShaderManager.instance.updateLightSource(id, position, new Vector3(), color, intensity);
            hasChanges = false;
        }
    }

    @Override
    public void render() {
        if(Raylib.IsKeyDown(Raylib.KEY_L)){
            Raylib.DrawSphere(position.toRaylibVector3(), 1, Jaylib.WHITE);
        }
    }

    // getters setters
    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
        hasChanges = true;
    }

    public Vector3 getColor() {
        return color;
    }

    public void setColor(Vector3 color) {
        this.color = color;
        hasChanges = true;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
        hasChanges = true;
    }

    public boolean hasChanges() {
        return hasChanges;
    }
}
