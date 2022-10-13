package uet.oop.bomberman.entities.movingobject;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.movingobject.pathfinder.pathfinder;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Oneal extends  Enemy{
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
        pfinder.setup(y,x,BombermanGame.bomberman.getY()/
                Sprite.SCALED_SIZE,BombermanGame.bomberman.getX()/Sprite.SCALED_SIZE);
        alive = pfinder.search();
    }
    public void update() {
        if(alive) {
            if(!bombDetected) {
                detectBomb(BombermanGame.bomberman.boms);
                followBomber(BombermanGame.bomberman);
            } else {
                moveaway++;
                if(moveaway > 120) {
                    bombDetected = false;
                    moveaway = 0;
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
        int realX = pfinder.closedlist.get(count).col * Sprite.SCALED_SIZE;
        int realY = pfinder.closedlist.get(count).row * Sprite.SCALED_SIZE;
        if (realX == x && realY == y) {
            if (count < pfinder.step - 1) {
                count++;
                if (count > pfinder.step - 5) {
                    setSpeed(2);
                }
            } else {
                int dx = y / Sprite.SCALED_SIZE;
                int dy = x / Sprite.SCALED_SIZE;
                int topleftX = (bomberman.getX() + 6) / Sprite.SCALED_SIZE;
                int topleftY = (bomberman.getY() + 6) / Sprite.SCALED_SIZE;
                pfinder.setup(dx, dy, topleftY, topleftX);
                if (!pfinder.search()) {
                    alive = false;
                }
                count = 0;
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
        if (CollisionwithWall()) {
            index = 0;
        }
        if (CollisionwithWall(BombermanGame.Map)) {
            index = 0;
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
                    y +=speed;
                    break;
                case DOWN:
                    y -=speed;
                    break;
                case RIGHT:
                    x-=speed;
                    break;
                case LEFT:
                    x+=speed;
                    break;
            }
            return true;
        }
        return false;
    }
    public  boolean CollisionwithWall(int[][] Map) {
        int topleftX = (x+6)/Sprite.SCALED_SIZE;
        int topleftY = (y+6)/Sprite.SCALED_SIZE;

        int toprightX = (x + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;
        int toprightY = (y+6)/Sprite.SCALED_SIZE;

        int bottomleftX = (x+6)/Sprite.SCALED_SIZE;
        int bottomleftY = (y + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;

        int bottomrightX = (x + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;
        int bottomrightY = (y + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;


        if(BombermanGame.Map[topleftY][topleftX] != 1|| BombermanGame.Map[bottomrightY][bottomrightX] != 1 ||
                BombermanGame.Map[toprightY][toprightX] != 1 || BombermanGame.Map[bottomleftY][bottomleftX] != 1) {
            switch (move) {
                case UP:
                    y+=speed;
                    break;
                case DOWN:
                    y-=speed;
                    break;
                case RIGHT:
                    x-=speed;
                    break;
                case LEFT:
                    x+=speed;
                    break;
            }
            return true;
        }
        return false;
    }
}
