package game.input;

import game.entity.Player;
import static org.lwjgl.input.Keyboard.*;

import java.util.HashMap;

/**
 * Created by thewa on 5/11/2016.
 */
public class PlayerActions {

    private HashMap<Integer, Action> actions = new HashMap<Integer, Action>();

    private Player player;

    public PlayerActions(Player player) {
        this.player = player;
        loadActions();
    }

    /**
     * Be sure to set the actions listener!!!
     */
    public void addAction(int key, Action action) {
        if (actions.containsKey(key)) {
            System.err.print("\"PlayerActions\" A action was already mapped to " + key);
        } else {
            actions.put(key, action);
        }
    }

    public HashMap<Integer, Action> getActions() {
        return actions;
    }

    private void loadActions() {
        Action up = new Action("UP");
        up.setOnHoldListener(() -> player.translateY(1.0F));

        Action down = new Action();
        down.setOnHoldListener(() -> player.translateY(-1.0F));

        Action left = new Action();
        left.setOnHoldListener(() -> player.translateX(1.0F));

        Action right = new Action();
        right.setOnHoldListener(() -> player.translateX(-1.0F));

        Action action = new Action();
        action.setOnClickListener(() -> player.interact());

        actions.put(KEY_W, up);
        actions.put(KEY_UP, up);
        actions.put(KEY_S, down);
        actions.put(KEY_DOWN, down);
        actions.put(KEY_A, left);
        actions.put(KEY_LEFT, left);
        actions.put(KEY_D, right);
        actions.put(KEY_RIGHT, right);
        actions.put(KEY_SPACE, action);
    }
}
