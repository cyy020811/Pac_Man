import bagel.Image;

public class Ghost extends Entity {
    private final static Image FRENZY_MODE_IMAGE = new Image("res/ghostFrenzy.png");
    private double speed;
    public Ghost(double x, double y, String color) {
        super(new Image("res/ghost" + color + ".png"), x, y);
        switch (color) {
            case "Red":
                speed = 1;
                break;
            case "Blue":
                speed = 2;
                break;
            case "Green":
                speed = 4;
                break;
            case "Pink":
                speed = 3;
                break;
            default:
                break;
        }
    }

    public void move() {

    }
}
