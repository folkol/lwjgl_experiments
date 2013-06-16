import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class TexturedAndShadedModel extends Base {

    private int indicesCount;

    private float rotation;

    private int vaoId = 0;
    private int vboId = 0;
    private int vboiId = 0;
    private int vsId = 0;
    private int fsId = 0;
    private int pId = 0;
    private final int glTexIds[] = new int[1];

    public static void main(String[] args) throws LWJGLException {
        new TexturedAndShadedModel().run();
    }

    @Override
    void init() {
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

        setupQuad();
        setupShaders();
        setupTextures();
    }

    @Override
    void updateWorld() {
        rotation = 0.01f * Mouse.getX();
    }

    @Override
    void renderScene() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GL20.glUseProgram(pId);

        int glGetUniformLocation = GL20.glGetUniformLocation(pId, "in_Rotation");
        GL20.glUniform1f(glGetUniformLocation, rotation);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, glTexIds[0]);

        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);

        GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_BYTE, 0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);

        GL20.glUseProgram(0);
    }

    public void setupQuad() {
        final int numSides = 4;
        TexturedVertex[] vertices = new TexturedVertex[2 + numSides];
        byte[] indices = new byte[2 * 3 * numSides];

        TexturedVertex bottom = new TexturedVertex(0f, -0.5f, 0f, 0f, 0f, 0f, 1f, 0.5f);
        TexturedVertex top = new TexturedVertex(0f, 0.5f, 0f, 0f, 0f, 0f, 1f, 0.5f);

        // for (int i = 0; i < numCorners; i++) {
        TexturedVertex v1 = new TexturedVertex(0f, 0f, 0.5f, 1f, 0f, 0f, 0.5f, 0.75f);
        TexturedVertex v2 = new TexturedVertex(-0.5f, 0f, 0f, 0, 1, 0, 0.5f, 0.5f);
        TexturedVertex v3 = new TexturedVertex(0f, 0f, -0.5f, 0, 0, 1, 0.5f, 0.25f);
        TexturedVertex v4 = new TexturedVertex(0.5f, 0f, 0f, 1, 1, 1, 0.5f, 0f);

        vertices[0] = bottom;
        vertices[1] = v1;
        vertices[2] = v2;
        vertices[3] = v3;
        vertices[4] = v4;
        vertices[5] = top;

        // for (int i = 0; i < numCorners; i++) {
        // 0, 1, 2 };
        indices[0] = 0;
        indices[1] = 1;
        indices[2] = 2;

        indices[3] = 0;
        indices[4] = 2;
        indices[5] = 3;

        indices[6] = 0;
        indices[7] = 3;
        indices[8] = 4;

        indices[9] = 0;
        indices[10] = 4;
        indices[11] = 1;

        indices[12] = 5;
        indices[13] = 2;
        indices[14] = 1;

        indices[15] = 5;
        indices[16] = 3;
        indices[17] = 2;

        indices[18] = 5;
        indices[19] = 4;
        indices[20] = 3;

        indices[21] = 5;
        indices[22] = 1;
        indices[23] = 4;

        indicesCount = indices.length;

        ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        // }

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length * TexturedVertex.elementCount);
        for (int i = 0; i < vertices.length; i++) {
            verticesBuffer.put(vertices[i].getElements());
        }
        verticesBuffer.flip();
        vaoId = GL30.glGenVertexArrays();

        GL30.glBindVertexArray(vaoId);

        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, TexturedVertex.positionElementCount, GL11.GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.positionByteOffset);
        GL20.glVertexAttribPointer(1, TexturedVertex.colorElementCount, GL11.GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.colorByteOffset);
        GL20.glVertexAttribPointer(2, TexturedVertex.textureElementCount, GL11.GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.textureByteOffset);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);

        vboiId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        checkError();
    }

    private void setupShaders() {
        vsId = this.loadShader(this.getClass().getName() + "Vertex.glsl", GL20.GL_VERTEX_SHADER);
        fsId = this.loadShader(this.getClass().getName() + "Fragment.glsl", GL20.GL_FRAGMENT_SHADER);

        pId = GL20.glCreateProgram();
        GL20.glAttachShader(pId, vsId);
        GL20.glAttachShader(pId, fsId);
        GL20.glLinkProgram(pId);

        GL20.glBindAttribLocation(pId, 0, "in_Position");
        GL20.glBindAttribLocation(pId, 1, "in_Color");
        GL20.glBindAttribLocation(pId, 2, "in_TextureCoord");

        GL20.glValidateProgram(pId);

        checkError();
    }

    private void checkError() {
        if (GL11.glGetError() != GL11.GL_NO_ERROR) {
            System.err.println("ERROR");
        }
    }

    private void setupTextures() {
        glTexIds[0] = loadTexture("lake.png", GL13.GL_TEXTURE0);
    }

    private int loadTexture(String filename, int textureUnit) {
        ByteBuffer textureDataBuffer = null;
        int tWidth = 0;
        int tHeight = 0;

        try {
            InputStream in = new FileInputStream("textures/" + filename);
            PNGDecoder decoder = new PNGDecoder(in);

            tWidth = decoder.getWidth();
            tHeight = decoder.getHeight();

            textureDataBuffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(textureDataBuffer, decoder.getWidth() * 4, Format.RGBA);
            textureDataBuffer.flip();

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        int texId = GL11.glGenTextures();
        GL13.glActiveTexture(textureUnit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, tWidth, tHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureDataBuffer);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);

        checkError();

        return texId;
    }

}