#version 330 core

uniform sampler2D texture_diffuse;
uniform int use_texture;

in vec4 pass_Color;
in vec2 pass_TextureCoord;

smooth in vec3 vNormal;

out vec4 out_Color;

void main(void) {
    out_Color = pass_Color;
    if (use_texture == 1) {
    	out_Color = texture(texture_diffuse, pass_TextureCoord);
	}
}
