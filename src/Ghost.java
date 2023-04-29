import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.Random;

enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

public class Ghost extends Entity {
    private final static Image FRENZY_MODE_IMAGE = new Image("res/ghostFrenzy.png");
    private Image coloredGhostImage;
    private boolean isEaten;
    private double speed;
    private Direction direction;
    private String color;
    private Point startPos;
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

    // Move the ghost to its new position
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

    // Change the ghost's direction
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

    // Test if the ghost collides with a wall
    private boolean collides(ArrayList<Wall> walls, double x, double y) {
        Rectangle newPos = new Rectangle(x, y, this.getWidth(), this.getHeight());
        for (Wall wall: walls) {
            if (newPos.intersects(wall.getHitbox())) return true;
        }
        return false;
    }

    // Return to the starting position
    public void returnStart(){
        setX(startPos.x);
        setY(startPos.y);
        getHitbox().moveTo(startPos);
    }

    // Set the ghost image for frenzy mode and switch it back afterwards
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

    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }

    public boolean isEaten() {
        return isEaten;
    }

    @Override
    public void draw() {
        if (!isEaten) super.draw();
    }
}
