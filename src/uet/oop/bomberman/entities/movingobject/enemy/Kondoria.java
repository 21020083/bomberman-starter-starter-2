package uet.oop.bomberman.entities.movingobject.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.entities.movingobject.Enemy;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Random;

public class Kondoria extends Enemy {
    private long curTime;
    private long endTime;



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
        if(alive) {
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
            if (CollisionwithWall()) {
                index = 0;
            }
            if (CollisionwithWall(BombermanGame.Map)) {
                index = 0;
            }
            if(CollisionwithBomb(BombermanGame.bomberman.boms)){
                index = 0;
            }
            endTime = System.currentTimeMillis();
            count++;
            int dx = x / Sprite.SCALED_SIZE;
            int dy = y / Sprite.SCALED_SIZE;
            if (count >= Sprite.SCALED_SIZE * 9 && (dx * Sprite.SCALED_SIZE == x || dy * Sprite.SCALED_SIZE == y)) {
                move = Move.values()[new Random().nextInt(Move.values().length)];
                count = 0;
            }
        } else {
            AniCount++;
            setImg(Sprite.kondoria_dead.getFxImage());
            if(AniCount > 30)
                death = true;
        }

    }
}
