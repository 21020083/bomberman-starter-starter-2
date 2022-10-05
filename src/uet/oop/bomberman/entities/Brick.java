package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Brick extends Entity{
   public int count;
    int index;
    int AniCount;
    private List<Image> exploded = new ArrayList<>();
    public Brick(int x, int y, Image img) {
        super(x, y, img);
        exploded.add(Sprite.brick_exploded.getFxImage());
        exploded.add(Sprite.brick_exploded1.getFxImage());
        exploded.add(Sprite.brick_exploded2.getFxImage());

        count = 0;
    }

    @Override
    public void update() {
        count++;
        //if(count > 600) {
            //AniCount++;
            //if (AniCount > 12){
                //if (index >= 2)
                    //index = 0;
                //else {
                   // index++;
                //}
                //AniCount = 0;
            //}
            //setImg(exploded.get(index));
        //}
    }
}
