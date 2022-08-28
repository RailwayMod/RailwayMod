#version 150

in vec2 uvOut;

uniform sampler2D textureSampler;

out vec4 fragColor;

void main() {
    fragColor = texture(textureSampler, uvOut);
    //fragColor = vec4(1.0, 1.0, 1.0, 1.0);
}
