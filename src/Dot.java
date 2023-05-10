import bagel.Image;

public class Dot extends Entity {
    private final static Image DOT_IMAGE = new Image("res/dot.png");
    private final static int score = 10;
    public Dot(double x, double y) {
        super(DOT_IMAGE, x, y);
    }

    public static int getScore() {
        return score;
    }
}
