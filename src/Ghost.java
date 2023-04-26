import bagel.Image;

public class Ghost extends Entity {
    private final static Image GHOST_IMAGE = new Image("res/ghostRed.png");

    public Ghost(double x, double y) {
        super(GHOST_IMAGE, x, y);
    }
}
