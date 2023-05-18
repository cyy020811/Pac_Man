import bagel.Image;

/** Represents a cherry that can be eaten by the player.
 * @author Chi-Yuan Yang
 * @version 1.0
 */
public class Cherry extends Entity {
    private final static Image CHERRY_IMAGE = new Image("res/cherry.png");
    private final static int score = 20;

    /** This method is used to create a Cherry instance
     * @param x this is the x position of the Cherry
     * @param y this is the y position of the Cherry
     */
    public Cherry(double x, double y) {
        super(CHERRY_IMAGE, x, y);
    }

    /** This method is used to return the score of a Cherry
     * @return int the score
     */
    public static int getScore() {
        return score;
    }
}
