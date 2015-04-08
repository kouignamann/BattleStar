#version 330 core

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

layout (location = 0) in vec4 in_Position;
layout (location = 1) in vec4 in_Normal;
layout (location = 2) in vec4 in_Color;
layout (location = 3) in vec2 in_TextureCoord;
layout (location = 4) in vec4 in_Diffuse;
layout (location = 5) in vec4 in_Specular;

out vec4 pass_Color;
out vec2 pass_TextureCoord;
out vec4 pass_Diffuse;
out vec4 pass_Specular;

smooth out vec3 vNormal;

void main(void) {
    mat4 modelView = viewMatrix * modelMatrix;
    gl_Position = projectionMatrix * modelView * in_Position;
    
    mat4 normalMatrix = transpose(inverse(modelView));
	vec4 vRes = normalMatrix*in_Normal;
	vNormal = vRes.xyz;
	
    pass_Color = in_Color;
    pass_TextureCoord = in_TextureCoord;
    pass_Diffuse = in_Diffuse;
    pass_Specular = in_Specular;
}
