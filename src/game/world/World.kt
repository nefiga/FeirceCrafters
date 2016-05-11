package game.world

import game.GameLoop
import game.entity.EntitySprites
import game.entity.Player
import game.graphics.ShaderManager
import game.graphics.SpriteBatch
import game.graphics.Texture
import game.input.InputHandler
import game.input.PlayerActions
import game.map.Layer
import game.map.Map
import game.math.Vector2
import org.lwjgl.opengl.Display

class World {

    private val map: Map

    private val player: Player

    private val entitySpriteBatch: SpriteBatch

    init {
        map = Map()
        player = Player(EntitySprites.playerSprite, Vector2(0f, 0f))
        player.joinWorld(this)
        entitySpriteBatch = SpriteBatch(ShaderManager.NORMAL_TEXTURE, Texture(EntitySprites.entitySpriteAtlas), 1000)
        InputHandler.setActions(PlayerActions(player).actions)
    }

    fun update(delta: Long) {
        updateEntities(delta)
    }

    fun updateEntities(delta: Long) {
        player.update(delta)
        updatePlayerOffsets(player.x + GameLoop.centerX, player.y + GameLoop.centerY)
    }

    fun render() {
        renderMap()
        renderEntities()
    }

    private fun renderEntities() {
        entitySpriteBatch.begin()
        player.render(entitySpriteBatch)
        entitySpriteBatch.end()
    }

    private fun renderMap() {
        map.renderMap(0, 0)
    }

    private fun updatePlayerOffsets(x: Float, y: Float) {
        playerXOffset = x
        playerYOffset = y
    }

    fun interact(x: Float, y: Float) {
        map.interact(Layer.pixelToTile(x.toInt()).toInt(), Layer.pixelToTile(y.toInt()).toInt())
    }

    companion object {
        var playerXOffset: Float = 0.toFloat()
            private set
        var playerYOffset: Float = 0.toFloat()
            private set
    }
}
