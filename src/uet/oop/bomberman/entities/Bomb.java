package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movingobject.Enemy;
import uet.oop.bomberman.entities.movingobject.MovingObject;
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
    private int length ;
    private int leftLength = 0;
    private int rightLength = 0;
    private int topLength = 0;
    private int bottomLength = 0;
    private List<Image> exploded = new ArrayList<>();
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
        length = 2;

        SetExplosion();


    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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
                destroyBrick(BombermanGame.Map);
            } else if (AniCount <= 12) {
                renderExplosion1(gc);
                explodeBomb(BombermanGame.bomberman.boms);
            } else if (AniCount <= 18) {
                renderExplosion2(gc);
                explodeBomber(BombermanGame.bomberman);
            }

            explodeEnemy(BombermanGame.enemy);

        }
    }
    public void SetExplosion() {
        leftx = x-Sprite.SCALED_SIZE; lefty = y;
        rightx = x+Sprite.SCALED_SIZE; righty = y;
        upx = x; upy =-Sprite.SCALED_SIZE + y;
        downx = x; downy = Sprite.SCALED_SIZE + y;

        while(leftx >=0 && leftx >= x-Sprite.SCALED_SIZE*length &&
                BombermanGame.Map[lefty/Sprite.SCALED_SIZE][leftx/Sprite.SCALED_SIZE] == 1) {
            leftLength++;
            leftx -=Sprite.SCALED_SIZE;
        }
        while(rightx <=(BombermanGame.WIDTH-1)*Sprite.SCALED_SIZE && rightx <= x+Sprite.SCALED_SIZE*length &&
                BombermanGame.Map[righty/Sprite.SCALED_SIZE][rightx/Sprite.SCALED_SIZE] == 1) {
            rightLength++;
            rightx +=Sprite.SCALED_SIZE;
        }
        while(upy >=0 && upy >= y-Sprite.SCALED_SIZE*length &&
                BombermanGame.Map[upy/Sprite.SCALED_SIZE][upx/Sprite.SCALED_SIZE] == 1) {
            topLength++;
            upy -= Sprite.SCALED_SIZE;
        }
        while(downy <=(BombermanGame.HEIGHT-1)*Sprite.SCALED_SIZE && downy <= y+Sprite.SCALED_SIZE*length &&
                BombermanGame.Map[downy/Sprite.SCALED_SIZE][downx/Sprite.SCALED_SIZE] == 1) {
            bottomLength++;
            downy += Sprite.SCALED_SIZE;
        }
    }
    public void renderExplosion(GraphicsContext gc) {


        if(leftLength > 0) {
            gc.drawImage(Sprite.explosion_horizontal_left_last.getFxImage(),x-Sprite.SCALED_SIZE*leftLength,y);
        }
        if(rightLength > 0) {
            gc.drawImage(Sprite.explosion_horizontal_right_last.getFxImage(),x+Sprite.SCALED_SIZE*rightLength,y);
        }
        if(topLength > 0) {
            gc.drawImage(Sprite.explosion_vertical_top_last.getFxImage(),x,-Sprite.SCALED_SIZE*topLength + y);
        }
        if(bottomLength > 0){
            gc.drawImage(Sprite.explosion_vertical_down_last.getFxImage(),x,Sprite.SCALED_SIZE*bottomLength + y);
        }
        int l = 1, r = 1, t = 1, b = 1;
        while(l < leftLength){
            gc.drawImage(Sprite.explosion_horizontal.getFxImage(),x-Sprite.SCALED_SIZE*l,y);
            l++;
        }
        while(r < rightLength){
            gc.drawImage(Sprite.explosion_horizontal.getFxImage(),x+Sprite.SCALED_SIZE*r,y);
            r++;
        }
        while (t < topLength) {
            gc.drawImage(Sprite.explosion_vertical.getFxImage(), x,-Sprite.SCALED_SIZE*t + y);
            t++;
        }
        while (b < bottomLength) {
            gc.drawImage(Sprite.explosion_vertical.getFxImage(), x,Sprite.SCALED_SIZE*b + y);
            b++;
        }
        gc.drawImage(exploded.get(0),x,y);
    }
    public void renderExplosion1(GraphicsContext gc) {

        if(leftLength > 0) {
            gc.drawImage(Sprite.explosion_horizontal_left_last1.getFxImage(),x-Sprite.SCALED_SIZE*leftLength,y);
        }
        if(rightLength > 0) {
            gc.drawImage(Sprite.explosion_horizontal_right_last1.getFxImage(),x+Sprite.SCALED_SIZE*rightLength,y);
        }
        if(topLength > 0) {
            gc.drawImage(Sprite.explosion_vertical_top_last1.getFxImage(),x,-Sprite.SCALED_SIZE*topLength + y);
        }
        if(bottomLength > 0){
            gc.drawImage(Sprite.explosion_vertical_down_last1.getFxImage(),x,Sprite.SCALED_SIZE*bottomLength + y);
        }

        int l = 1, r = 1, t = 1, b = 1;
        while(l < leftLength){
            gc.drawImage(Sprite.explosion_horizontal1.getFxImage(),x-Sprite.SCALED_SIZE*l,y);
            l++;
        }
        while(r < rightLength){
            gc.drawImage(Sprite.explosion_horizontal1.getFxImage(),x+Sprite.SCALED_SIZE*r,y);
            r++;
        }
        while (t < topLength) {
            gc.drawImage(Sprite.explosion_vertical1.getFxImage(), x,-Sprite.SCALED_SIZE*t + y);
            t++;
        }
        while (b < bottomLength) {
            gc.drawImage(Sprite.explosion_vertical1.getFxImage(), x,Sprite.SCALED_SIZE*b + y);
            b++;
        }
        gc.drawImage(exploded.get(1),x,y);
    }
    public  void renderExplosion2(GraphicsContext gc) {
        if(leftLength > 0) {
            gc.drawImage(Sprite.explosion_horizontal_left_last2.getFxImage(),x-Sprite.SCALED_SIZE*leftLength,y);
        }
        if(rightLength > 0) {
            gc.drawImage(Sprite.explosion_horizontal_right_last2.getFxImage(),x+Sprite.SCALED_SIZE*rightLength,y);
        }
        if(topLength > 0) {
            gc.drawImage(Sprite.explosion_vertical_top_last2.getFxImage(),x,-Sprite.SCALED_SIZE*topLength + y);
        }
        if(bottomLength > 0){
            gc.drawImage(Sprite.explosion_vertical_down_last2.getFxImage(),x,Sprite.SCALED_SIZE*bottomLength + y);
        }

        int l = 1, r = 1, t = 1, b = 1;
        while(l < leftLength){
            gc.drawImage(Sprite.explosion_horizontal2.getFxImage(),x-Sprite.SCALED_SIZE*l,y);
            l++;
        }
        while(r < rightLength){
            gc.drawImage(Sprite.explosion_horizontal2.getFxImage(),x+Sprite.SCALED_SIZE*r,y);
            r++;
        }
        while (t < topLength) {
            gc.drawImage(Sprite.explosion_vertical2.getFxImage(), x,-Sprite.SCALED_SIZE*t + y);
            t++;
        }
        while (b < bottomLength) {
            gc.drawImage(Sprite.explosion_vertical2.getFxImage(), x,Sprite.SCALED_SIZE*b + y);
            b++;
        }
        gc.drawImage(exploded.get(2),x,y);
    }
    public void destroyBrick(int[][] Map) {
        int lx,ly,rx,ry,ux,uy,dx,dy;
        if(leftLength < length) {
             lx = (x-Sprite.SCALED_SIZE*(leftLength+1))/Sprite.SCALED_SIZE;
        } else {
            lx = (x-Sprite.SCALED_SIZE*(leftLength))/Sprite.SCALED_SIZE;
        }
        ly = y/Sprite.SCALED_SIZE;
        if(rightLength < length) {
            rx = (x+Sprite.SCALED_SIZE*(rightLength+1))/Sprite.SCALED_SIZE;
        } else {
            rx = (x+Sprite.SCALED_SIZE*(rightLength))/Sprite.SCALED_SIZE;
        }
         ry = y/Sprite.SCALED_SIZE;
         if(topLength < length) {
             uy =(-Sprite.SCALED_SIZE*(topLength+1) + y)/Sprite.SCALED_SIZE;
         } else {
             uy =(-Sprite.SCALED_SIZE*(topLength) + y)/Sprite.SCALED_SIZE;
         }
         ux = x/Sprite.SCALED_SIZE;
         dx = x/Sprite.SCALED_SIZE;
         if(bottomLength < length)
         {
             dy = (Sprite.SCALED_SIZE*(bottomLength+1) + y)/Sprite.SCALED_SIZE;
         } else {
             dy = (Sprite.SCALED_SIZE*(bottomLength) + y)/Sprite.SCALED_SIZE;
         }
        if(Map[ly][lx] == 2 || Map[ly][lx] == 3) {
            Map[ly][lx] = -1;
        }
        if(Map[ry][rx] == 2 || Map[ry][rx] == 3) {
             Map[ry][rx] = -1;
        }
        if(Map[uy][ux] == 2 || Map[uy][ux] == 3) {
            Map[uy][ux] = -1;
        }
        if(Map[dy][dx] == 2 || Map[dy][dx] == 3) {
            Map[dy][dx] = -1;
        }
    }
    public void explodeEnemy(List<Enemy> enemy) {
        for(int i = 0; i < enemy.size(); i++) {
            int topleftX = (enemy.get(i).getX() + 6);
            int topleftY = (enemy.get(i).getY() + 6);

            int toprightX = (enemy.get(i).getX() + Sprite.DEFAULT_SIZE + 12);
            int toprightY = (enemy.get(i).getY() + 6);

            int bottomleftX = (enemy.get(i).getX() + 6);
            int bottomleftY = (enemy.get(i).getY() + Sprite.DEFAULT_SIZE + 12);

            int bottomrightX = (enemy.get(i).getX() + Sprite.DEFAULT_SIZE + 12);
            int bottomrightY = (enemy.get(i).getY() + Sprite.DEFAULT_SIZE + 12);

            if (topleftX <= x + Sprite.SCALED_SIZE * rightLength && toprightX >= x - Sprite.SCALED_SIZE * leftLength &&
                    topleftY <= y + Sprite.SCALED_SIZE && bottomleftY >= y) {
                enemy.get(i).setAlive(false);
            }
            if (topleftX <= x + Sprite.SCALED_SIZE && toprightX >= x &&
                    topleftY <= Sprite.SCALED_SIZE * bottomLength + y && bottomleftY >= -Sprite.SCALED_SIZE * topLength + y) {
                enemy.get(i).setAlive(false);
            }

        }
    }
    public void explodeBomber(MovingObject bomber) {
        int topleftX = (bomber.getX() + 6);
        int topleftY = (bomber.getY() + 6);

        int toprightX = (bomber.getX() + Sprite.DEFAULT_SIZE + 12);
        int toprightY = (bomber.getY() + 6);

        int bottomleftX = (bomber.getX() + 6);
        int bottomleftY = (bomber.getY() + Sprite.DEFAULT_SIZE + 12);

        int bottomrightX = (bomber.getX() + Sprite.DEFAULT_SIZE + 12);
        int bottomrightY = (bomber.getY() + Sprite.DEFAULT_SIZE + 12);

        if (topleftX <= x + Sprite.SCALED_SIZE * (rightLength+1) && toprightX >= x - Sprite.SCALED_SIZE * leftLength &&
                topleftY <= y + Sprite.SCALED_SIZE && bottomleftY >= y) {
            bomber.setAlive(false);
        }
        if (topleftX <= x + Sprite.SCALED_SIZE && toprightX >= x &&
                topleftY <= Sprite.SCALED_SIZE * (bottomLength+1) + y && bottomleftY >= -Sprite.SCALED_SIZE * topLength + y) {
            bomber.setAlive(false);
        }
    }
    public void explodeBomb(List<Bomb> boms) {
        for(Bomb b : boms) {
            int topleftX = (b.getX() + 3);
            int topleftY = (b.getY() + 3);

            int toprightX = (b.getX() + Sprite.DEFAULT_SIZE + 12);
            int toprightY = (b.getY() + 3);

            int bottomleftX = (b.getX() + 3);
            int bottomleftY = (b.getY() + Sprite.DEFAULT_SIZE + 12);

            int bottomrightX = (b.getX() + Sprite.DEFAULT_SIZE + 12);
            int bottomrightY = (b.getY() + Sprite.DEFAULT_SIZE + 12);

            if (topleftX <= x + Sprite.SCALED_SIZE * (rightLength+1) && toprightX >= x - Sprite.SCALED_SIZE * leftLength &&
                    topleftY <= y + Sprite.SCALED_SIZE && bottomleftY >= y) {
                b.duration = 100;
            }
            if (topleftX <= x + Sprite.SCALED_SIZE && toprightX >= x &&
                    topleftY <= Sprite.SCALED_SIZE * (bottomLength+1) + y && bottomleftY >= -Sprite.SCALED_SIZE * topLength + y) {
                b.duration = 100;
            }
        }
    }
}
