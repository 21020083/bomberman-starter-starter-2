package uet.oop.bomberman.entities.movingobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
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
        int dx = x/Sprite.SCALED_SIZE;
        int dy = y / Sprite.SCALED_SIZE;
        if(count >= Sprite.SCALED_SIZE * 3 && (dx * Sprite.SCALED_SIZE == x || dy * Sprite.SCALED_SIZE ==y)){
            move = Move.values()[new Random().nextInt(Move.values().length)];
            curTime = endTime;
            count = 0;
        }

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
    public  boolean CollisionwithWall(Entity[][] Map) {
        int topleftX = (x+6)/Sprite.SCALED_SIZE;
        int topleftY = (y+6)/Sprite.SCALED_SIZE;

        int toprightX = (x + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;
        int toprightY = (y+6)/Sprite.SCALED_SIZE;

        int bottomleftX = (x+6)/Sprite.SCALED_SIZE;
        int bottomleftY = (y + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;

        int bottomrightX = (x + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;
        int bottomrightY = (y + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;


        if(Map[topleftY][topleftX] instanceof Wall || Map[toprightY][toprightX] instanceof Wall||
                Map[bottomrightY][bottomrightX] instanceof Wall|| Map[bottomleftY][bottomleftX]instanceof Wall||
        Map[topleftY][topleftX] instanceof Brick || Map[toprightY][toprightX] instanceof Brick ||
                Map[bottomrightY][bottomrightX] instanceof Brick|| Map[bottomleftY][bottomleftX] instanceof Brick){
            count = 0;
            switch (move) {
                case UP:
                    y++;
                    move = Move.values()[new Random().nextInt(Move.values().length)];
                    break;
                case DOWN:
                    y--;
                    move = Move.values()[new Random().nextInt(Move.values().length)];
                    break;
                case RIGHT:
                    x--;
                    move = Move.values()[new Random().nextInt(Move.values().length)];
                    break;
                case LEFT:
                    x++;
                    move = Move.values()[new Random().nextInt(Move.values().length)];
                    break;
            }
            return true;
        }
        return false;
    }
}
