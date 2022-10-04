package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity{
    private List<Image> bomb = new ArrayList<>();
    public int duration = 0;
    private List<Image> exploded = new ArrayList<>();
    private List<Image> horizontal = new ArrayList<>();
    private List<Image> vertical = new ArrayList<>();
    private List<Image> edge = new ArrayList<>();
    private int index;
    private int AniCount;
    public Bomb(int x, int y, Image img){
        super(x, y, img);

        bomb.add(Sprite.bomb.getFxImage());
        bomb.add(Sprite.bomb_1.getFxImage());
        bomb.add(Sprite.bomb_2.getFxImage());

        exploded.add(Sprite.bomb_exploded.getFxImage());
        exploded.add(Sprite.bomb_exploded1.getFxImage());
        exploded.add(Sprite.bomb_exploded2.getFxImage());


        AniCount = 0;
        index = 0;
    }
    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        duration ++;
        if(duration <= 100) {
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
        } else {
            AniCount++;
            if (AniCount <= 9) {
                renderExplosion(gc);
            } else if (AniCount <= 18) {
                renderExplosion1(gc);
            } else if (AniCount <= 27 ) {
                renderExplosion2(gc);
            }


        }
    }
    public void renderExplosion(GraphicsContext gc) {
        gc.drawImage(Sprite.explosion_horizontal_left_last.getFxImage(),x-Sprite.SCALED_SIZE*2,y);
        gc.drawImage(Sprite.explosion_horizontal_right_last.getFxImage(),x+Sprite.SCALED_SIZE*2,y);
        gc.drawImage(Sprite.explosion_vertical_top_last.getFxImage(),x,-Sprite.SCALED_SIZE*2 + y);
        gc.drawImage(Sprite.explosion_vertical_down_last.getFxImage(),x,Sprite.SCALED_SIZE*2 + y);

        gc.drawImage(Sprite.explosion_horizontal.getFxImage(),x-Sprite.SCALED_SIZE,y);
        gc.drawImage(Sprite.explosion_horizontal.getFxImage(),x+Sprite.SCALED_SIZE,y);
        gc.drawImage(Sprite.explosion_vertical.getFxImage(), x,Sprite.SCALED_SIZE + y);
        gc.drawImage(Sprite.explosion_vertical.getFxImage(), x,-Sprite.SCALED_SIZE + y);
        gc.drawImage(exploded.get(0),x,y);
    }
    public void renderExplosion1(GraphicsContext gc) {
        gc.drawImage(Sprite.explosion_horizontal_left_last1.getFxImage(),x-Sprite.SCALED_SIZE*2,y);
        gc.drawImage(Sprite.explosion_horizontal_right_last1.getFxImage(),x+Sprite.SCALED_SIZE*2,y);
        gc.drawImage(Sprite.explosion_vertical_top_last1.getFxImage(),x,-Sprite.SCALED_SIZE*2 + y);
        gc.drawImage(Sprite.explosion_vertical_down_last1.getFxImage(),x,Sprite.SCALED_SIZE*2 + y);

        gc.drawImage(Sprite.explosion_horizontal1.getFxImage(),x-Sprite.SCALED_SIZE,y);
        gc.drawImage(Sprite.explosion_horizontal1.getFxImage(),x+Sprite.SCALED_SIZE,y);
        gc.drawImage(Sprite.explosion_vertical1.getFxImage(), x,Sprite.SCALED_SIZE + y);
        gc.drawImage(Sprite.explosion_vertical1.getFxImage(), x,-Sprite.SCALED_SIZE + y);
        gc.drawImage(exploded.get(1),x,y);
    }
    public  void renderExplosion2(GraphicsContext gc) {
        gc.drawImage(Sprite.explosion_horizontal_left_last2.getFxImage(),x-Sprite.SCALED_SIZE*2,y);
        gc.drawImage(Sprite.explosion_horizontal_right_last2.getFxImage(),x+Sprite.SCALED_SIZE*2,y);
        gc.drawImage(Sprite.explosion_vertical_top_last2.getFxImage(),x,-Sprite.SCALED_SIZE*2 + y);
        gc.drawImage(Sprite.explosion_vertical_down_last2.getFxImage(),x,Sprite.SCALED_SIZE*2 + y);

        gc.drawImage(Sprite.explosion_horizontal2.getFxImage(),x-Sprite.SCALED_SIZE,y);
        gc.drawImage(Sprite.explosion_horizontal2.getFxImage(),x+Sprite.SCALED_SIZE,y);
        gc.drawImage(Sprite.explosion_vertical2.getFxImage(), x,Sprite.SCALED_SIZE + y);
        gc.drawImage(Sprite.explosion_vertical2.getFxImage(), x,-Sprite.SCALED_SIZE + y);
        gc.drawImage(exploded.get(2),x,y);
    }
}
