package game

import game.world.World

import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.glClear

class Game : GameLoop() {

    lateinit internal var world: World

    override fun init() {
        super.init()

        world = World()
    }

    override fun update(delta: Long) {
        world.update(delta)
    }

    override fun render() {
        glClear(GL_COLOR_BUFFER_BIT)

        world.render()
    }

    companion object {

        @JvmStatic fun main(args: Array<String>) {
            val game = Game()
        }
    }
}
