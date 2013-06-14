import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL20;

abstract public class Base {

    void run() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(800, 600));
        Display.create();
        Display.setTitle(this.getClass().getName());
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

    public int loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("shaders/" + filename));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }

        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        return shaderID;
    }

    abstract void init();

    abstract void renderScene();

    void updateWorld() {
    }

    void checkInput() {
    }

    void teardown() {
    }
}
