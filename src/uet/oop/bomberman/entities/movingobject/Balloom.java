package uet.oop.bomberman.entities.movingobject;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;


import java.util.ArrayList;
import java.util.Random;

public class Balloom extends MovingObject {
    private final ArrayList<Image> left  = new ArrayList<>();
    private final ArrayList<Image> right = new ArrayList<>();
    private int index;
    private int AniCount;
    private long curTime;
    private long endTime;

    public Balloom(int x, int y, Image img) {
        super(x, y, img);
        left.add(Sprite.balloom_left1.getFxImage());
        left.add(Sprite.balloom_left2.getFxImage());
        left.add(Sprite.balloom_left3.getFxImage());

        right.add(Sprite.balloom_right1.getFxImage());
        right.add(Sprite.balloom_right2.getFxImage());
        right.add(Sprite.balloom_right3.getFxImage());

        index = 0;
        AniCount = 0;
        move = Move.RIGHT;
        setSpeed(2);
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
        endTime = System.currentTimeMillis();
        if((endTime-curTime)/1000 > 3){
            move = Move.values()[new Random().nextInt(Move.values().length)];
            curTime = endTime;
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
                    move = Move.DOWN;
                    break;
                case DOWN:
                    y --;
                    move = Move.UP;
                    break;
                case RIGHT:
                    x--;
                    move = Move.LEFT;
                    break;
                case LEFT:
                    x++;
                    move = Move.RIGHT;
                    break;
            }
            return true;
        }
        return false;
    }
}
