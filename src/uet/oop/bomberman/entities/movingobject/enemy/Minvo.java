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
    private int i = 0;
    private int count = 0;
    private boolean bombDetected = false;
    private pathfinder pfinder = new pathfinder(BombermanGame.Map);
    private  boolean detectPlayer = false;

    public Minvo(int x, int y, Image img) {
        super(x, y, img);

        left.add(Sprite.minvo_left1.getFxImage());
        left.add(Sprite.minvo_left2.getFxImage());
        left.add(Sprite.minvo_left3.getFxImage());


        right.add(Sprite.minvo_right1.getFxImage());
        right.add(Sprite.minvo_right2.getFxImage());
        right.add(Sprite.minvo_right3.getFxImage());

        dead.add(Sprite.minvo_dead.getFxImage());
        dead.add(Sprite.mob_dead1.getFxImage());
        dead.add(Sprite.mob_dead2.getFxImage());
        dead.add(Sprite.mob_dead3.getFxImage());

        index = 0;
        AniCount = 0;
        move = Move.RIGHT;
        Point = 400;
        setSpeed(1);
    }

    @Override
    public void update() {
        if(alive) {
            if(!bombDetected && !detectPlayer) {
                detectBomber(BombermanGame.bomberman);
                detectBomb();
                moveRandom();
            } else {
                if(runAway(BombermanGame.bomberman)) {
                    followPath();
                } else moveRandom();
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

    private boolean runAway(Bomber b) {
        int col, row;
        int dx = y/Sprite.SCALED_SIZE;
        int dy = x/Sprite.SCALED_SIZE;
        if(x <= b.getX()){
            col = 1;
            row = 1;
            pfinder.setup(dx,dy,row,col);
            while(!pfinder.search()  && col < dy ) {
                row++;
                pfinder.setup(dx,dy,row,col);
                if(row > 11) {
                    row = 1;
                    col ++;
                }
            }
        } else {
            col = 30;
            row = 1;
            pfinder.setup(dx,dy,row,col);
            while(!pfinder.search()  && col > dy + 1) {
                row++;
                pfinder.setup(dx,dy,row,col);
                if(row > 11) {
                    row = 1;
                    col --;
                }
            }
        }
        return pfinder.search();
    }

    private void followPath() {
        setSpeed(2);
        int realX = pfinder.closedlist.get(i).col * Sprite.SCALED_SIZE;
        int realY = pfinder.closedlist.get(i).row * Sprite.SCALED_SIZE;
        if(realX == x){
            if(realY == y) {
                i++;
            } else if(realY > y) {
                move_down();
            } else move_up();
        } else if (realX < x) {
            if(realY == y) {
                move_left();
            } else if(realY > y) {
                move_down();
            } else move_up();
        } else {
            if(realY == y) {
                move_right();
            } else if (realY < y) {
                move_up();
            } else move_down();
        }
        if( i == pfinder.closedlist.size() - 2) {
            detectPlayer = false;
            i = 0;
            setSpeed(1);
        }
    }

    public void detectBomber(Bomber b) {
        int bomberPosx = b.getX()/Sprite.SCALED_SIZE;
        int bomberPosy = b.getY()/Sprite.SCALED_SIZE;

        int posx = x/Sprite.SCALED_SIZE;
        int posy = y/Sprite.SCALED_SIZE;

        if(Math.abs(posx - bomberPosx) + Math.abs(posy - bomberPosy) < 2) {
            detectPlayer = true;
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

