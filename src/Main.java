import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Main {

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
        Display.destroy();
    }

    private static void init() {
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
    }

    private static void renderScene() {

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
}
