package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movingobject.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item{
    public SpeedItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if(contactWithPlayer(BombermanGame.bomberman) && !isPicked()) {
            BombermanGame.bomberman.setSpeed(4);
            setPicked(true);
        }
        if(isPicked()) {
            duration++;
            if (duration > 150){
                BombermanGame.bomberman.setSpeed(2);
            }
        }
    }



}
