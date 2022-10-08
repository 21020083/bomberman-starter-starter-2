package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Brick extends Entity{
   public boolean destroyed;
   public boolean remove = false;
    private int index;
    private int AniCount;
    public int duration = 0;
    public static List<Image> exploded = new ArrayList<>();
    public Brick(int x, int y, Image img) {
        super(x, y, img);
        exploded.add(Sprite.brick_exploded.getFxImage());
        exploded.add(Sprite.brick_exploded1.getFxImage());
        exploded.add(Sprite.brick_exploded2.getFxImage());

        destroyed =false;
    }

    @Override
    public void update() {
        if(destroyed) {
            AniCount++;
            duration++;
            if (AniCount > 5){
                if (index >= 2)
                    index = 0;
                else {
                    index++;
                }
                AniCount = 0;
            }
            setImg(exploded.get(index));
            if(duration >= 18){
                int dx = x/Sprite.SCALED_SIZE;
                int dy = y/Sprite.SCALED_SIZE;
                BombermanGame.Map[dy][dx] = 1;
                remove = true;
            }
        }
    }


}
