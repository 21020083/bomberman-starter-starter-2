package uet.oop.bomberman.entities.movingobject.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.movingobject.Bomber;
import uet.oop.bomberman.entities.movingobject.Enemy;
import uet.oop.bomberman.entities.movingobject.pathfinder.pathfinder;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;
import java.util.Random;

import static uet.oop.bomberman.BombermanGame.bomberman;

public class Oneal extends Enemy {
    private boolean searching = true;
    protected int count = 0;
    protected boolean bombDetected = false;
    protected pathfinder pfinder = new pathfinder(BombermanGame.Map);
    protected int moveaway = 0;
    public Oneal(int x, int y, Image img) {
        super(x, y, img);

        left.add(Sprite.oneal_left1.getFxImage());
        left.add(Sprite.oneal_left2.getFxImage());
        left.add(Sprite.oneal_left3.getFxImage());

        right.add(Sprite.oneal_right1.getFxImage());
        right.add(Sprite.oneal_right2.getFxImage());
        right.add(Sprite.oneal_right3.getFxImage());

        dead.add(Sprite.oneal_dead.getFxImage());
        dead.add(Sprite.mob_dead1.getFxImage());
        dead.add(Sprite.mob_dead2.getFxImage());
        dead.add(Sprite.mob_dead3.getFxImage());

        index = 0;
        AniCount = 0;
        Point = 200;
        move = Move.RIGHT;
        setSpeed(1);

    }
    public void update() {
        if(alive) {
            int disx = Math.abs((x - bomberman.getX())/Sprite.SCALED_SIZE);
            int disy = Math.abs((y-bomberman.getY())/Sprite.SCALED_SIZE);
            int distance = disx+disy;
            if(searching) {
                int centerX = (bomberman.getY()+12)/ Sprite.SCALED_SIZE;
                int centerY = (bomberman.getX()+12)/ Sprite.SCALED_SIZE;
                pfinder.setup(y/Sprite.SCALED_SIZE,x/Sprite.SCALED_SIZE, centerX,centerY);
            }
            if(distance < 4 && !bombDetected && pfinder.search()) {
                followBomber();
            } else {
                moveRandom();
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
    public void followBomber() {
            searching = false;
            int realX = pfinder.closedlist.get(0).col*Sprite.SCALED_SIZE;
            int realY = pfinder.closedlist.get(0).row*Sprite.SCALED_SIZE;
             if (realX == x) {
                 if(realY < y) {
                     move_up();
                 } else if(realY > y) {
                     move_down();
                 } else searching = true;
            } else if (realX > x) {
                 if(realY == y) {
                     move_right();
                 } else if(realY < y){
                     move_up();
                 } else move_down();
            } else{
                 if(realY == y) {
                     move_left();
                 } else if(realY < y){
                     move_up();
                 } else move_down();
            }
            if (CollisionwithBomb(BombermanGame.bomberman.boms)) {
                index = 0;
                bombDetected = true;
            }
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

    public void moveRandom() {
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
        if (CollisionwithWall(BombermanGame.Map)) {
            index = 0;
        }
        if(CollisionwithBomb(bomberman.boms)){
            index = 0;
        }
        count++;
        int dx = x / Sprite.SCALED_SIZE;
        int dy = y / Sprite.SCALED_SIZE;
        if (count >= Sprite.SCALED_SIZE * 9 && (dx * Sprite.SCALED_SIZE == x || dy * Sprite.SCALED_SIZE == y)) {
            move = Move.values()[new Random().nextInt(Move.values().length)];
            count = 0;
            bombDetected = false;
        }
    }

}
