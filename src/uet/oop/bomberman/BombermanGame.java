package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.item.ItemList;
import uet.oop.bomberman.entities.movingobject.*;
import uet.oop.bomberman.entities.movingobject.enemy.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.BombermanApp.canvas;
import static uet.oop.bomberman.BombermanApp.gc;

public class BombermanGame {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static final int DEFAULT = 16;
    public static int[][] Map = new int [13][31];

    public static boolean nextStage = false;
    public static int Score = 0;
    public static int Countdown = 210*60;
    public static int level = 1;
    public static final int Maxlevel = 3;
    public static int health = 3;
    public String path;

    public static ImageView imgView;
    public static List<Enemy> enemy = new ArrayList<>();
    public static Bomber bomberman;
    public static List<Brick> brick = new ArrayList<>();
    public static ItemList items = new ItemList();
    public List<Entity> stillObjects = new ArrayList<>();

    public BombermanGame() {
        loadMap();
        bomberman = new Bomber(1,1,Sprite.player_right_2.getFxImage());
        bomberman.setX(Sprite.SCALED_SIZE);
        bomberman.setY(Sprite.SCALED_SIZE);
    }
    public void init() {
        stillObjects.clear();
        brick.clear();
        enemy.clear();
        items  = new ItemList();
        path = "/levels/level"  + level +  ".txt";
    }
    public void reset(){
        Countdown = 210*60;
        health = 3;
    }
    public void loadMap() {
        try {
            reset();
            init();
            InputStream in = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int i = 0;
            while(i < 13) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                for(int j = 0; j < 31; j++) {
                    int num = Integer.parseInt(numbers[j]);
                    if(num == 0){
                        stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
                    } else if (num == 2) {
                        stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        brick.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    } else if (num == 3) {
                        stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        brick.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        items.add(j,i);
                    } else if (num == 4) {
                        enemy.add(new Balloom(j,i,Sprite.balloom_left2.getFxImage()));
                    } else if (num == 5) {
                        enemy.add(new Kondoria(j,i,Sprite.kondoria_right1.getFxImage()));
                    } else if(num == 6) {
                        enemy.add(new Oneal(j, i,Sprite.oneal_right1.getFxImage()));
                    } else if( num == 7) {
                        enemy.add(new Minvo(j , i, Sprite.minvo_right2.getFxImage()));
                    } else if(num == 8) {
                        enemy.add(new Ovape(j , i, Sprite.ovape_right1.getFxImage()));
                    } else if(num == 9) {
                        enemy.add(new Doll(j, i , Sprite.doll_left2.getFxImage()));
                    } else {
                        stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    }
                    if(num >= 4) {
                        stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        Map[i][j] = 1;
                    } else Map[i][j] = num;
                }
                i++;
            }
            items.setDoor();
            in.close();
            br.close();
        }
        catch (Exception e){

        }
    }
    public void updateMap(){
        for(int i = 0; i < brick.size(); i++) {
            int dx = brick.get(i).getX()/Sprite.SCALED_SIZE;
            int dy = brick.get(i).getY()/Sprite.SCALED_SIZE;
            if (BombermanGame.Map[dy][dx] == -1) {
                brick.get(i).destroyed = true;
            }
            brick.get(i).update();
            if(brick.get(i).remove) {
                brick.remove(i);
                i--;
                BombermanGame.Map[dy][dx] = 1;
            }
        }
    }


    public void updateEnemy() {
        for(int i = 0; i < enemy.size(); i++) {
            if(!enemy.get(i).isDeath()) {
                enemy.get(i).update();
            } else {
                enemy.remove(i);
                i--;
            }
        }
    }

    public void update() {
        updateEnemy();
        updateMap();
        bomberman.update();
        items.update();
    }

    public void render() {
        gc.setFill(Color.SILVER);
        gc.fillRect(0,0 ,WIDTH*Sprite.SCALED_SIZE,(HEIGHT+2)*Sprite.SCALED_SIZE);
        stillObjects.forEach(g->g.render(BombermanApp.gc));
        items.render(BombermanApp.gc);
        brick.forEach(g->g.render(BombermanApp.gc));
        enemy.forEach(g -> g.render(BombermanApp.gc));
        bomberman.render(BombermanApp.gc);

    }
}
