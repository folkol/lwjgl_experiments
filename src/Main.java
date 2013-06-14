import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Main {

    private static int vertexCount;
    private static int vertexArrayId;
    private static int vertexBufferObjectId;

    public static void main(String[] args) throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(800, 600));
        Display.create();
        init();
        while (!Display.isCloseRequested()) {
            checkInput();
            updateWorld();
            renderScene();
            Display.setVSyncEnabled(true);
            Display.update();
        }
        teardown();
        Display.destroy();
    }

    private static void init() {
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));

        float[] vertices = {
                // Left bottom triangle
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                // Right top triangle
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f };
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(vertices.length);
        floatBuffer.put(vertices);
        floatBuffer.flip();

        vertexCount = 6;

        vertexArrayId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vertexArrayId);

        vertexBufferObjectId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferObjectId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);
    }

    private static void renderScene() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GL30.glBindVertexArray(vertexArrayId);
        GL20.glEnableVertexAttribArray(0);

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
    }

    private static void updateWorld() {

    }

    private static void checkInput() {
        if (Mouse.isButtonDown(0)) {
            System.out.println("Vänster knapp nere");
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            System.out.println("Space nere");
        }
    }

    private static void teardown() {
        GL20.glDisableVertexAttribArray(0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vertexBufferObjectId);

        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vertexArrayId);
    }
}
