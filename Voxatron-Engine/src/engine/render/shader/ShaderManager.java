package engine.render.shader;

import com.raylib.Raylib;
import engine.math.Vector3;
import engine.render.Renderer;
import org.bytedeco.javacpp.FloatPointer;

public class ShaderManager {

    public static ShaderManager instance;

    public Raylib.Shader lightShader;

    public ShaderManager() {
        instance = this;
    }

    // Mate Innit? Hehehehehe
    public void init() {
        String path = System.getProperty("user.dir") + "\\Voxatron-Engine\\src\\engine\\shader\\";
        lightShader = Raylib.LoadShader(path + "lighting.vert", path + "lighting.frag");
    }

    //// Maximum number of light sources
    //const int MAX_LIGHT_SOURCES = 4;
    //
    //// Light struct
    //struct Light {
    //    vec3 position;
    //    vec4 color;
    //    float intensity;
    //};
    //
    //// Light source array
    //uniform Light lights[MAX_LIGHT_SOURCES];

    public void addLightSource() {
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[0].position"), new Vector3(0, 20, 0).toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC3);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[0].color"), new Vector3(1,1,1).toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC4);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[0].intensity"), new FloatPointer(5.0f), Raylib.SHADER_ATTRIB_FLOAT);

        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[1].position"), new Vector3(50, 20, 0).toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC3);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[1].color"), new Vector3(1,1,1).toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC4);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[1].intensity"), new FloatPointer(5.0f), Raylib.SHADER_ATTRIB_FLOAT);

        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[2].position"), new Vector3(0, 20, 50).toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC3);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[2].color"), new Vector3(1,1,1).toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC4);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[2].intensity"), new FloatPointer(5.0f), Raylib.SHADER_ATTRIB_FLOAT);

        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[3].position"), new Vector3(50, 20, 50).toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC3);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[3].color"), new Vector3(1,1,1).toRaylibVector3(), Raylib.SHADER_ATTRIB_VEC4);
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "lights[3].intensity"), new FloatPointer(5.0f), Raylib.SHADER_ATTRIB_FLOAT);
    }

    public void update() {
        Raylib.SetShaderValue(lightShader, Raylib.GetShaderLocation(lightShader, "viewPos"), Renderer.camera.position, Raylib.SHADER_ATTRIB_VEC3);

        addLightSource();
    }
}