#version 150 core

in vec4 in_Position;
in vec4 in_Color;
in vec2 in_TextureCoord;

uniform float in_Rotation;;

out vec4 pass_Color;
out vec2 pass_TextureCoord;

void main(void) {
	 mat4 rotation = mat4(
	        vec4(1.0,         0.0,         0.0, 0.0),
        	vec4(0.0,  cos(in_Rotation),  sin(in_Rotation), 0.0),
	        vec4(0.0, -sin(in_Rotation),  cos(in_Rotation), 0.0),
        	vec4(0.0,         0.0,         0.0, 1.0)
	    );

	gl_Position = rotation * in_Position;
	
	pass_Color = in_Color;
}