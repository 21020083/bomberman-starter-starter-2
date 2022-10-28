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


    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
        left.add(Sprite.kondoria_left1.getFxImage());
        left.add(Sprite.kondoria_left2.getFxImage());
        left.add(Sprite.kondoria_left3.getFxImage());


        right.add(Sprite.kondoria_right1.getFxImage());
        right.add(Sprite.kondoria_right2.getFxImage());
        right.add(Sprite.kondoria_right3.getFxImage());

        dead.add(Sprite.kondoria_dead.getFxImage());
        dead.add(Sprite.mob_dead1.getFxImage());
        dead.add(Sprite.mob_dead2.getFxImage());
        dead.add(Sprite.mob_dead3.getFxImage());

        index = 0;
        AniCount = 0;
        move = Move.RIGHT;
        setSpeed(1);
        Point = 1000;
        curTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if (alive) {
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
            if(CollisionwithWall(BombermanGame.Map)) {
                index = 0;
            }
            if (CollisionwithBomb(BombermanGame.bomberman.boms)) {
                index = 0;
            }
            endTime = System.currentTimeMillis();
            if ((endTime - curTime) / 1000 > 3) {
                move = Move.values()[new Random().nextInt(Move.values().length)];
                curTime = endTime;
            }
        } else {
            AniCount++;
            if (AniCount < 9) setImg(dead.get(0));
            else if (AniCount < 18) setImg(dead.get(1));
            else if (AniCount < 27) setImg(dead.get(2));
            else if (AniCount < 36) setImg(dead.get(3));
            else {
                death = true;
                BombermanGame.Score+= Point;
            }
        }
    }
    public  boolean CollisionwithWall(int[][] Map) {
        int topleftX = (x+2)/Sprite.SCALED_SIZE;
        int topleftY = (y+2)/Sprite.SCALED_SIZE;

        int toprightX = (x + Sprite.DEFAULT_SIZE + 14)/Sprite.SCALED_SIZE;
        int toprightY = (y+2)/Sprite.SCALED_SIZE;

        int bottomleftX = (x+3)/Sprite.SCALED_SIZE;
        int bottomleftY = (y + Sprite.DEFAULT_SIZE + 14)/Sprite.SCALED_SIZE;

        int bottomrightX = (x + Sprite.DEFAULT_SIZE + 14)/Sprite.SCALED_SIZE;
        int bottomrightY = (y + Sprite.DEFAULT_SIZE + 14)/Sprite.SCALED_SIZE;


        if(Map[topleftY][topleftX] < 1|| Map[bottomrightY][bottomrightX] < 1 ||
                Map[toprightY][toprightX] < 1 || Map[bottomleftY][bottomleftX] < 1){
            count = 0;
            switch (move) {
                case UP:
                    y+=speed;
                    move = Move.values()[new Random().nextInt(Move.values().length)];
                    break;
                case DOWN:
                    y-=speed;
                    move = Move.values()[new Random().nextInt(Move.values().length)];
                    break;
                case RIGHT:
                    x-=speed;
                    move = Move.values()[new Random().nextInt(Move.values().length)];
                    break;
                case LEFT:
                    x+=speed;
                    move = Move.values()[new Random().nextInt(Move.values().length)];
                    break;
            }
            return true;
        }
        return false;
    }
}
