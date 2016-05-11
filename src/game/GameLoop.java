package game;

import game.input.InputHandler;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;

public class GameLoop {

    private static GameLoop gameLoop;

    private int FPS = 60;
    private long lastFPS;
    private int fps;

    public GameLoop() {
        gameLoop = this;

        try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAttribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCompatibility(true);

            Display.create(pixelFormat, contextAttribs);
            Display.setResizable(true);

            loop();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void init() {
        glClearColor(0, 0, 0, 0);
    }

    public void loop() {
        long lastFrame = getCurrentTime();
        lastFPS = getCurrentTime();
        long thisFrame;

        init();

        while (!Display.isCloseRequested()) {
            thisFrame = getCurrentTime();

            InputHandler.update();
            update(thisFrame - lastFrame);
            render();

            lastFrame = thisFrame;

            Display.update();

            if (Display.wasResized()) {
                resized();
            }

            Display.sync(FPS);
            updateFPS();
        }

        dispose();
    }

    public void update(long delta) {

    }

    public void render() {

    }

    public void updateFPS() {
        if (getCurrentTime() - lastFPS > 1000) {
            Display.setTitle("FPS " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public static long getCurrentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

    public static void resized() {
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
    }

    public void dispose() {

    }

    public static void exit() {
        gameLoop.dispose();
        Display.destroy();
        System.exit(0);
    }

    /**
     * Sets a DisplayMode after selecting for a better one.
     *
     * @param width      The width of the display.
     * @param height     The height of the display.
     * @param fullscreen The fullscreen mode.
     * @return True if switching is successful. Else false.
     */
    public static boolean setDisplayMode(int width, int height, boolean fullscreen) {
        // return if requested DisplayMode is already set
        if ((Display.getDisplayMode().getWidth() == width) &&
                (Display.getDisplayMode().getHeight() == height) &&
                (Display.isFullscreen() == fullscreen))
            return true;

        try {
            // The target DisplayMode
            DisplayMode targetDisplayMode = null;

            if (fullscreen) {
                // Gather all the DisplayModes available at fullscreen
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;

                // Iterate through all of them
                for (DisplayMode current : modes) {
                    // Make sure that the width and height matches
                    if ((current.getWidth() == width) && (current.getHeight() == height)) {
                        // Select the one with greater frequency
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
                            // Select the one with greater bits per pixel
                            if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }

                        // if we've found a match for bpp and frequency against the
                        // original display mode then it'screenManager probably best to go for this one
                        // since it'screenManager most likely compatible with the monitor
                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
                                (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } else {
                // No need to query for windowed mode
                targetDisplayMode = new DisplayMode(width, height);
            }

            if (targetDisplayMode == null) {
                System.out.println("Failed to find value mode: " + width + "screenX" + height + " fs=" + fullscreen);
                return false;
            }

            // Set the DisplayMode we've found
            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);

            System.out.println("Selected DisplayMode: " + targetDisplayMode.toString());

            // Generate a resized event
            gameLoop.resized();

            return true;
        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode " + width + "screenX" + height + " fullscreen=" + fullscreen + e);
        }

        return false;
    }
}
