package lwjgl_engine;

import IO.Window;
import Render.Model;
import Render.Renderer;

/**
 *
 * @author chess
 */
public class LWJGL_Engine {

    public static Renderer renderer;
    
    public static void main(String[] args) {
        Window window = new Window(800, 600, "Fuck", 60);
        renderer = new Renderer();
        window.create();
        window.setBackgroundColor(1.0f, 0.0f, 0.0f);
        
        Model model = new Model(new float[]{
           -0.5f, 0.5f, 0.0f, //TOP LEFT
           0.5f, 0.5f, 0.0f, //TOP RIGHT
           0.5f, -0.5f, 0.0f, //BOTTOM RIGHT
           
           -0.5f, -0.5f, 0.0f, //BOTTOM LEFT
           0.5f, -0.5f, 0.0f, //BOTTOM RIGHT
           -0.5f, 0.5f, 0.0f //TOP LEFT
        });
        model.create();

        while (!window.closed()) {
            if (!window.isUpdating())
                window.update();
            renderer.renderModel(model);
            window.swapBuffers();
        }

        window.stop();
        model.remove();
    }
    
}
