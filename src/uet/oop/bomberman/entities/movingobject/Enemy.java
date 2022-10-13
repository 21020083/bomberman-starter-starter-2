package uet.oop.bomberman.entities.movingobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends MovingObject{
    protected final ArrayList<Image> left  = new ArrayList<>();
    protected final ArrayList<Image> right = new ArrayList<>();
    protected int index;
    protected int AniCount;

    public Enemy(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void move_up() {
        y-=speed;
        move = Move.UP;
        setImg(left.get(index));

    }
    public void move_down() {
        y+=speed;
        move = Move.DOWN;
        setImg(right.get(index));
    }
    public void move_left() {
        x-=speed;
        move = Move.LEFT;
        setImg(left.get(index));
    }
    public void move_right() {
        x+=speed;
        move = Move.RIGHT;
        setImg(right.get(index));
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

    @Override
    public boolean CollisionwithBomb(List<Bomb> bombs) {
        for(Bomb b : bombs)
        {
            if(b.getX() + Sprite.SCALED_SIZE > x && x + 28 > b.getX() &&
            y+ 28 > b.getY() && b.getY() + Sprite.SCALED_SIZE > y){
                switch (move) {
                    case UP:
                        y +=Math.abs(b.getY() + Sprite.SCALED_SIZE -y);
                        move = Move.DOWN;
                        break;
                    case DOWN:
                        y -=Math.abs(b.getY()-y-28);;
                        move = Move.UP;
                        break;
                    case RIGHT:
                        x-=Math.abs(b.getX()-x-28);;
                        move = Move.LEFT;
                        break;
                    case LEFT:
                        x+=Math.abs(b.getX()+Sprite.SCALED_SIZE-x);;
                        move = Move.RIGHT;
                        break;
                }
                return true;
            }
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
    public void render(GraphicsContext gc) {

        if (alive) {
            AniCount++;
            if (AniCount > 9) {
                if (index >= 2)
                    index = 0;
                else {
                    index++;
                }
                AniCount = 0;
            }
            if (move == Move.UP || move == Move.LEFT) {
                setImg(left.get(index));
            }   else {
                setImg(right.get(index));
            }
        }
        gc.drawImage(img, x, y);

    }

    @Override
    public void update() {

    }
}
