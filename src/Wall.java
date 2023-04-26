import bagel.Image;

public class Wall extends Entity {
    private final static Image WALL_IMAGE = new Image("res/wall.png");

    public Wall(double x, double y) {
        super(WALL_IMAGE, x, y);
    }
}
