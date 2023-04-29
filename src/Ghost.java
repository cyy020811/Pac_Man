import bagel.Image;

public class Ghost extends Entity {
    private final static Image GHOST_IMAGE = new Image("res/ghostRed.png");
    private final static Image FRENZY_MODE_IMAGE = new Image("res/ghostFrenzy.png");
    public Ghost(double x, double y, String imagePath) {
        super(new Image(imagePath), x, y);
    }
}
