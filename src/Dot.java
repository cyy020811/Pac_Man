import bagel.Image;

public class Dot extends Entity {
    private final static Image DOT_IMAGE = new Image("res/dot.png");

    public Dot(double x, double y) {
        super(DOT_IMAGE, x, y);
    }
}
