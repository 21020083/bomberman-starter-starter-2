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

        index = 0;
        AniCount = 0;
        move = Move.RIGHT;
        setSpeed(1);

    }
    public void update() {
        if(alive) {
            int disx = Math.abs((x - bomberman.getX())/Sprite.SCALED_SIZE);
            int disy = Math.abs((y-bomberman.getY())/Sprite.SCALED_SIZE);
            int distance = disx+disy;
            if(searching) {
                pfinder.setup(y/Sprite.SCALED_SIZE,x/Sprite.SCALED_SIZE, bomberman.getY()/
                        Sprite.SCALED_SIZE, bomberman.getX()/Sprite.SCALED_SIZE);
            }
            if(distance < 6 && !bombDetected && pfinder.search()) {
                detectBomb(bomberman.boms);
                followBomber();
            } else {
                moveRandom();
                detectBomb(bomberman.boms);
            }
        } else {
            AniCount++;
            setImg(Sprite.oneal_dead.getFxImage());
            if(AniCount > 30)
                death = true;
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
            }
    }
    public void detectBomb(List<Bomb> bombs){
        for(Bomb b : bombs)
        {
            int dx = Math.abs((x-b.getX())/Sprite.SCALED_SIZE);
            int dy = Math.abs((y-b.getY())/Sprite.SCALED_SIZE);
            if(dy + dx < 4) {
                bombDetected = true;
                if(dx == 0) {
                    if(b.getY() < y) {
                        move = Move.UP;
                    } else if(b.getY() > y) {
                        move = Move.DOWN;
                    }
                } else if(dy == 0) {
                    if(b.getX() < x) {
                        move = Move.RIGHT;
                    } else if (b.getX() > x) {
                        move = Move.LEFT;
                    }
                }
                Moveaway();
                break;
            } else {
                bombDetected = false;
            }
        }
    }
    public void Moveaway() {
        switch (move) {
            case UP:
                y-=speed;
                break;
            case DOWN:
                y+=speed;
                break;
            case LEFT:
                x-=speed;
                break;
            case RIGHT:
                x+=speed;
                break;
        }
        if (CollisionwithWall(BombermanGame.Map) && CollisionwithWall()) {
            index = 0;
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
        }
    }

}
