package game.input;

/**
 * Created by thewa on 5/11/2016.
 */
public class Action {
    private OnClickListener onClickListener;
    private OnHoldListener onHoldListener;

    private boolean holding;
    private boolean pressed;
    private boolean clicked;

    private String name;

    public Action(String name) {
        this.name = name;
    }

    public Action() {

    }

    public void handleInput() {
        if (isHolding() && onHoldListener != null)
            onHoldListener.holding();
        if (isClicked() && onClickListener != null)
            onClickListener.clicked();
    }

    public void press() {
        if (!holding) {
            pressed = true;
            holding = true;
        }
        else pressed = false;
    }

    public void release() {
        if (holding || pressed)
            clicked = true;

        holding = false;
        pressed = false;
    }

    public boolean isHolding() {
        return holding;
    }

    public boolean isPressed() {
        if (pressed){
            pressed = false;
            return true;
        }
        return false;
    }

    public boolean isClicked() {
        if (clicked) {
            clicked = false;
            return true;
        }

        return false;
    }

    public String getName() {
        return name;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnHoldListener(OnHoldListener onHoldListener) {
        this.onHoldListener = onHoldListener;
    }

    public interface OnClickListener {
        void clicked();
    }

    public interface OnHoldListener {
        void holding();
    }
}
