import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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
    private final Font SIXTYFOUR_FONT = new Font("res/FSO8BITR.TTF", 64);
    private final Font FORTY_FONT = new Font("res/FSO8BITR.TTF", 40);
    private final Font TWENTYFOUR_FONT = new Font("res/FSO8BITR.TTF", 24);
    private final Font TWENTY_FONT = new Font("res/FSO8BITR.TTF", 20);
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private final Image HEART_IMAGE = new Image("res/heart.png");
    private boolean start;
    private boolean win;
    private boolean lose;
    private boolean levelComplete;
    private boolean frenzyMode = false;
    private int frame;
    private int lives;
    private int score;
    private int level;
    private int endFrame;
    private Player pacman;
    private ArrayList<Entity> entities;
    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        start = false;
        win = false;
        lose = false;
        levelComplete = false;
        frame = 0;
        lives = 3;
        score = 1200;
        level = 0;
        pacman = null;
        entities = new ArrayList<Entity>();
    }

    /**
     * Method used to read file and create objects (you can change
     * this method as you wish).
     */
    private void readCSV() {
        // Extract the csv file with a buffer reader
        try (BufferedReader br = new BufferedReader(new FileReader("res/level" + level + ".csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
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
                        entities.add(new Ghost(x, y, "Red"));
                        break;
                    case "Wall":
                        entities.add(new Wall(x, y));
                        break;
                    case "Dot":
                        entities.add(new Dot(x, y));
                        break;
                    case "Cherry":
                        entities.add(new Cherry(x, y));
                        break;
                    case "Pellet":
                        entities.add(new Pellet(x, y));
                        break;
                    default:
                        // Create ghosts based on their color
                        String color = type.substring(5);
                        entities.add(new Ghost(x, y, color));
                        break;
                }
            }
        }
        catch (IOException e) {
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
            // Different start screens for level 0 and 1
            if (level == 1) {
                int space = 36;
                FORTY_FONT.drawString("PRESS SPACE TO START", 200, 350);
                FORTY_FONT.drawString("USE ARROW KEYS TO MOVE", 200, 350 + space);
                FORTY_FONT.drawString("EAT THE PELLET TO ATTACK", 200, 350 + space * 2);
            } else {
                SIXTYFOUR_FONT.drawString(GAME_TITLE, 260, 250);
                TWENTYFOUR_FONT.drawString("PRESS SPACE TO START", 320, 440);
                TWENTYFOUR_FONT.drawString("USE ARROW KEYS TO MOVE", 320, 440 + 30);
            }
        } else if (!levelComplete && !lose) {
            double x = pacman.getX();
            double y = pacman.getY();
            double heartX = 900;
            double heartY = 10;
            boolean canMove = true;
            boolean hitGhost = false;
            pacman.draw(frame);
            // Show current score and remaining lives
            TWENTY_FONT.drawString("SCORE " + score, 25, 25);
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
            Iterator<Entity> iterator = entities.iterator();
            while (iterator.hasNext()) {
                Entity entity = iterator.next();
                if (entity.getHitbox().intersects(newPos)) {
                    String className = entity.getClass().getName();
                    switch (className) {
                        // Increment the score by 10 when an uneaten dot is eaten
                        case "Dot":
                            score += 10;
                            iterator.remove();
                            break;
                        // Begin frenzy mode
                        case "Pellet":
                            frenzyMode = true;
                            endFrame = frame + 1000;
                            pacman.setSpeed(4);
                            iterator.remove();
                            break;
                        // Cannot move if the new position is inside a wall
                        case "Wall":
                            canMove = false;
                            break;
                        // Lose a live when PacMan hits a ghost
                        case "Ghost":
                            hitGhost = true;
                            break;
                        default:
                            break;
                    }
                }
            }
            // Test for win and lose conditions
            if(lives == 0) lose = true;
            // Complete the level and show level complete messages
            if(score == DOT_COUNT * 10) {
                levelComplete = true;
                endFrame = frame + 300;
            }
            // Turn off frenzy mode
            if (frenzyMode && frame + 1 > endFrame ) {
                frenzyMode = false;
                pacman.setSpeed(3);
            }
            // Return to the starting point after a collision with a ghost
            if (hitGhost) {
                pacman.move(x, y);
                pacman.returnStart();
                lives--;
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
            double x = (Window.getWidth() - SIXTYFOUR_FONT.getWidth("WELL DONE!"))/2.0;
            double y = Window.getHeight()/2.0;
            SIXTYFOUR_FONT.drawString("WELL DONE!", x, y);
        }
        // Show level complete messages for 300 frames
        if (levelComplete) {
            if (frame + 1 > endFrame) {
                levelComplete = false;
                start = false;
                score = 0;
                entities.clear();
                level++;
                readCSV();
            }
            double x = (Window.getWidth() - SIXTYFOUR_FONT.getWidth("LEVEL COMPLETE!"))/2.0;
            double y = Window.getHeight()/2.0;
            SIXTYFOUR_FONT.drawString("LEVEL COMPLETE!", x, y);
        }
        // Game losing message
        if (lose) {
            double x = (Window.getWidth() - SIXTYFOUR_FONT.getWidth("GAME OVER!"))/2.0;
            double y = Window.getHeight()/2.0;
            SIXTYFOUR_FONT.drawString("GAME OVER!", x, y);
        }
    }
}
