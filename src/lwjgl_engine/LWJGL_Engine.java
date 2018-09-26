package lwjgl_engine;

import IO.Window;

/**
 *
 * @author chess
 */
public class LWJGL_Engine {

    public static void main(String[] args) {
        Window window = new Window(800, 600, "Fuck", 60);
        window.create();

        while (!window.closed()) {
            if (window.isUpdating()) {
                window.update();
            }
            window.swapBuffers();
        }

        window.stop();
    }
    
}
