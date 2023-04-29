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

    public Entity(Image image, double x, double y) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.hitbox = new Rectangle(x, y, width, height);
        this.x = x;
        this.y = y;
    }

    // Draw the image from top-left
    public void draw(){
        image.drawFromTopLeft(x, y);
    }

    public Point topLeft(){
        return hitbox.topLeft();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight(){
        return height;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
    public void setImage(Image image) {
        this.image = image;
    }
}
