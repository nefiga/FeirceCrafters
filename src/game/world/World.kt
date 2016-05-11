package game.world

import game.GameLoop
import game.entity.EntitySprites
import game.entity.Player
import game.entity.item.ItemEntity
import game.entity.item.ItemEntitySprites
import game.graphics.ShaderManager
import game.graphics.SpriteBatch
import game.graphics.Texture
import game.input.InputHandler
import game.input.PlayerActions
import game.util.Util.pixelToTile
import game.util.Util.tileToPixel
import game.map.Map
import game.math.Vector2
import java.util.*

class World {

    private val map: Map

    private val player: Player

    private val entitySpriteBatch: SpriteBatch
    private val entityItemSpriteBatch: SpriteBatch

    private var itemEntities = ArrayList<ItemEntity>()

    init {
        map = Map(this)
        player = Player(EntitySprites.playerSprite, Vector2(0F, 0F))
        player.joinWorld(this)
        entitySpriteBatch = SpriteBatch(ShaderManager.NORMAL_TEXTURE, Texture(EntitySprites.entitySpriteAtlas), 1000)
        entityItemSpriteBatch = SpriteBatch(ShaderManager.NORMAL_TEXTURE, Texture(ItemEntitySprites.itemEntityAtlas), 1000)
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
        entityItemSpriteBatch.begin()
        entityItemSpriteBatch.setOffsets(World.playerXOffset, World.playerYOffset)
        for (itemEntity in itemEntities) {
            itemEntity.render(entityItemSpriteBatch)
        }
        entityItemSpriteBatch.end()

        entitySpriteBatch.begin()
        player.render(entitySpriteBatch)
        entitySpriteBatch.end()
    }

    private fun renderMap() {
        map.renderMap(pixelToTile(-player.x - GameLoop.centerX), pixelToTile(-player.y - GameLoop.centerY))
    }

    private fun updatePlayerOffsets(x: Float, y: Float) {
        playerXOffset = x
        playerYOffset = y
    }

    fun interact(x: Float, y: Float) {
        map.interact(pixelToTile(x.toInt()).toInt(), pixelToTile(y.toInt()).toInt())
    }

    fun addItemEntity(itemEntity: ItemEntity) {
        itemEntities.add(itemEntity)
    }

    companion object {
        var playerXOffset: Float = 0.toFloat()
            private set
        var playerYOffset: Float = 0.toFloat()
            private set
    }
}
