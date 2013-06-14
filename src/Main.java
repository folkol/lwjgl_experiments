import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Main {
    private static float x;
    private static float y;
    private static float rotation;

    public static void main(String[] args) throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(800, 600));
        Display.create();
        init();
        while (!Display.isCloseRequested()) {
            checkInput();
            updateWorld();
            renderScene();
            Display.sync(60);
            Display.update();
        }
        Display.destroy();
    }

    private static void init() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 0, 600, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
    }

    private static void renderScene() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glColor3f(0.5f, 0.5f, 1.0f);
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glRotatef(rotation, 0f, 0f, 1f);
        GL11.glTranslatef(-x, -y, 0);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x - 50, y - 50);
        GL11.glVertex2f(x + 50, y - 50);
        GL11.glVertex2f(x + 50, y + 50);
        GL11.glVertex2f(x - 50, y + 50);
        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glLineWidth(2.5f);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2f(0.0f, 0.0f);
        GL11.glVertex2f(100f, 200f);
        GL11.glEnd();
    }

    private static void updateWorld() {
        x++;
        y++;
        rotation++;
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
