import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

public class Ghost extends Entity {
    private final static Image FRENZY_MODE_IMAGE = new Image("res/ghostFrenzy.png");
    private final static int score = 30;
    private Image coloredGhostImage;
    private boolean isEaten;
    private double speed;
    private Direction direction;
    private String color;
    private Point startPos;


    /** This method is used to create a Ghost instance
     * @param x this is the x position of the Ghost
     * @param y this is the y position of the Ghost
     * @param color this is the color of the Ghost
     */
    public Ghost(double x, double y, String color) {
        super(new Image("res/ghost" + color + ".png"), x, y);
        this.color = color;
        coloredGhostImage = new Image("res/ghost" + color + ".png");
        startPos = new Point(x, y);
        isEaten = false;
        Random rand = new Random();
        // Assign one image and one direction to the ghost based on the color
        switch (color) {
            case "Red":
                speed = 1;
                direction = Direction.RIGHT;
                break;
            case "Blue":
                speed = 2;
                direction = Direction.DOWN;
                break;
            case "Green":
                speed = 4;
                // Randomly select vertical or horizontal
                if (rand.nextInt(2) == 1);
                direction = Direction.values()[(rand.nextInt(2) == 1) ? 1 : 3];
                break;
            case "Pink":
                speed = 3;
                // Randomly select Up/Down/Left/Right
                direction = Direction.values()[rand.nextInt(4)];
                break;
            default:
                break;
        }
    }

    /** This method is used to move the Ghost to the new
     * position
     * @param walls all the walls at the current level
     */
    public void move(ArrayList<Wall> walls) {
        double x = getX();
        double y = getY();
        boolean collide = false;
        switch (direction) {
            case RIGHT:
                x += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            default:
                break;
        }
        // Check if the new position intersects with a wall
        collide = collides(walls, x, y);
        // Change direction if the ghost hits the wall, else move
        if (collide) {
            setDirection();
        } else {
            setX(x);
            setY(y);
            getHitbox().moveTo(new Point(x, y));
        }
    }

    /** This method is used to change the Ghost's direction
     */
    public void setDirection() {
        // Randomly select a direction for pink ghost
        if (color.equals("Pink")) {
            Random rand = new Random();
            direction = Direction.values()[rand.nextInt(4)];
        } else {
            switch (direction) {
                case UP:
                    direction = Direction.DOWN;
                    break;
                case DOWN:
                    direction = Direction.UP;
                    break;
                case LEFT:
                    direction = Direction.RIGHT;
                    break;
                case RIGHT:
                    direction = Direction.LEFT;
                    break;
                default:
                    break;
            }
        }
    }

    /** This method is used to check if the Ghost hits
     * a wall
     * @param walls all the walls at the current level
     * @param x the x-coordinate of the Ghost
     * @param y the y-coordinate of the Ghost
     * @return boolean if the Ghost hits a wall
     */
    private boolean collides(ArrayList<Wall> walls, double x, double y) {
        Rectangle newPos = new Rectangle(x, y, this.getWidth(), this.getHeight());
        for (Wall wall: walls) {
            if (newPos.intersects(wall.getHitbox())) return true;
        }
        return false;
    }

    /** Return to the starting position
     */
    public void returnStart() {
        setX(startPos.x);
        setY(startPos.y);
        getHitbox().moveTo(startPos);
    }

    /** Set the Ghost's image and speed for frenzy mode and switch it back afterwards
     * @param frenzyMode the frenzy mode
     */
    public void setFrenzyMode(boolean frenzyMode) {
        if (frenzyMode) {
            super.setImage(FRENZY_MODE_IMAGE);
            speed -= 0.5;
        } else {
            super.setImage(coloredGhostImage);
            isEaten = false;
            speed += 0.5;
        }
    }

    /** Set the Ghost's state
     * @param eaten if the ghost is eaten
     */
    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }

    /** Get the eaten state of the Ghost
     * @return boolean the eaten state of the Ghost
     */
    public boolean isEaten() {
        return isEaten;
    }

    /** Draw the image from top-left if the Ghost is not eaten
     */
    @Override
    public void draw() {
        if (!isEaten) super.draw();
    }

    /** This method is used to return the score of a Ghost
     * @return int the score
     */
    public static int getScore() {
        return score;
    }
}
