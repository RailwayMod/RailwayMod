#version 150

in vec3 position;
in vec2 uvIn;

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;

out vec2 uvOut;

void main() {
    gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
    uvOut = uvIn;
}
