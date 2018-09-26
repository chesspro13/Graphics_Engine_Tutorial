package IO;

import com.sun.javafx.geom.Vec3f;
import java.nio.DoubleBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author chess
 */
public class Window {

    private int width, height;
    private String title;
    private long window;

    private boolean[] keys;
    private boolean[] mouseButtons;

    private double time;
    private double processedTime;
    private double fps;
    private double fpsCap;

    private Vec3f backgroundColor;

    public Window(int width, int height, String title, int fps) {
        this.width = width;
        this.height = height;
        this.title = title;
        keys = new boolean[GLFW.GLFW_KEY_LAST];
        mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

        this.fpsCap = 1.0 / fps;
        processedTime = 0;
        backgroundColor = new Vec3f(0f, 0f, 0f);
    }

    public void create() {
        if (!GLFW.glfwInit()) {
            System.err.println("GLFW not working!");
            System.exit(-1);
        }

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        if (window == 0) {
            System.err.println("Window not created!");
            System.exit(-1);
        }

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);

        GLFW.glfwShowWindow(window);

        time = getTime();
    }

    public boolean closed() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void update() {
        for (int i = 0; i < GLFW.GLFW_KEY_LAST; i++) {
            keys[i] = isKeyDown(i);
        }
        for (int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++) {
            mouseButtons[i] = isMouseDown(i);
        }
        GL11.glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GLFW.glfwPollEvents();
    }

    public void stop() {
        GLFW.glfwTerminate();
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public boolean isKeyDown(int keyCode) {
        return GLFW.glfwGetKey(window, keyCode) == 1;
    }

    public boolean isMouseDown(int mouseButton) {
        return GLFW.glfwGetMouseButton(window, mouseButton) == 1;
    }

    public boolean isKeyPressed(int keyCode) {
        return isKeyDown(keyCode) && !keys[keyCode];
    }

    public boolean isKeyReleased(int keyCode) {
        return !isKeyDown(keyCode) && keys[keyCode];
    }

    public boolean isMousePressed(int keyCode) {
        return isMouseDown(keyCode) && !mouseButtons[keyCode];
    }

    public boolean isMouseReleased(int keyCode) {
        return !isMouseDown(keyCode) && mouseButtons[keyCode];
    }

    public double getMouseX() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(window, buffer, null);
        return buffer.get(0);
    }

    public double getMouseY() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(window, null, buffer);
        return buffer.get(0);
    }

    public double getTime() {
        return (double) System.nanoTime() / (double) 1000000000;
    }

    public boolean isUpdating() {
        double nextTime = getTime();
        double passedTime = nextTime - time;
        processedTime += passedTime;
        time = nextTime;

        while (processedTime > 1.0 / fpsCap) {
            processedTime -= 1.0 / fpsCap;
            return true;
        }
        return false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindow() {
        return window;
    }

    public double getFPS() {
        return fpsCap;
    }
    
    public void setBackgroundColor( float r, float g, float b )
    {
        backgroundColor = new Vec3f(r, g, b);
    }
}
