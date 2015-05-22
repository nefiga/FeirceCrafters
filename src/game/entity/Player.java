package game.entity;

import game.math.Vector2;

public class Player extends Entity {

    public Player(Vector2 position) {
        super(position);
    }

    public void update(long delta) {
        position.setX(position.getX() + 1);
        position.setY(position.getY() + 1);
    }
}
