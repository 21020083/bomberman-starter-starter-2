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

        dead.add(Sprite.doll_dead.getFxImage());
        dead.add(Sprite.mob_dead1.getFxImage());
        dead.add(Sprite.mob_dead2.getFxImage());
        dead.add(Sprite.mob_dead3.getFxImage());

        index = 0;
        AniCount = 0;
        move = Move.RIGHT;
        rand = new Random();
        Point = 500;
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
}
