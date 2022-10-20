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
    private int count = 0;
    private boolean bombDetected = false;
    private pathfinder pfinder = new pathfinder(BombermanGame.Map);
    private int moveaway = 0;
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
        pfinder.setup(y,x, bomberman.getY()/
                Sprite.SCALED_SIZE, bomberman.getX()/Sprite.SCALED_SIZE);
    }
    public void update() {
        if(alive) {
            if(!bombDetected) {
                detectBomb(bomberman.boms);
                if(pfinder.search()) {
                    followBomber(bomberman);
                } else {
                    moveRandom();
                }
            } else {
                moveaway++;
                if(moveaway > 120) {
                    bombDetected = false;
                    moveaway = 0;
                    int dx = y / Sprite.SCALED_SIZE;
                    int dy = x / Sprite.SCALED_SIZE;
                    int topleftX = (bomberman.getX() + 6) / Sprite.SCALED_SIZE;
                    int topleftY = (bomberman.getY() + 6) / Sprite.SCALED_SIZE;
                    pfinder.setup(dx, dy, topleftY, topleftX);;
                }
                Moveaway();

            }
        } else {
            AniCount++;
            setImg(Sprite.oneal_dead.getFxImage());
            if(AniCount > 30)
                death = true;
        }

    }
    public void followBomber(Bomber bomberman) {
        int disx = Math.abs((x - bomberman.getX())/Sprite.SCALED_SIZE);
        int disy = Math.abs((y-bomberman.getY())/Sprite.SCALED_SIZE);
        int distance = disx+disy;
        if(distance < 10) {
            int realX = pfinder.closedlist.get(0).col * Sprite.SCALED_SIZE;
            int realY = pfinder.closedlist.get(0).row * Sprite.SCALED_SIZE;
            if (realX == x && realY == y) {
                int dx = y / Sprite.SCALED_SIZE;
                int dy = x / Sprite.SCALED_SIZE;
                int topleftX = (bomberman.getX() + 6) / Sprite.SCALED_SIZE;
                int topleftY = (bomberman.getY() + 6) / Sprite.SCALED_SIZE;
                pfinder.setup(dx, dy, topleftY, topleftX);
                if (!pfinder.search()) {
                    alive = false;
                }
            } else if (realX < x) {
                move_left();
            } else if (realX > x) {
                move_right();
            } else if (realY > y) {
                move_down();
            } else {
                move_up();
            }
            if (CollisionwithBomb(BombermanGame.bomberman.boms)) {
                index = 0;
            }
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
    public  boolean CollisionwithWall(int[][] Map) {
        int topleftX = (x+2)/Sprite.SCALED_SIZE;
        int topleftY = (y+2)/Sprite.SCALED_SIZE;

        int toprightX = (x + Sprite.DEFAULT_SIZE + 14)/Sprite.SCALED_SIZE;
        int toprightY = (y+2)/Sprite.SCALED_SIZE;

        int bottomleftX = (x+3)/Sprite.SCALED_SIZE;
        int bottomleftY = (y + Sprite.DEFAULT_SIZE + 14)/Sprite.SCALED_SIZE;

        int bottomrightX = (x + Sprite.DEFAULT_SIZE + 14)/Sprite.SCALED_SIZE;
        int bottomrightY = (y + Sprite.DEFAULT_SIZE + 14)/Sprite.SCALED_SIZE;


        if(Map[topleftY][topleftX] != 1|| Map[bottomrightY][bottomrightX] != 1 ||
                Map[toprightY][toprightX] != 1 || Map[bottomleftY][bottomleftX] != 1){
            count = 0;
            switch (move) {
                case UP:
                    y+=speed;
                    move = Move.DOWN;
                    break;
                case DOWN:
                    y-=speed;
                    move = Move.UP;
                    break;
                case RIGHT:
                    x-=speed;
                    move = Move.LEFT;
                    break;
                case LEFT:
                    x+=speed;
                    move = Move.RIGHT;
                    break;
            }
            return true;
        }
        return false;
    }
}
