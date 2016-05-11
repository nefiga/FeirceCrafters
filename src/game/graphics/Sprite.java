package game.graphics;

/**
 * Location of the image in the atlas
 */
public class Sprite {
    public final int startX;
    public final int startY;
    public final int endX;
    public final int endY;
    public final int width;
    public final int height;

    public Sprite(int startX, int startY, int width, int height) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        endX = startX + width;
        endY = startY + height;
    }
}
