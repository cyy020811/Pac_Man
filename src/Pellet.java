import bagel.Image;

public class Pellet extends Entity {
    private final static Image PELLET_IMAGE = new Image("res/pellet.png");

    public Pellet(double x, double y) {
        super(PELLET_IMAGE, x, y);
    }
}
