package uet.oop.bomberman.entities.item;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemList {
    List<Item> Itemlist = new ArrayList<>();
    public void add(int x, int y) {
        Random rand = new Random();
        int r = rand.nextInt(100);
        if(r < 33) {
            Itemlist.add(new SpeedItem(x,y, Sprite.powerup_speed.getFxImage()));
        } else if(r < 66) {
            Itemlist.add(new FlameItem(x,y, Sprite.powerup_flames.getFxImage()));
        } else {
            Itemlist.add(new BombItem(x,y, Sprite.powerup_bombs.getFxImage()));
        }
    }
    public void setDoor(int x, int y) {
        Itemlist.add(new Door(x,y,Sprite.portal.getFxImage()));
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
