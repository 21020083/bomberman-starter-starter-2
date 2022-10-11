package uet.oop.bomberman.entities.movingobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movingobject.pathfinder.pathfinder;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Random;

public class Minvo extends Enemy{
    private int count = 0;
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
            int realX = pfinder.closedlist.get(count).col*Sprite.SCALED_SIZE;
            int realY = pfinder.closedlist.get(count).row*Sprite.SCALED_SIZE;
            if(realX == x && realY == y ) {
                if(count < pfinder.step - 1) {
                    count++;
                    if(count > pfinder.step - 5) {
                        setSpeed(2);
                    }
                } else {
                    int dx = y/Sprite.SCALED_SIZE;
                    int dy = x/Sprite.SCALED_SIZE;
                    int topleftX = (BombermanGame.bomberman.getX()+6)/Sprite.SCALED_SIZE;
                    int topleftY = (BombermanGame.bomberman.getY()+6)/Sprite.SCALED_SIZE;
                    pfinder.setup(dx,dy,topleftY,topleftX);
                    if(!pfinder.search()){
                       alive = false;
                    }
                    count = 0;
                }

            }else if(realX < x) {
                move_left();
            } else if(realX > x) {
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
            if(CollisionwithBomb(BombermanGame.bomberman.boms)){
                index = 0;
            }
        } else {
            AniCount++;
            setImg(Sprite.minvo_dead.getFxImage());
            if(AniCount > 30)
                death = true;
        }

    }


    public  boolean CollisionwithWall() {
        if(x < Sprite.SCALED_SIZE || y < Sprite.SCALED_SIZE || x > (BombermanGame.WIDTH - 2) *
                Sprite.SCALED_SIZE || y > (BombermanGame.HEIGHT - 2) * Sprite.SCALED_SIZE) {
            switch (move) {
                case UP:
                    y +=speed;
                    move = Move.DOWN;
                    break;
                case DOWN:
                    y -=speed;
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
    public  boolean CollisionwithWall(int[][] Map) {
        int topleftX = (x+6)/Sprite.SCALED_SIZE;
        int topleftY = (y+6)/Sprite.SCALED_SIZE;

        int toprightX = (x + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;
        int toprightY = (y+6)/Sprite.SCALED_SIZE;

        int bottomleftX = (x+6)/Sprite.SCALED_SIZE;
        int bottomleftY = (y + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;

        int bottomrightX = (x + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;
        int bottomrightY = (y + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;


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
}
