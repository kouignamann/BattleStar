#version 330 core

uniform sampler2D texture_diffuse;
uniform int use_texture;

in vec4 pass_Color;
in vec2 pass_TextureCoord;
in vec4 pass_Diffuse;
in vec4 pass_Specular;

smooth in vec3 vNormal;

out vec4 out_Color;

struct DirectionalLight
{
   vec3 vColor;
   vec3 vDirection;
   float fAmbientIntensity;
};
uniform DirectionalLight sunLight;

void main(void) {
    float fDiffuseIntensity = max(0.0, dot(normalize(vNormal), -sunLight.vDirection));
    vec4 sunVector = vec4(sunLight.vColor*(sunLight.fAmbientIntensity+fDiffuseIntensity), 1.0);
    out_Color = pass_Color*sunVector;
    
    // Override out_Color with our texture pixel
    if (use_texture == 1) {
      	out_Color = texture(texture_diffuse, pass_TextureCoord)*sunVector;
	}
}
