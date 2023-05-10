import bagel.Image;

public class Cherry extends Entity {
    private final static Image CHERRY_IMAGE = new Image("res/cherry.png");
    private final static int score = 20;
    public Cherry(double x, double y) {
        super(CHERRY_IMAGE, x, y);
    }

    public static int getScore() {
        return score;
    }
}
