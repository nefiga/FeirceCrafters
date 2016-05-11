package game.entity;

import game.graphics.ImageManager;
import game.graphics.Sprite;
import game.graphics.TextureAtlas;

/**
 * Created by thewa on 5/11/2016.
 */
public class EntitySprites {

    public static final TextureAtlas entitySpriteAtlas = new TextureAtlas(TextureAtlas.LARGE);

    public static final Sprite playerSprite = entitySpriteAtlas.addTexture("faded", ImageManager.getImage("/faded"));
}
