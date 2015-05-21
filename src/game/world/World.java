package game.world;

import game.graphics.SpriteBatch;
import game.map.Map;

public class World {

    SpriteBatch mapBatch;

    private Map map;

    public World() {
        map = new Map();
    }

    public void update(long delta) {

    }

    public void render() {
        map.renderMap(0, 0);
    }

    private void renderMap() {
        //TODO figure offsets
        map.renderMap(0, 0);
    }
}
