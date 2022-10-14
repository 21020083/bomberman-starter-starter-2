package uet.oop.bomberman.entities.movingobject.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movingobject.Enemy;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Doll extends Enemy {
    private Random rand ;
    public Doll(int x, int y, Image img) {
        super(x, y, img);
        left.add(Sprite.doll_left1.getFxImage());
        left.add(Sprite.doll_left2.getFxImage());
        left.add(Sprite.doll_left3.getFxImage());


        right.add(Sprite.doll_right1.getFxImage());
        right.add(Sprite.doll_right2.getFxImage());
        right.add(Sprite.doll_right3.getFxImage());



        index = 0;
        AniCount = 0;
        move = Move.RIGHT;
        rand = new Random();
        setSpeed(1);
    }
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
            count++;
            int dx = x / Sprite.SCALED_SIZE;
            int dy = y / Sprite.SCALED_SIZE;

            if(count >= Sprite.SCALED_SIZE) {
                int randNum = rand.nextInt(100);
                if(randNum < 33){
                    setSpeed(1);
                } else if (randNum < 67) {
                    setSpeed(1);
                } else if( randNum < 80){
                    setSpeed(2);
                } else {
                    setSpeed(3);
                }
            }
            if (count >= Sprite.SCALED_SIZE * 9 && (dx * Sprite.SCALED_SIZE == x || dy * Sprite.SCALED_SIZE == y)) {
                move = Move.values()[new Random().nextInt(Move.values().length)];
                count = 0;
            }
        } else {
            AniCount++;
            setImg(Sprite.doll_dead.getFxImage());
            if(AniCount > 30)
                death = true;
        }

    }
    public  boolean CollisionwithWall(int[][] Map) {
        int topleftX = (x+3)/Sprite.SCALED_SIZE;
        int topleftY = (y+3)/Sprite.SCALED_SIZE;

        int toprightX = (x + Sprite.SCALED_SIZE-3)/Sprite.SCALED_SIZE;
        int toprightY = (y+3)/Sprite.SCALED_SIZE;

        int bottomleftX = (x+3)/Sprite.SCALED_SIZE;
        int bottomleftY = (y + Sprite.SCALED_SIZE-3)/Sprite.SCALED_SIZE;

        int bottomrightX = (x + Sprite.SCALED_SIZE-3)/Sprite.SCALED_SIZE;
        int bottomrightY = (y + Sprite.SCALED_SIZE-3)/Sprite.SCALED_SIZE;


        if(Map[topleftY][topleftX] != 1|| Map[bottomrightY][bottomrightX] != 1 ||
                Map[toprightY][toprightX] != 1 || Map[bottomleftY][bottomleftX] != 1){
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
    public  boolean CollisionwithWall() {
        if(x < Sprite.SCALED_SIZE || y < Sprite.SCALED_SIZE || x > (BombermanGame.WIDTH - 2) *
                Sprite.SCALED_SIZE || y > (BombermanGame.HEIGHT - 2) * Sprite.SCALED_SIZE) {
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
