package game

import game.input.InputHandler
import org.lwjgl.LWJGLException
import org.lwjgl.Sys
import org.lwjgl.opengl.ContextAttribs
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.DisplayMode
import org.lwjgl.opengl.PixelFormat

import org.lwjgl.opengl.GL11.glClearColor
import org.lwjgl.opengl.GL11.glViewport

open class GameLoop {

    private val FPS = 60
    private var lastFPS: Long = 0
    private var fps: Int = 0

    init {
        gameLoop = this

        try {
            val pixelFormat = PixelFormat()
            val contextAttribs = ContextAttribs(3, 2).withForwardCompatible(true).withProfileCompatibility(true)

            Display.create(pixelFormat, contextAttribs)
            Display.setResizable(true)

            loop()
        } catch (e: LWJGLException) {
            e.printStackTrace()
            System.exit(-1)
        }

    }

    open fun init() {
        glClearColor(0f, 0f, 0f, 0f)

        centerX = Display.getWidth() / 2
        centerY = Display.getHeight() / 2
    }

    fun loop() {
        var lastFrame = currentTime
        lastFPS = currentTime
        var thisFrame: Long

        init()

        while (!Display.isCloseRequested()) {
            thisFrame = currentTime

            InputHandler.update()
            update(thisFrame - lastFrame)
            render()

            lastFrame = thisFrame

            Display.update()

            if (Display.wasResized()) {
                resized()
            }

            Display.sync(FPS)
            updateFPS()
        }

        dispose()
    }

    open fun update(delta: Long) {

    }

    open fun render() {

    }

    fun updateFPS() {
        if (currentTime - lastFPS > 1000) {
            Display.setTitle("FPS " + fps)
            fps = 0
            lastFPS += 1000
        }
        fps++
    }

    fun dispose() {

    }

    companion object {
        lateinit private var gameLoop: GameLoop

        var centerX: Int = 0
            private set
        var centerY: Int = 0
            private set

        val currentTime: Long
            get() = Sys.getTime() * 1000 / Sys.getTimerResolution()

        open fun resized() {
            glViewport(0, 0, Display.getWidth(), Display.getHeight())
        }

        fun exit() {
            gameLoop.dispose()
            Display.destroy()
            System.exit(0)
        }

        /**
         * Sets a DisplayMode after selecting for a better one.

         * @param width      The width of the display.
         * *
         * @param height     The height of the display.
         * *
         * @param fullscreen The fullscreen mode.
         * *
         * @return True if switching is successful. Else false.
         */
        fun setDisplayMode(width: Int, height: Int, fullscreen: Boolean): Boolean {
            // return if requested DisplayMode is already set
            if (Display.getDisplayMode().width == width &&
                    Display.getDisplayMode().height == height &&
                    Display.isFullscreen() == fullscreen)
                return true

            try {
                // The target DisplayMode
                var targetDisplayMode: DisplayMode? = null

                if (fullscreen) {
                    // Gather all the DisplayModes available at fullscreen
                    val modes = Display.getAvailableDisplayModes()
                    var freq = 0

                    // Iterate through all of them
                    for (current in modes) {
                        // Make sure that the width and height matches
                        if (current.width == width && current.height == height) {
                            // Select the one with greater frequency
                            if (targetDisplayMode == null || current.frequency >= freq) {
                                // Select the one with greater bits per pixel
                                if (targetDisplayMode == null || current.bitsPerPixel > targetDisplayMode.bitsPerPixel) {
                                    targetDisplayMode = current
                                    freq = targetDisplayMode!!.frequency
                                }
                            }

                            // if we've found a match for bpp and frequency against the
                            // original display mode then it'screenManager probably best to go for this one
                            // since it'screenManager most likely compatible with the monitor
                            if (current.bitsPerPixel == Display.getDesktopDisplayMode().bitsPerPixel && current.frequency == Display.getDesktopDisplayMode().frequency) {
                                targetDisplayMode = current
                                break
                            }
                        }
                    }
                } else {
                    // No need to query for windowed mode
                    targetDisplayMode = DisplayMode(width, height)
                }

                if (targetDisplayMode == null) {
                    println("Failed to find value mode: " + width + "screenX" + height + " fs=" + fullscreen)
                    return false
                }

                // Set the DisplayMode we've found
                Display.setDisplayMode(targetDisplayMode)
                Display.setFullscreen(fullscreen)

                println("Selected DisplayMode: " + targetDisplayMode.toString())

                // Generate a resized event
                resized()

                return true
            } catch (e: LWJGLException) {
                println("Unable to setup mode " + width + "screenX" + height + " fullscreen=" + fullscreen + e)
            }

            return false
        }
    }
}
