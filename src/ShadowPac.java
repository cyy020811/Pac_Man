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
    private final static int LEVEL_0_GOAL = 1210;
    private final static int LEVEL_1_GOAL = 800;
    private final static int L1_MESSAGE_X = 200;
    private final static int L1_MESSAGE_Y = 350;
    private final static int L0_MESSAGE_X = 320;
    private final static int L0_MESSAGE_Y = 440;
    private final static int GAME_TITLE_X = 260;
    private final static int GAME_TITLE_Y = 250;
    private final static int HEART_X = 900;
    private final static int HEART_Y = 10;
    private final static int HEART_SPACE = 30;
    private final static int SCORE_COORD = 25;
    private final static  int INIT_LIVES = 3;
    private final static int INIT_LEVEL = 0;
    private final static int FINAL_LEVEL = 1;
    private final static int LEVEL_COMPLETE_FRAME = 300;
    private final static int FRENZY_MODE_FRAME = 1000;
    private final static int GHOST_COLOR_INDEX = 5;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final static String GAME_INSTRUCTIONS[] = {"PRESS SPACE TO START", "USE ARROW KEYS TO MOVE", "EAT THE PELLET TO ATTACK"};
    private final static String SCORE = "SCORE ";
    private final static String WIN_MESSAGE = "WELL DONE!";
    private final static String LOSE_MESSAGE = "GAME OVER!";
    private final static String LEVEL_COMPLETE = "LEVEL COMPLETE!";
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
    private int goal;
    private Player pacman;
    private ArrayList<Ghost> coloredGhosts;
    private ArrayList<Entity> entities;
    private ArrayList<Wall> walls;
    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        start = false;
        win = false;
        lose = false;
        levelComplete = false;
        frame = 0;
        lives = INIT_LIVES;
        score = 1200;
        goal = LEVEL_0_GOAL;
        level = INIT_LEVEL;
        pacman = null;
        entities = new ArrayList<>();
        coloredGhosts = new ArrayList<>();
        walls = new ArrayList<>();
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
                        Ghost ghost = new Ghost(x, y, "Red");
                        entities.add(ghost);
                        break;
                    case "Wall":
                        Wall wall = new Wall(x, y);
                        entities.add(wall);
                        walls.add(wall);
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
                        String color = type.substring(GHOST_COLOR_INDEX);
                        Ghost coloredGhost = new Ghost(x, y, color);
                        entities.add(coloredGhost);
                        coloredGhosts.add(coloredGhost);
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
            if (level == FINAL_LEVEL) {
                int space = 36;
                FORTY_FONT.drawString(GAME_INSTRUCTIONS[0], L1_MESSAGE_X, L1_MESSAGE_Y);
                FORTY_FONT.drawString(GAME_INSTRUCTIONS[1], L1_MESSAGE_X, L1_MESSAGE_Y + space);
                FORTY_FONT.drawString(GAME_INSTRUCTIONS[2], L1_MESSAGE_X, L1_MESSAGE_Y + space * 2);
            } else {
                SIXTYFOUR_FONT.drawString(GAME_TITLE, GAME_TITLE_X, GAME_TITLE_Y);
                TWENTYFOUR_FONT.drawString(GAME_INSTRUCTIONS[0], L0_MESSAGE_X, L0_MESSAGE_Y);
                TWENTYFOUR_FONT.drawString(GAME_INSTRUCTIONS[1], L0_MESSAGE_X, L0_MESSAGE_Y + 30);
            }
        } else if (!levelComplete && !lose) {
            double x = pacman.getX();
            double y = pacman.getY();
            boolean canMove = true;
            boolean hitGhost = false;
            pacman.draw(frame);
            // Show current score and remaining lives
            TWENTY_FONT.drawString(SCORE + score, SCORE_COORD, SCORE_COORD);
            for (int i = 0; i < lives; i++) {
                HEART_IMAGE.drawFromTopLeft(HEART_X + HEART_SPACE * i, HEART_Y);
            }
            // Draw all entities on to the screen
            for (Entity entity: entities) {
                entity.draw();
            }
            // Move the PacMan and rotate its direction
            if (input.isDown(Keys.DOWN)) {
                pacman.setRotation(90);
                y += pacman.getSpeed();
            }
            if (input.isDown(Keys.UP)) {
                pacman.setRotation(-90);
                y -= pacman.getSpeed();
            }
            if (input.isDown(Keys.RIGHT)) {
                pacman.setRotation(0);
                x += pacman.getSpeed();
            }
            if (input.isDown(Keys.LEFT)) {
                pacman.setRotation(180);
                x -= pacman.getSpeed();
            }
            // Move the colored ghosts
            for (Ghost ghost: coloredGhosts) {
                if (!ghost.isEaten()) ghost.move(walls);
            }
            // Test the new position for possible collisions
            Rectangle newPos = new Rectangle(x, y, pacman.getWidth(), pacman.getHeight());
            Iterator<Entity> iterator = entities.iterator();
            while (iterator.hasNext()) {
                Entity entity = iterator.next();
                if (entity.getHitbox().intersects(newPos)) {
                    String className = entity.getClass().getName();
                    switch (className) {
                        // Increment the score by 10 when a dot is eaten
                        case "Dot":
                            score += Dot.getScore();
                            iterator.remove();
                            break;
                        // Increment the score by 10 when a cherry is eaten
                        case "Cherry":
                            score += Cherry.getScore();
                            iterator.remove();
                            break;
                        // Begin frenzy mode
                        case "Pellet":
                            frenzyMode = true;
                            endFrame = frame + FRENZY_MODE_FRAME;
                            pacman.setSpeed(frenzyMode);
                            for (Ghost ghost: coloredGhosts) ghost.setFrenzyMode(frenzyMode);
                            iterator.remove();
                            break;
                        // Cannot move if the new position is inside a wall
                        case "Wall":
                            canMove = false;
                            break;
                        // Lose a live when PacMan hits a ghost
                        case "Ghost":
                            hitGhost = true;
                            Ghost ghost = (Ghost)entity;
                            ghost.returnStart();
                            if (frenzyMode) {
                                if (!ghost.isEaten()) score += Ghost.getScore();
                                ghost.setEaten(true);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            // Test for win and lose conditions
            if(lives == 0) lose = true;
            // Complete the level and show level complete messages
            if(score >= goal) {
                if (level == FINAL_LEVEL) win = true;
                levelComplete = true;
                endFrame = frame + LEVEL_COMPLETE_FRAME;
            }
            // Turn off frenzy mode
            if (frenzyMode && frame + 1 > endFrame ) {
                frenzyMode = false;
                pacman.setSpeed(frenzyMode);
                for (Ghost ghost: coloredGhosts) ghost.setFrenzyMode(frenzyMode);
            }
            // Return to the starting point after a collision with a ghost or gain points during frenzy mode
            if (hitGhost) {
                pacman.move(x, y);
                if (!frenzyMode) {
                    pacman.returnStart();
                    lives--;
                }
            } else if (canMove) {
                // Move to the next position
                pacman.move(x, y);
            }
        }
        // Enter the game when space is pressed
        if (input.wasPressed(Keys.SPACE)) {
            start = true;
        }
        // Game winning message
        if (win) {
            double x = (Window.getWidth() - SIXTYFOUR_FONT.getWidth(WIN_MESSAGE))/2.0;
            double y = Window.getHeight()/2.0;
            SIXTYFOUR_FONT.drawString(WIN_MESSAGE, x, y);
        }
        // Show level complete messages for 300 frames
        if (levelComplete && !win) {
            if (frame + 1 > endFrame) {
                levelComplete = false;
                start = false;
                score = 0;
                goal = LEVEL_1_GOAL;
                entities.clear();
                walls.clear();
                level++;
                readCSV();
            }
            double x = (Window.getWidth() - SIXTYFOUR_FONT.getWidth(LEVEL_COMPLETE))/2.0;
            double y = Window.getHeight()/2.0;
            SIXTYFOUR_FONT.drawString(LEVEL_COMPLETE, x, y);
        }
        // Game losing message
        if (lose) {
            double x = (Window.getWidth() - SIXTYFOUR_FONT.getWidth(LOSE_MESSAGE))/2.0;
            double y = Window.getHeight()/2.0;
            SIXTYFOUR_FONT.drawString(LOSE_MESSAGE, x, y);
        }
    }
}
