package game.entity;

import game.math.Vector2;

public class Entity {

    Vector2 position;

    public Entity(Vector2 position) {
        this.position = position;
    }

    public void update(long delta) {

    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }

    public void translateX(float x) {
        position.translateX(x);
    }

    public void translateY(float y) {
        position.translateY(y);
    }
}
