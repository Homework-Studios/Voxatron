#version 330

// Input vertex attributes (from vertex shader)
in vec3 fragPosition;
in vec2 fragTexCoord;
//in vec4 fragColor;
in vec3 fragNormal;

// Input uniform values
uniform sampler2D texture0;
uniform vec4 colDiffuse;
uniform vec3 viewPos;

// Output fragment color
out vec4 finalColor;


vec3 lightPosition = vec3(0.0, 10.0, 0.0);
vec4 lightColor = vec4(1.0, 0.0, 0.0, 1.0);
vec4 ambient = vec4(0.1, 0.1, 0.1, 1.0);

void main()
{
    // Texel color fetching from texture sampler
    vec4 texelColor = texture(texture0, fragTexCoord);
    vec3 lightDot = vec3(0.0);
    vec3 normal = normalize(fragNormal);
    vec3 viewD = normalize(viewPos - fragPosition);
    vec3 specular = vec3(0.0);

    vec3 light = normalize(lightPosition - fragPosition);

    float NdotL = max(dot(normal, light), 0.0);
    lightDot += vec3(lightColor)*NdotL;

    float specCo = 0.0;
    if (NdotL > 0.0) specCo = pow(max(0.0, dot(viewD, reflect(-(light), normal))), 16.0); // 16 refers to shine
    specular += specCo;

    // attenuation
    float distance = length(lightPosition - fragPosition);
    float attenuation = 1.0 / (1.0 + 0.1 * pow(distance, 2.0));

    finalColor = (texelColor*((colDiffuse + vec4(specular, 1.0))*vec4(lightDot, 1.0))) * attenuation;
    // remove transparency
    finalColor.a = 1.0;
    finalColor += texelColor*(ambient/10.0)*colDiffuse;

    // Gamma correction
    finalColor = pow(finalColor, vec4(1.0/2.2));
}