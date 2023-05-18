import bagel.Image;

/** Represents a pellet that can be eaten by the player.
 * @author Chi-Yuan Yang
 * @version 1.0
 */
public class Pellet extends Entity {
    private final static Image PELLET_IMAGE = new Image("res/pellet.png");

    /** This method is used to create a Pellet instance
     * @param x this is the x position of the Pellet
     * @param y this is the y position of the Pellet
     */
    public Pellet(double x, double y) {
        super(PELLET_IMAGE, x, y);
    }
}
