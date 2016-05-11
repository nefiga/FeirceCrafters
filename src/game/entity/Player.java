package game.entity;

import game.Game;
import game.graphics.Sprite;
import game.graphics.SpriteBatch;
import game.map.Layer;
import game.math.Vector2;
import game.world.World;
import org.lwjgl.opengl.Display;

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
        world.interact(-position.getX() - 32, -position.getY() -32);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw((float) (Game.getCenterX() - 32), (float) (Game.getCenterY() - 32), sprite);
    }


}
