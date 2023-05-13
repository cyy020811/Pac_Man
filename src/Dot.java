import bagel.Image;

public class Dot extends Entity {
    private final static Image DOT_IMAGE = new Image("res/dot.png");
    private final static int score = 10;

    /** This method is used to create a Dot instance
     * @param x this is the x position of the Dot
     * @param y this is the y position of the Dot
     */
    public Dot(double x, double y) {
        super(DOT_IMAGE, x, y);
    }

    /** This method is used to return the score of a Dot
     * @return int the score
     */
    public static int getScore() {
        return score;
    }
}
