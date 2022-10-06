package uet.oop.bomberman.entities.movingobject;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;


public abstract class MovingObject extends Entity {
    protected int speed = 3;
    protected boolean alive;
    protected enum  Move{UP, LEFT, DOWN , RIGHT};
    protected Move move ;
    public MovingObject(int x, int y, Image img) {

        super(x, y, img);
        alive = true;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public abstract void move_up();
    public abstract void move_down();
    public abstract void move_left();
    public abstract void move_right();
    public abstract boolean CollisionwithWall() ;


}
