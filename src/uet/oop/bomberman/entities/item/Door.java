package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

public class Door extends Item{
    public Door(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if(contactWithPlayer(BombermanGame.bomberman) && BombermanGame.enemy.isEmpty()){
            BombermanGame.gameover = true;
        }
    }
}
