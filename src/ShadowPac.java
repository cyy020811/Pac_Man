import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2023
 *
 * Please enter your name below
 * Chi-Yuan Yang
 */
public class ShadowPac extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final static int DOT_COUNT = 121;
    private final static double STEP_SIZE = 3;
    private final Font LARGE_FONT = new Font("res/FSO8BITR.TTF", 64);
    private final Font MEDIUM_FONT = new Font("res/FSO8BITR.TTF", 24);
    private final Font SMALL_FONT = new Font("res/FSO8BITR.TTF", 20);
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private final Image HEART_IMAGE = new Image("res/heart.png");
    private boolean start;
    private boolean win;
    private boolean lose;
    private int frame;
    private int lives;
    private int score;
    private Player pacman;
    private Entity[] entities;
    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        start = false;
        win = false;
        lose = false;
        frame = 0;
        lives = 3;
        score = 0;
        pacman = null;
        entities = new Entity[270];
    }

    /**
     * Method used to read file and create objects (you can change
     * this method as you wish).
     */
    private void readCSV() {
        // Extract the csv file with a buffer reader
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("res/level0.csv"))){
            String line;
            while ((line = br.readLine()) != null){
                // Process the input
                String[] data = line.split(",");
                String type = data[0];
                double x = Double.parseDouble(data[1]);
                double y = Double.parseDouble(data[2]);
                // Create the entity based on its child class
                switch (type) {
                    case "Player":
                        pacman = new Player(x, y);
                        break;
                    case "Ghost":
                        entities[count++] = new Ghost(x, y);
                        break;
                    case "Wall":
                        entities[count++] = new Wall(x, y);
                        break;
                    case "Dot":
                        entities[count++] = new Dot(x, y);
                        break;
                    default:
                        break;
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.readCSV();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        frame++;
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        // Game Start
        if (!start) {
            LARGE_FONT.drawString(GAME_TITLE, 260, 250);
            MEDIUM_FONT.drawString("PRESS SPACE TO START", 320, 440);
            MEDIUM_FONT.drawString("USE ARROW KEYS TO MOVE", 320, 440 + 30);
        } else if (!win && !lose) {
            double x = pacman.getX();
            double y = pacman.getY();
            double heartX = 900;
            double heartY = 10;
            boolean canMove = true;
            boolean hitGhost = false;
            pacman.draw(frame);
            // Show current score and remaining lives
            SMALL_FONT.drawString("SCORE " + score, 25, 25);
            for (int i = 0; i < lives; i++) {
                HEART_IMAGE.drawFromTopLeft(heartX, heartY);
                heartX += 30;
            }
            // Draw all entities on to the screen
            for (Entity entity: entities) {
                entity.draw();
            }
            // Move the PacMan and rotate its direction
            if (input.isDown(Keys.DOWN)) {
                pacman.setRotation(90);
                y += STEP_SIZE;
            }
            if (input.isDown(Keys.UP)) {
                pacman.setRotation(-90);
                y -= STEP_SIZE;
            }
            if (input.isDown(Keys.RIGHT)) {
                pacman.setRotation(0);
                x += STEP_SIZE;
            }
            if (input.isDown(Keys.LEFT)) {
                pacman.setRotation(180);
                x -= STEP_SIZE;
            }
            // Test the new position for possible collisions
            Rectangle newPos = new Rectangle(x, y, pacman.getWidth(), pacman.getHeight());
            for (Entity entity: entities) {
                if (entity.getHitbox().intersects(newPos)) {
                    String className = entity.getClass().getName();
                    switch (className) {
                        // Increment the score by 10 when an uneaten dot is eaten
                        case "Dot":
                            Dot dot = (Dot)entity;
                            if (!dot.isEaten()) {
                                dot.eaten();
                                score += 10;
                            }
                            break;
                        // Cannot move if the new position is inside a wall
                        case "Wall":
                            canMove = false;
                            break;
                        // Lose a live when PacMan hits a ghost
                        case "Ghost":
                            hitGhost = true;
                            lives--;
                            break;
                        default:
                            break;
                    }
                }
            }
            // Test for win and lose conditions
            if(lives == 0) lose = true;
            if(score == DOT_COUNT * 10) win = true;
            // Return to the starting point after a collision with a ghost
            if (hitGhost) {
                pacman.move(x, y);
                pacman.returnStart();
            // Move to the next position
            } else if (canMove) {
                pacman.move(x, y);
            }
        }
        // Enter the game when space is pressed
        if (input.wasPressed(Keys.SPACE)) {
            start = true;
        }
        // Game winning message
        if (win) {
            double x = (Window.getWidth() - LARGE_FONT.getWidth("WELL DONE!"))/2.0;
            double y = Window.getHeight()/2.0;
            LARGE_FONT.drawString("WELL DONE!", x, y);
        }
        // Game losing message
        if (lose) {
            double x = (Window.getWidth() - LARGE_FONT.getWidth("GAME OVER!"))/2.0;
            double y = Window.getHeight()/2.0;
            LARGE_FONT.drawString("GAME OVER!", x, y);
        }
    }
}
