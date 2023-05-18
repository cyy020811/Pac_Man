import bagel.Image;

/** Represents a dot that can be eaten by the player.
 * @author Chi-Yuan Yang
 * @version 1.0
 */
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
