package uet.oop.bomberman.entities.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.movingobject.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Item extends Entity {
    private boolean picked = false;
    private boolean removed = false;
    protected int duration = 0;

    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public boolean contactWithPlayer(Bomber b) {
        int dx = (b.getX()+6);
        int dy = (b.getY()+6);
        if(dx + 22 > x && dx < x + Sprite.SCALED_SIZE && dy + 22 > y && dy < y+Sprite.SCALED_SIZE
                && BombermanGame.Map[dy/Sprite.SCALED_SIZE][dx/Sprite.SCALED_SIZE] == 1) {
            return true;
        }
        return false;
    }
    public void render(GraphicsContext gc) {
        if(!isPicked())
        {
            gc.drawImage(img, x, y);
        }
    }
}
