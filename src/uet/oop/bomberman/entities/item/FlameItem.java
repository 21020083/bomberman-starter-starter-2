package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.movingobject.Bomber;

public class FlameItem extends Item{
    public FlameItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if(contactWithPlayer(BombermanGame.bomberman) && !isPicked()) {
            for(Bomb b : BombermanGame.bomberman.boms) {
                b.setLength(b.getLength()+1);
            }
            setPicked(true);
        }
        if(isPicked() && !isRemoved()) {
            duration++;
            if(duration > 1500){
                for(Bomb b : BombermanGame.bomberman.boms) {
                    b.setLength(b.getLength()-1);
                }
                setRemoved(true);
            }
        }
    }


}
