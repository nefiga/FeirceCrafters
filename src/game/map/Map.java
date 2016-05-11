package game.map;

import game.tiles.Tiles;

import java.util.HashMap;

public class Map {

    private HashMap<String, Layer> layers;

    private Layer currentLayer;

    public Map() {
        currentLayer = new Layer(Tiles.spriteBatch, 300, 300);
    }

    public void renderMap(int offsetX, int offsetY) {
        currentLayer.render(offsetX, offsetY);
    }

    public void update(int xPosition, int yPosition, long delta) {
        currentLayer.update(xPosition, yPosition, delta);
    }

    public void remove(int xPosition, int yPosition) {
        currentLayer.remove(xPosition, yPosition);
    }

    //Should take an entity once they have been set up
    public void interact(int xPosition, int yPosition) {
        currentLayer.interact(xPosition, yPosition);
    }

    public void addLayer(String name, Layer layer) {
        layers.put(name, layer);
    }

    public void setCurrentLayer(String name) {
        if (layers.containsKey(name))
            currentLayer = layers.get(name);
        else
            System.err.println("Could not set current layer to " + name + " because it does not exist");
    }
}
