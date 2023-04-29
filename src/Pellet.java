import bagel.Image;

public class Pellet extends Entity {
    private final static Image PELLET_IMAGE = new Image("res/pellet.png");
    private boolean isEaten = false;

    public Pellet(double x, double y) {
        super(PELLET_IMAGE, x, y);
    }

    public void eaten() {
        isEaten = true;
    }

    public boolean isEaten() {
        return isEaten;
    }

    @Override
    // Do not draw the pellet if it is eaten
    public void draw() {
        if(!isEaten) super.draw();
    }
}
