import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class Entity {
    private Image image;
    private Rectangle hitbox;
    private double x;
    private double y;
    private double width;
    private double height;

    /** This method is used to create an Entity instance
     * @param image this is the image of the Entity
     * @param x this is the x position of the Entity
     * @param y this is the y position of the Entity
     */
    public Entity(Image image, double x, double y) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.hitbox = new Rectangle(x, y, width, height);
        this.x = x;
        this.y = y;
    }

    /** Draw the image from top-left
     */
    public void draw() {
        image.drawFromTopLeft(x, y);
    }

    /** Get the top left point of the hitbox
     * @return Point the top left point
     */
    public Point topLeft() {
        return hitbox.topLeft();
    }

    /** Get the x-coordinate of the entity
     * @return double the x-coordinate
     */
    public double getX() {
        return x;
    }

    /** Get the y-coordinate of the entity
     * @return double the y-coordinate
     */
    public double getY() {
        return y;
    }

    /** Set the x-coordinate of the entity
     * @param x the new x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /** Set the y-coordinate of the entity
     * @param y the new y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /** Get the width of the entity
     * @return double the width
     */
    public double getWidth() {
        return width;
    }

    /** Get the height of the entity
     * @return double the height
     */
    public double getHeight(){
        return height;
    }

    /** Get the hitbox of the entity
     * @return Rectangle the hitbox
     */
    public Rectangle getHitbox() {
        return hitbox;
    }

    /** Set the image of the entity
     * @param image the new image
     */
    public void setImage(Image image) {
        this.image = image;
    }
}
