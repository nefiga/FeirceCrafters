package game;

import game.graphics.Sprite;
import game.graphics.SpriteBatch;
import game.graphics.TextureAtlas;
import game.world.World;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class Game extends GameLoop {

    TextureAtlas atlas;
    SpriteBatch spriteBatch;

    World world;

    Sprite testImage, testImageTwo;

    public void init() {
        super.init();

        world = new World();
    }

    public void update(long delta) {
        world.update(delta);
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);

        world.render();
    }

    public static void main(String[] args) {
        GameLoop game = new Game();
    }
}
