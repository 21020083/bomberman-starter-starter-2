package uet.oop.bomberman.entities.movingobject;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Balloom extends Enemy {
    private List<Image> dead = new ArrayList<>();
    private long curTime;
    private long endTime;

    public Balloom(int x, int y, Image img) {
        super(x, y, img);
        left.add(Sprite.balloom_left1.getFxImage());
        left.add(Sprite.balloom_left2.getFxImage());
        left.add(Sprite.balloom_left3.getFxImage());

        right.add(Sprite.balloom_right1.getFxImage());
        right.add(Sprite.balloom_right2.getFxImage());
        right.add(Sprite.balloom_right3.getFxImage());

        dead.add(Sprite.balloom_dead.getFxImage());
        dead.add(Sprite.mob_dead1.getFxImage());
        dead.add(Sprite.mob_dead2.getFxImage());
        dead.add(Sprite.mob_dead3.getFxImage());

        index = 0;
        AniCount = 0;
        move = Move.RIGHT;
        setSpeed(2);
        curTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if(alive){
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
            if(CollisionwithWall()) {
                index = 0;
            }
            endTime = System.currentTimeMillis();
            if((endTime-curTime)/1000 > 3){
                move = Move.values()[new Random().nextInt(Move.values().length)];
                curTime = endTime;
            }
        } else {
            AniCount++;
            if (AniCount < 9) setImg(dead.get(0));
            else if (AniCount < 18) setImg(dead.get(1));
            else if (AniCount < 27) setImg(dead.get(2));
            else if (AniCount < 36) setImg(dead.get(3));
            else {
                death = true;
            }
        }


    }
}
