package game.entity;

import game.graphics.SpriteBatch;
import game.math.Vector2;

public class Entity {

    Vector2 position;

    public Entity(Vector2 position) {
        this.position = position;
    }

    public void update(long delta) {

    }

    public void render(SpriteBatch spriteBatch) {

    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }
}
