package uet.oop.bomberman.entities.movingobject.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.movingobject.Bomber;
import uet.oop.bomberman.entities.movingobject.Enemy;
import uet.oop.bomberman.entities.movingobject.pathfinder.pathfinder;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Minvo extends Enemy {
    private int count = 0;
    private int i = 0;
    private int duration = 0;
    private boolean bombDetected = false;
    private  pathfinder pfinder = new pathfinder(BombermanGame.Map);

    public Minvo(int x, int y, Image img) {
        super(x, y, img);

        left.add(Sprite.minvo_left1.getFxImage());
        left.add(Sprite.minvo_left2.getFxImage());
        left.add(Sprite.minvo_left3.getFxImage());


        right.add(Sprite.minvo_right1.getFxImage());
        right.add(Sprite.minvo_right2.getFxImage());
        right.add(Sprite.minvo_right3.getFxImage());

        index = 0;
        AniCount = 0;
        move = Move.RIGHT;
        setSpeed(1);
        pfinder.setup(y,x,BombermanGame.bomberman.getY()/
                Sprite.SCALED_SIZE,BombermanGame.bomberman.getX()/Sprite.SCALED_SIZE);
        pfinder.search();

    }

    @Override
    public void update() {
        if(alive) {
            if(!bombDetected && pfinder.search()) {
                detectBomb();
                followBomber(BombermanGame.bomberman);
            } else {
                duration ++;
                if(duration > 100) {
                    bombDetected = false;
                    int dx = y / Sprite.SCALED_SIZE;
                    int dy = x / Sprite.SCALED_SIZE;
                    int topleftX = (BombermanGame.bomberman.getX() + 6) / Sprite.SCALED_SIZE;
                    int topleftY = (BombermanGame.bomberman.getY() + 6) / Sprite.SCALED_SIZE;
                    pfinder.setup(dx, dy, topleftY, topleftX);
                }
                moveRandom();
            }
        } else {
            AniCount++;
            setImg(Sprite.minvo_dead.getFxImage());
            if(AniCount > 30)
                death = true;
        }

    }

    public void followBomber(Bomber bomberman) {
        int disx = Math.abs((x - bomberman.getX())/Sprite.SCALED_SIZE);
        int disy = Math.abs((y-bomberman.getY())/Sprite.SCALED_SIZE);
        int distance = disx+disy;
        if(distance < 10) {
            int realX = pfinder.closedlist.get(i).col * Sprite.SCALED_SIZE;
            int realY = pfinder.closedlist.get(i).row * Sprite.SCALED_SIZE;
            if (realX == x && realY == y) {
                if (i < pfinder.step - 1) {
                    i++;
                } else {
                    int dx = y / Sprite.SCALED_SIZE;
                    int dy = x / Sprite.SCALED_SIZE;
                    int topleftX = (BombermanGame.bomberman.getX() + 6) / Sprite.SCALED_SIZE;
                    int topleftY = (BombermanGame.bomberman.getY() + 6) / Sprite.SCALED_SIZE;
                    pfinder.setup(dx, dy, topleftY, topleftX);
                    if (!pfinder.search()) {
                        alive = false;
                    }
                    i = 0;
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
        }
    }

    public void detectBomb(){
        for(Bomb b : BombermanGame.bomberman.boms)
        {
          int dx = Math.abs((x-b.getX())/Sprite.SCALED_SIZE);
          int dy = Math.abs((y-b.getY())/Sprite.SCALED_SIZE);
          if(dy + dx < 4) {
              bombDetected = true;
              return;
          }
        }
        bombDetected = false;
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
        if(CollisionwithBomb(BombermanGame.bomberman.boms)){
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

