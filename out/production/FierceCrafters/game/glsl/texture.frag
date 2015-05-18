#version 330 core

uniform sampler2D tex;
uniform float lighting;

in vec2 texCoords;

out vec4 outColor;

void main() {
    outColor = texture(tex, texCoords);
}