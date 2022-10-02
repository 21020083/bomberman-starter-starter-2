package uet.oop.bomberman.entities.movingobject;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Bomber extends MovingObject {

    private boolean up, down, left, right;
    private final ArrayList<Image> up_movement = new ArrayList<>();
    private final ArrayList<Image> down_movement = new ArrayList<>();
    private final ArrayList<Image> left_movement = new ArrayList<>();
    private final ArrayList<Image> right_movement = new ArrayList<>();
    private int index;
    private double AniCount;
    private boolean moving = false;


    public Bomber(int x, int y, Image img) {
        super( x, y, img);

        up_movement.add(Sprite.player_up.getFxImage());
        up_movement.add(Sprite.player_up_1.getFxImage());
        up_movement.add(Sprite.player_up_2.getFxImage());

        down_movement.add(Sprite.player_down.getFxImage());
        down_movement.add(Sprite.player_down_1.getFxImage());
        down_movement.add(Sprite.player_down_2.getFxImage());

        left_movement.add(Sprite.player_left.getFxImage());
        left_movement.add(Sprite.player_left_1.getFxImage());
        left_movement.add(Sprite.player_left_2.getFxImage());

        right_movement.add(Sprite.player_right.getFxImage());
        right_movement.add(Sprite.player_right_1.getFxImage());
        right_movement.add(Sprite.player_right_2.getFxImage());

        index = 0;
        AniCount = 0;
        setSpeed(2);
        up = false;
        down = false;
        left = false;
        right = false;
    }

    @Override
    public void update() {
        if(up) move_up();
        if(down) move_down();
        if(left) move_left();
        if(right) move_right();
        AniCount++;
        if (AniCount > 12/speed){
            if (index >= 2)
                index = 0;
            else {
                index++;
            }
            AniCount = 0;
        }
        if(CollisionwithWall()) {
            index = 0;
        }
        if(CollisionwithWall(BombermanGame.Map)){
            index = 0;
        }

    }
    public void eventHandle(Scene scene) {

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch(keyEvent.getCode()) {
                    case W:
                        up = true;
                        moving = true;
                        break;
                    case S:
                        down = true;
                        moving = true;
                        break;
                    case A:
                        left = true;
                        moving = true;
                        break;
                    case D:
                        right = true;
                        moving = true;
                        break;
                    default:
                        moving = false;
                        break;
                }


            }


        });


        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch(keyEvent.getCode()) {
                    case W:
                        up = false;
                        moving = false;
                        break;
                    case S:
                        down = false;
                        moving = false;
                        break;
                    case A:
                        left = false;
                        moving = false;
                        break;
                    case D:
                        right = false;
                        moving = false;
                        break;
                    default:
                        moving = false;
                        break;
                }
            }

        });



    }
    public void move_up() {
        y-= speed;
        move = Move.UP;
        setImg(up_movement.get(index));

    }
    public void move_down() {
        y+= speed;
        move = Move.DOWN;
        setImg(down_movement.get(index));
    }
    public void move_left() {
        x-= speed;
        move = Move.LEFT;
        setImg(left_movement.get(index));
    }
    public void move_right() {
        x+= speed;
        move = Move.RIGHT;
        setImg(right_movement.get(index));
    }
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    @Override
    public  boolean CollisionwithWall() {
        if(x < Sprite.SCALED_SIZE || y < Sprite.SCALED_SIZE || x > (BombermanGame.WIDTH - 2) *
                Sprite.SCALED_SIZE || y > (BombermanGame.HEIGHT - 2) * Sprite.SCALED_SIZE) {
            moving = false;
            if(up) y+=speed;
            if(down) y-=speed;
            if(left) x+=speed;
            if(right) x-=speed;
            return true;
        }
        return false;
    }
    public  boolean CollisionwithWall(char[][] Map) {
        int topleftX = (x+6)/Sprite.SCALED_SIZE;
        int topleftY = (y+6)/Sprite.SCALED_SIZE;

        int toprightX = (x + Sprite.DEFAULT_SIZE + 6)/Sprite.SCALED_SIZE;
        int toprightY = (y+6)/Sprite.SCALED_SIZE;

        int bottomleftX = (x+6)/Sprite.SCALED_SIZE;
        int bottomleftY = (y + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;

        int bottomrightX = (x + Sprite.DEFAULT_SIZE + 6)/Sprite.SCALED_SIZE;
        int bottomrightY = (y + Sprite.DEFAULT_SIZE + 12)/Sprite.SCALED_SIZE;


        if(Map[topleftY][topleftX] == '#' || Map[toprightY][toprightX] == '#' ||
                Map[bottomrightY][bottomrightX] == '#' || Map[bottomleftY][bottomleftX] == '#'){
            moving = false;
            if(up) y+=speed;
            if(down) y-=speed;
            if(left) x+=speed;
            if(right) x-=speed;
            return true;
        }
        return false;
    }
}