import bagel.Image;

public class Cherry extends Entity {
    private final static Image CHERRY_IMAGE = new Image("res/cherry.png");
    private boolean isEaten = false;

    public Cherry(double x, double y) {
        super(CHERRY_IMAGE, x, y);
    }

    public void eaten() {
        isEaten = true;
    }

    public boolean isEaten() {
        return isEaten;
    }

    @Override
    // Do not draw the cherry if it is eaten
    public void draw() {
        if(!isEaten) super.draw();
    }
}
