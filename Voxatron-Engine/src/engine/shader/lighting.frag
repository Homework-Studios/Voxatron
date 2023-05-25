#version 330

// Input vertex attributes (from vertex shader)
in vec3 fragPosition;
in vec2 fragTexCoord;
in vec3 fragNormal;

// Input uniform values
uniform sampler2D texture0;
uniform vec4 colDiffuse;
uniform vec3 viewPos;

// Maximum number of light sources
const int MAX_LIGHT_SOURCES = 4;

// Light struct
struct Light {
    vec3 position;
    vec4 color;
    float intensity;
};

// Light source array
uniform Light lights[MAX_LIGHT_SOURCES];

vec4 ambient = vec4(0.1, 0.1, 0.1, 1.0);

// Output fragment color
out vec4 finalColor;

float attenuation(Light light)
{
    float distance = length(light.position - fragPosition);

    // light intensity
    distance /= light.intensity;

    return 1.0 / (1.0 + 0.1 * pow(distance, 2.0));
}

vec3 CalcLight(vec3 normal, vec3 viewDir, Light light)
{
    vec3 lightDir = normalize(light.position - fragPosition);
    float NdotL = max(dot(normal, lightDir), 0.0);
    vec4 color = (light.color * colDiffuse)/2;

    vec3 lightDot = vec3(color) * NdotL;

    // Specular
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 16.0);
    vec3 specular = vec3(color) * spec;

    // attenuation
    float attenuation = attenuation(light);

    return (lightDot + specular) * attenuation;
}

void ApplyAmbientLight(vec4 texelColor)
{
    finalColor += texelColor * (ambient / 10.0) * colDiffuse;
}

void ApplyGammaCorrection()
{
    finalColor = pow(finalColor, vec4(1.0 / 2.2));
}

void main()
{
    // Texel color fetching from texture sampler
    vec4 texelColor = texture(texture0, fragTexCoord);
    vec3 normal = normalize(fragNormal);
    vec3 viewD = normalize(viewPos - fragPosition);

    vec3 totalLight = vec3(0.0);

    for (int i = 0; i < MAX_LIGHT_SOURCES; i++)
    {
        Light light = lights[i];

        vec3 lightResult = CalcLight(normal, viewD, light);
        totalLight += lightResult;
    }

    finalColor = texelColor * vec4((totalLight / MAX_LIGHT_SOURCES), 1.0);

    ApplyAmbientLight(texelColor);
    ApplyGammaCorrection();

    // Remove transparency
    finalColor.a = 1.0;
}
