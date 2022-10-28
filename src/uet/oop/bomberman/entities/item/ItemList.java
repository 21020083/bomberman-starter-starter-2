package uet.oop.bomberman.entities.item;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static uet.oop.bomberman.BombermanGame.brick;

public class ItemList {
    List<Item> Itemlist = new ArrayList<>();
    public void add(int x, int y) {
        int r = new Random().nextInt(200);
        if(r < 33) {
            Itemlist.add(new SpeedItem(x,y, Sprite.powerup_speed.getFxImage()));
        } else if(r < 66) {
            Itemlist.add(new FlameItem(x,y, Sprite.powerup_flames.getFxImage()));
        } else if(r < 99){
            Itemlist.add(new BombItem(x,y, Sprite.powerup_bombs.getFxImage()));
        } else if(r < 132) {
            Itemlist.add(new FlamePass(x,y, Sprite.powerup_flamepass.getFxImage()));
        } else if(r < 166){
            Itemlist.add(new Bombpass(x,y, Sprite.powerup_bombpass.getFxImage()));
        } else {
            Itemlist.add(new WallPass(x, y, Sprite.powerup_wallpass.getFxImage()));
        }
    }
    public void setDoor() {
        int i = new Random().nextInt(brick.size());
        int posX = brick.get(i).getX()/Sprite.SCALED_SIZE;
        int posY = brick.get(i).getY()/Sprite.SCALED_SIZE;
        Itemlist.add(new Door(posX,posY,Sprite.portal.getFxImage()));
    }
    public void update() {
        for(int i = 0; i < Itemlist.size(); i++) {
            Itemlist.get(i).update();
            if(Itemlist.get(i).isRemoved()) {
                Itemlist.remove(i);
                i--;
            }
        }
    }
    public void render(GraphicsContext gc) {
        Itemlist.forEach(i -> i.render(gc));
    }
}
