package game.world;

import game.entity.EntitySprites;
import game.entity.Player;
import game.graphics.ShaderManager;
import game.graphics.SpriteBatch;
import game.graphics.Texture;
import game.input.InputHandler;
import game.input.PlayerActions;
import game.map.Layer;
import game.map.Map;
import game.math.Vector2;
import org.lwjgl.opengl.Display;

public class World {

    private Map map;

    private Player player;
    private static float playerXOffset;
    private static float playerYOffset;

    private SpriteBatch entitySpriteBatch;

    public World() {
        map = new Map();
        player = new Player(EntitySprites.playerSprite, new Vector2(-1000, -1000));
        player.joinWorld(this);
        entitySpriteBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(EntitySprites.entitySpriteAtlas), 1000);
        InputHandler.setActions(new PlayerActions(player).getActions());
    }

    public void update(long delta) {
        updateEntities(delta);
    }

    public void updateEntities(long delta) {
        player.update(delta);
        updatePlayerOffsets(player.getX() - Display.getWidth() / 2, player.getY() - Display.getHeight() / 2);
    }

    public void render() {
        renderMap();
        renderEntities();
    }

    private void renderEntities() {
        entitySpriteBatch.begin();
        player.render(entitySpriteBatch);
        entitySpriteBatch.end();
    }

    private void renderMap() {
        map.renderMap(0, 0);
    }

    private void updatePlayerOffsets(float x, float y) {
        playerXOffset = x;
        playerYOffset = y;
    }

    public static float getPlayerXOffset() {
        return playerXOffset;
    }

    public static float getPlayerYOffset() {
        return playerYOffset;
    }

    public void interact(float x, float y) {
        map.remove((int) Layer.pixelToTile((int) x), (int) Layer.pixelToTile((int) y));
    }
}
