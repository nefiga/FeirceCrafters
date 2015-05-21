package game.world;

import game.map.Map;

public class World {

    private Map map;

    public World() {
        map = new Map();
    }

    public void update(long delta) {

    }

    public void render() {
        renderMap();
    }

    private void renderMap() {
        //TODO figure offsets
        map.renderMap(0, 0);
    }
}
