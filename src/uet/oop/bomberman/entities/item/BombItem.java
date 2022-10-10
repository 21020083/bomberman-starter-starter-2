package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

public class BombItem extends Item{
    public BombItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if(contactWithPlayer(BombermanGame.bomberman) && !isPicked()) {
            BombermanGame.bomberman.setBombAmount(BombermanGame.bomberman.getBombAmount()+1);
            setPicked(true);
            setRemoved(true);
        }
    }
}
