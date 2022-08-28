#version 150

in vec2 uvOut;

out vec4 fragColor;

void main() {
    fragColor = vec4(uvOut.x, (uvOut.x + uvOut.y) / 2, uvOut.y, 1.0);
}
