#version 330

// Input vertex attributes (from vertex shader)
in vec2 fragTexCoord;
in vec4 fragColor;

// Input uniform values
uniform sampler2D texture0;
uniform vec4 colDiffuse;
uniform float width;
uniform float height;

// Output fragment color
out vec4 finalColor;

void main()
{
    vec4 Color = texture(texture0, fragTexCoord);

    finalColor = Color;
}