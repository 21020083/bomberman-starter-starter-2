package uet.oop.bomberman.entities.movingobject.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.movingobject.Enemy;
import uet.oop.bomberman.entities.movingobject.pathfinder.pathfinder;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;
import java.util.Random;

import static uet.oop.bomberman.BombermanGame.bomberman;

public class Ovape extends Enemy {
    private long curTime;
    private long endTime;
    private int i = 0;
    private pathfinder pfinder;
    private boolean searching = true;
    private  boolean bomDetected = false;

    public Ovape(int x, int y, Image img) {
        super(x, y, img);
        left.add(Sprite.ovape_left1.getFxImage());
        left.add(Sprite.ovape_left2.getFxImage());
        left.add(Sprite.ovape_left3.getFxImage());


        right.add(Sprite.ovape_right1.getFxImage());
        right.add(Sprite.ovape_right2.getFxImage());
        right.add(Sprite.ovape_right3.getFxImage());

        dead.add(Sprite.ovape_dead.getFxImage());
        dead.add(Sprite.mob_dead1.getFxImage());
        dead.add(Sprite.mob_dead2.getFxImage());
        dead.add(Sprite.mob_dead3.getFxImage());

        pfinder = new pathfinder(BombermanGame.Map);

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
            if(!bomDetected){
                Bombdetect(bomberman.boms);
                if(searching) {
                    int centerX = (bomberman.getY()+12)/ Sprite.SCALED_SIZE;
                    int centerY = (bomberman.getX()+12)/ Sprite.SCALED_SIZE;
                    pfinder.setup2(y/Sprite.SCALED_SIZE,x/Sprite.SCALED_SIZE, centerX,centerY);
                    searching = !pfinder.search();
                } else {
                    followBomber();
                }
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
        int realX = pfinder.closedlist.get(i).col*Sprite.SCALED_SIZE;
        int realY = pfinder.closedlist.get(i).row*Sprite.SCALED_SIZE;
        if (realX == x) {
            if(realY < y) {
                move_up();
            } else if(realY > y) {
                move_down();
            } else i++;
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
        if(i >= pfinder.closedlist.size() - 1){
            i = 0;
            searching = true;
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
            bomDetected = false;
        }
    }

    public void Bombdetect(List<Bomb> bombs) {
        for(Bomb b : bombs)
        {
            if(b.getX() + 70 > x && x + 32 > b.getX() - 50 &&
                    y+ 32 > b.getY() - 50 && b.getY() + 70 > y){
                switch (move) {
                    case UP:
                        if(Math.abs(b.getY() + Sprite.SCALED_SIZE -y) < 15) {
                            y +=Math.abs(b.getY() + Sprite.SCALED_SIZE -y);
                        } else y += speed;
                        move = Move.DOWN;
                        break;
                    case DOWN:
                        if(Math.abs(b.getY()-y-28) < 15) {
                            y -=Math.abs(b.getY()-y-28);
                        } else y -= speed;
                        move = Move.UP;
                        break;
                    case RIGHT:
                        if(Math.abs(b.getX()-x-28) < 15) {
                            x-=Math.abs(b.getX()-x-28);
                        } else x -= speed;
                        move = Move.LEFT;
                        break;
                    case LEFT:
                        if(Math.abs(b.getX()+Sprite.SCALED_SIZE-x) < 15) {
                            x+=Math.abs(b.getX()+Sprite.SCALED_SIZE-x);
                        } else x+= speed;
                        move = Move.RIGHT;
                        break;
                }
                bomDetected = true;
            }
        }
    }
}
