#version 150 core

in vec4 in_Position;
in vec4 in_Color;
in vec2 in_TextureCoord;

uniform float in_Rotation;;

out vec4 pass_Color;
out vec2 pass_TextureCoord;

void main(void) {
	vec3 axis = vec3(0f, 1f, 0f);
	float s = sin(in_Rotation);
	float c = cos(in_Rotation);
	float oc = 1.0f - c;

//	mat4 rotation = mat4(
//		vec4(1.0,         0.0,         0.0, 0.0),
//        	vec4(0.0,  cos(in_Rotation),  sin(in_Rotation), 0.0),
//		vec4(0.0, -sin(in_Rotation),  cos(in_Rotation), 0.0),
//		vec4(0.0,         0.0,         0.0, 1.0)
//	);



	mat4 rotation = mat4(
		vec4(oc * axis.x * axis.x + c,           oc * axis.x * axis.y - axis.z * s,  oc * axis.z * axis.x + axis.y * s,  0.0),
                vec4(oc * axis.x * axis.y + axis.z * s,  oc * axis.y * axis.y + c,           oc * axis.y * axis.z - axis.x * s,  0.0),
                vec4(oc * axis.z * axis.x - axis.y * s,  oc * axis.y * axis.z + axis.x * s,  oc * axis.z * axis.z + c,           0.0),
                vec4(0.0,                                0.0,                                0.0,                                1.0)
	);

	gl_Position = in_Position * rotation;
	
	pass_Color = in_Color;
	pass_TextureCoord = in_TextureCoord;
}