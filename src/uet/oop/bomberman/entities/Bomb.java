package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity{

    private List<Image> bomb = new ArrayList<>();
    int leftx, lefty;
    int rightx, righty;
    int upx, upy;
    int downx, downy;
    public int duration = 0;
    private int length = 2;
    private int leftLength = 0;
    private int rightLength = 0;
    private int topLength = 0;
    private int bottomLength = 0;
    private List<Image> exploded = new ArrayList<>();
    private List<Image> left = new ArrayList<>();
    private List<Image> right = new ArrayList<>();
    private List<Image> up = new ArrayList<>();
    private List<Image> down = new ArrayList<>();
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

        SetExplosion();


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
            if (AniCount <= 6) {
                renderExplosion(gc);
            } else if (AniCount <= 12) {
                renderExplosion1(gc);
            } else if (AniCount <= 18) {
                renderExplosion2(gc);
                destroyBrick(BombermanGame.Map);
            }


        }
    }
    public void SetExplosion() {
        leftx = x-Sprite.SCALED_SIZE; lefty = y;
        rightx = x+Sprite.SCALED_SIZE; righty = y;
        upx = x; upy =-Sprite.SCALED_SIZE + y;
        downx = x; downy = Sprite.SCALED_SIZE + y;

        while(leftx >=0 && leftx >= x-Sprite.SCALED_SIZE*length &&
                !(BombermanGame.Map[lefty/Sprite.SCALED_SIZE][leftx/Sprite.SCALED_SIZE] instanceof Wall)
                && !(BombermanGame.Map[lefty/Sprite.SCALED_SIZE][leftx/Sprite.SCALED_SIZE] instanceof Brick)) {
            leftLength++;
            leftx -=Sprite.SCALED_SIZE;
        }
        while(rightx <=(BombermanGame.WIDTH-1)*Sprite.SCALED_SIZE && rightx <= x+Sprite.SCALED_SIZE*length &&
                !(BombermanGame.Map[righty/Sprite.SCALED_SIZE][rightx/Sprite.SCALED_SIZE] instanceof Wall)
                && !(BombermanGame.Map[righty/Sprite.SCALED_SIZE][rightx/Sprite.SCALED_SIZE] instanceof Brick)) {
            rightLength++;
            rightx +=Sprite.SCALED_SIZE;
        }
        while(upy >=0 && upy >= y-Sprite.SCALED_SIZE*length &&
                !(BombermanGame.Map[upy/Sprite.SCALED_SIZE][upx/Sprite.SCALED_SIZE] instanceof Wall)
                && !(BombermanGame.Map[upy/Sprite.SCALED_SIZE][upx/Sprite.SCALED_SIZE] instanceof Brick)) {
            topLength++;
            upy -= Sprite.SCALED_SIZE;
        }
        while(downy <=(BombermanGame.HEIGHT-1)*Sprite.SCALED_SIZE && downy <= y+Sprite.SCALED_SIZE*length &&
                !(BombermanGame.Map[downy/Sprite.SCALED_SIZE][downx/Sprite.SCALED_SIZE] instanceof Wall)
                && !(BombermanGame.Map[downy/Sprite.SCALED_SIZE][downx/Sprite.SCALED_SIZE] instanceof Brick)) {
            bottomLength++;
            downy += Sprite.SCALED_SIZE;
        }
    }
    public void renderExplosion(GraphicsContext gc) {


        if(leftLength > 0) {
            gc.drawImage(Sprite.explosion_horizontal_left_last.getFxImage(),x-Sprite.SCALED_SIZE*2,y);
        }
        if(rightLength > 0) {
            gc.drawImage(Sprite.explosion_horizontal_right_last.getFxImage(),x+Sprite.SCALED_SIZE*2,y);
        }
        if(topLength > 0) {
            gc.drawImage(Sprite.explosion_vertical_top_last.getFxImage(),x,-Sprite.SCALED_SIZE*2 + y);
        }
        if(bottomLength > 0){
            gc.drawImage(Sprite.explosion_vertical_down_last.getFxImage(),x,Sprite.SCALED_SIZE*2 + y);
        }
        int l = leftLength, r = rightLength, t =topLength, b = bottomLength;
        while(l > 0){
            gc.drawImage(Sprite.explosion_horizontal.getFxImage(),x-Sprite.SCALED_SIZE,y);
            l--;
        }
        while(r > 0){
            r--;
            gc.drawImage(Sprite.explosion_horizontal.getFxImage(),x+Sprite.SCALED_SIZE,y);
        }
        while (t > 0) {
            t--;
            gc.drawImage(Sprite.explosion_vertical.getFxImage(), x,-Sprite.SCALED_SIZE + y);
        }
        while (b > 0) {
            b--;
            gc.drawImage(Sprite.explosion_vertical.getFxImage(), x,+Sprite.SCALED_SIZE + y);
        }
        gc.drawImage(exploded.get(0),x,y);
    }
    public void renderExplosion1(GraphicsContext gc) {

        if(leftLength > 0) {
            gc.drawImage(Sprite.explosion_horizontal_left_last1.getFxImage(),x-Sprite.SCALED_SIZE*2,y);
        }
        if(rightLength > 0) {
            gc.drawImage(Sprite.explosion_horizontal_right_last1.getFxImage(),x+Sprite.SCALED_SIZE*2,y);
        }
        if(topLength > 0) {
            gc.drawImage(Sprite.explosion_vertical_top_last1.getFxImage(),x,-Sprite.SCALED_SIZE*2 + y);
        }
        if(bottomLength > 0){
            gc.drawImage(Sprite.explosion_vertical_down_last1.getFxImage(),x,Sprite.SCALED_SIZE*2 + y);
        }

        int l = leftLength, r = rightLength, t =topLength, b = bottomLength;
        while(l > 0){
            gc.drawImage(Sprite.explosion_horizontal1.getFxImage(),x-Sprite.SCALED_SIZE,y);
            l--;
        }
        while(r > 0){
            r--;
            gc.drawImage(Sprite.explosion_horizontal1.getFxImage(),x+Sprite.SCALED_SIZE,y);
        }
        while (t > 0) {
            t--;
            gc.drawImage(Sprite.explosion_vertical1.getFxImage(), x,-Sprite.SCALED_SIZE + y);
        }
        while (b > 0) {
            b--;
            gc.drawImage(Sprite.explosion_vertical1.getFxImage(), x,Sprite.SCALED_SIZE + y);
        }
        gc.drawImage(exploded.get(1),x,y);
    }
    public  void renderExplosion2(GraphicsContext gc) {
        if(leftLength > 0) {
            gc.drawImage(Sprite.explosion_horizontal_left_last2.getFxImage(),x-Sprite.SCALED_SIZE*2,y);
        }
        if(rightLength > 0) {
            gc.drawImage(Sprite.explosion_horizontal_right_last2.getFxImage(),x+Sprite.SCALED_SIZE*2,y);
        }
        if(topLength > 0) {
            gc.drawImage(Sprite.explosion_vertical_top_last2.getFxImage(),x,-Sprite.SCALED_SIZE*2 + y);
        }
        if(bottomLength > 0){
            gc.drawImage(Sprite.explosion_vertical_down_last2.getFxImage(),x,Sprite.SCALED_SIZE*2 + y);
        }

        int l = leftLength, r = rightLength, t =topLength, b = bottomLength;
        while(l > 0){
            gc.drawImage(Sprite.explosion_horizontal2.getFxImage(),x-Sprite.SCALED_SIZE,y);
            l--;
        }
        while(r > 0){
            r--;
            gc.drawImage(Sprite.explosion_horizontal2.getFxImage(),x+Sprite.SCALED_SIZE,y);
        }
        while (t > 0) {
            t--;
            gc.drawImage(Sprite.explosion_vertical2.getFxImage(), x,-Sprite.SCALED_SIZE + y);
        }
        while (b > 0) {
            b--;
            gc.drawImage(Sprite.explosion_vertical2.getFxImage(), x,Sprite.SCALED_SIZE + y);
        }
        gc.drawImage(exploded.get(2),x,y);
    }
    public void destroyBrick(Entity[][] Map) {
        int lx = (x-Sprite.SCALED_SIZE*(leftLength+1))/Sprite.SCALED_SIZE, ly = y/Sprite.SCALED_SIZE;
        int rx = (x+Sprite.SCALED_SIZE*(rightLength+1))/Sprite.SCALED_SIZE, ry = y/Sprite.SCALED_SIZE;
        int ux = x/Sprite.SCALED_SIZE, uy =(-Sprite.SCALED_SIZE*(topLength+1) + y)/Sprite.SCALED_SIZE;
        int dx = x/Sprite.SCALED_SIZE, dy = (Sprite.SCALED_SIZE*(bottomLength+1) + y)/Sprite.SCALED_SIZE;
        if(Map[ly][lx] instanceof Brick) {
            Map[ly][lx] = new Grass(lx,ly,Sprite.grass.getFxImage());
        }
        if(Map[ry][rx] instanceof Brick) {
            Map[ry][rx] = new Grass(rx,ry,Sprite.grass.getFxImage());
        }
        if(Map[uy][ux] instanceof Brick) {
            Map[uy][ux] = new Grass(ux,uy,Sprite.grass.getFxImage());
        }
        if(Map[dy][dx] instanceof Brick) {
            Map[dy][dx] = new Grass(dx,dy,Sprite.grass.getFxImage());
        }
    }
}
