package uet.oop.bomberman.entities.movingobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Random;

public class Kondoria extends MovingObject{
    private final ArrayList<Image> left  = new ArrayList<>();
    private final ArrayList<Image> right = new ArrayList<>();
    private int index;
    private int AniCount;
    private long curTime;
    private long endTime;
    private int count = 0;


    int leftX = x - Sprite.SCALED_SIZE;
    int leftY = y;

    int rightX = x + Sprite.SCALED_SIZE;
    int rightY = y;

    int upX = x;
    int upY = y - Sprite.SCALED_SIZE;

    int downX = x;
    int downY = y + Sprite.SCALED_SIZE;

    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
        left.add(Sprite.kondoria_left1.getFxImage());
        left.add(Sprite.kondoria_left2.getFxImage());
        left.add(Sprite.kondoria_left3.getFxImage());

        right.add(Sprite.kondoria_right1.getFxImage());
        right.add(Sprite.kondoria_right2.getFxImage());
        right.add(Sprite.kondoria_right3.getFxImage());

        index = 0;
        AniCount = 0;
        move = Move.RIGHT;
        setSpeed(1);
        curTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        switch (move) {
            case UP:
                move_up();
                break;
            case DOWN:
                move_down();
                break;
            case LEFT:
                move_left();
                break;
            case RIGHT:
                move_right();
                break;
        }
        if(CollisionwithWall()) {
            index = 0;
        }
        if(CollisionwithWall(BombermanGame.Map)) {
            index = 0;

        }

        endTime = System.currentTimeMillis();
        count ++;
       // if(count >= Sprite.SCALED_SIZE * 5){
           // move = Move.values()[new Random().nextInt(Move.values().length)];
           // curTime = endTime;
            //count = 0;
        //}

    }


    @Override
    public void move_up() {
        y--;
        move = Move.UP;
        setImg(left.get(index));

    }
    public void move_down() {
        y++;
        move = Move.DOWN;
        setImg(right.get(index));
    }
    public void move_left() {
        x--;
        move = Move.LEFT;
        setImg(left.get(index));
    }
    public void move_right() {
        x++;
        move = Move.RIGHT;
        setImg(right.get(index));
    }
    public void render(GraphicsContext gc) {

        AniCount++;
        if (AniCount > 9) {
            if (index >= 2)
                index = 0;
            else {
                index++;
            }
            AniCount = 0;
        }
        if(move == Move.UP || move == Move.LEFT) {
            setImg(left.get(index));
        } else {
            setImg(right.get(index));
        }
        gc.drawImage(img, x, y);
    }
    public  boolean CollisionwithWall() {
        if(x <= Sprite.SCALED_SIZE || y <= Sprite.SCALED_SIZE || x >= (BombermanGame.WIDTH - 2) *
                Sprite.SCALED_SIZE || y >= (BombermanGame.HEIGHT - 2) * Sprite.SCALED_SIZE) {
            switch (move) {
                case UP:
                    y ++;
                    move = Move.LEFT;
                    break;
                case DOWN:
                    y --;
                    move = Move.RIGHT;
                    break;
                case RIGHT:
                    x--;
                    move = Move.UP;
                    break;
                case LEFT:
                    x++;
                    move = Move.DOWN;
                    break;
            }
            return true;
        }
        return false;
    }
    public  boolean CollisionwithWall(char[][] Map) {
        int topleftX = (x+6)/Sprite.SCALED_SIZE;
        int topleftY = (y+6)/Sprite.SCALED_SIZE;

        int toprightX = (x + Sprite.DEFAULT_SIZE + 6)/Sprite.SCALED_SIZE;
        int toprightY = (y+6)/Sprite.SCALED_SIZE;

        int bottomleftX = (x+6)/Sprite.SCALED_SIZE;
        int bottomleftY = (y + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;

        int bottomrightX = (x + Sprite.DEFAULT_SIZE + 6)/Sprite.SCALED_SIZE;
        int bottomrightY = (y + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;


        if(Map[topleftY][topleftX] == '#' || Map[toprightY][toprightX] == '#' ||
                Map[bottomrightY][bottomrightX] == '#' || Map[bottomleftY][bottomleftX] == '#'||
        Map[topleftY][topleftX] == '*' || Map[toprightY][toprightX] == '*' ||
                Map[bottomrightY][bottomrightX] == '*' || Map[bottomleftY][bottomleftX] == '*'){
            count = 0;
            switch (move) {
                case UP:
                    y++;
                    move_left();
                    break;
                case DOWN:
                    y--;
                    move_right();
                    break;
                case RIGHT:
                    x--;
                    move_up();
                    break;
                case LEFT:
                    x++;
                    move_down();
                    break;
            }
            return true;
        }
        return false;
    }
}
