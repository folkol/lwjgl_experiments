import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

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

    abstract void init();

    abstract void renderScene();

    void updateWorld() {
    }

    void checkInput() {
    }

    void teardown() {
    }
}
