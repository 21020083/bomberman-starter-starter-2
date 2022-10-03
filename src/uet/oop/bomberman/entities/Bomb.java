package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity{
    private List<Image> bomb = new ArrayList<>();
    private int index;
    private int AniCount;
    public Bomb(int x, int y, Image img){
        super(x, y, img);

        bomb.add(Sprite.bomb.getFxImage());
        bomb.add(Sprite.bomb_1.getFxImage());
        bomb.add(Sprite.bomb_2.getFxImage());

        AniCount = 0;
        index = 0;
    }
    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        AniCount++;
        if (AniCount > 9) {
            if (index >= 2)
                index = 0;
            else {
                index++;
            }
            AniCount = 0;
        }
        setImg(bomb.get(index));
        gc.drawImage(img, x, y);
    }
}
