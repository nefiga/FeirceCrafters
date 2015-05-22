package game.world;

import game.entity.Player;
import game.map.Map;
import game.math.Vector2;
import org.lwjgl.opengl.Display;

public class World {

    private Map map;

    private Player player;
    private static float playerXOffset;
    private static float playerYOffset;

    public World() {
        map = new Map();
        player = new Player(new Vector2(0, 0));
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
    }

    private void renderEntities() {

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
}
