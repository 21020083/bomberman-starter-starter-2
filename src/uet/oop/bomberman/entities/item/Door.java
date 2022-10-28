package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Sound;
import uet.oop.bomberman.entities.movingobject.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Door extends Item{
    public Door(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    private boolean stageClear = true;
    public boolean contactWithPlayer(Bomber b) {
        int dx = (b.getX()+6);
        int dy = (b.getY()+6);
        if(dx + 22 > x && dx < x + 22 && dy + 16 > y && dy < y+16
                && BombermanGame.Map[y/Sprite.SCALED_SIZE][x/Sprite.SCALED_SIZE] == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if(contactWithPlayer(BombermanGame.bomberman) && BombermanGame.enemy.isEmpty() && stageClear){
            Sound.stop();
            Sound.Stageclear();
            stageClear = false;
        }
    }
}
