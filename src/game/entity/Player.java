package game.entity;

import game.graphics.Sprite;
import game.graphics.SpriteBatch;
import game.map.Layer;
import game.math.Vector2;
import game.world.World;

public class Player extends Entity implements VisibleEntity {

    private World world;

    private Sprite sprite;

    public Player(Sprite sprite, Vector2 position) {
        super(position);
        this.sprite = sprite;
    }

    public void joinWorld(World world) {
        this.world = world;
    }

    public void update(long delta) {

    }

    public void interact() {
        world.interact(-position.getX() - World.getPlayerXOffset(), -position.getY() - World.getPlayerYOffset());
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(position.getX() - World.getPlayerXOffset(), position.getY() - World.getPlayerYOffset(), sprite);
    }


}
