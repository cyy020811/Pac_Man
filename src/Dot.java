import bagel.Image;

public class Dot extends Entity {
    private final static Image DOT_IMAGE = new Image("res/dot.png");
    private boolean isEaten = false;

    public Dot(double x, double y) {
        super(DOT_IMAGE, x, y);
    }

    public void eaten() {
        isEaten = true;
    }

    public boolean isEaten() {
        return isEaten;
    }

    @Override
    // Do not draw the dot if it is eaten
    public void draw() {
        if(!isEaten) super.draw();
    }
}
