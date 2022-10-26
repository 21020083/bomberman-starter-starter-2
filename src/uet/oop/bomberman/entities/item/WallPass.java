package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movingobject.Bomber;

public class WallPass extends Item{
    public WallPass(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if(contactWithPlayer(BombermanGame.bomberman)) {
            Bomber.WallPass = true;
            setPicked(true);
        }
    }
}
