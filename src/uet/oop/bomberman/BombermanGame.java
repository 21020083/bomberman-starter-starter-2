package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.item.ItemList;
import uet.oop.bomberman.entities.movingobject.*;
import uet.oop.bomberman.entities.movingobject.pathfinder.pathfinder;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static int[][] Map = new int [13][31];
    public static final int HEIGHT = 13;
    public String path = "/levels/level.txt";
    
    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Enemy> enemy = new ArrayList<>();
    public static Bomber bomberman = new Bomber(1,1,Sprite.player_right.getFxImage());
    public static ItemList items = new ItemList();

    public Balloom balloom = new Balloom(13,2,Sprite.balloom_left3.getFxImage()) ;
    public Balloom balloom1 = new Balloom(12,2,Sprite.balloom_left3.getFxImage()) ;
    public Balloom balloom2 = new Balloom(10,2,Sprite.balloom_left3.getFxImage()) ;
    public Balloom balloom3 = new Balloom(13,6,Sprite.balloom_left3.getFxImage()) ;

    public Kondoria kondoria = new Kondoria(27,6,Sprite.kondoria_right1.getFxImage());
    public List<Entity> stillObjects = new ArrayList<>();
    private List<Brick> brick  =new ArrayList<>();



    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();


        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);


        // Them scene vao stage
        stage.setScene(scene);

        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                bomberman.eventHandle(scene);
                render();
                update();

            }
        };
        timer.start();
        loadMap();


        enemy.add(balloom);
        enemy.add(kondoria);
        enemy.add(balloom1);
        enemy.add(balloom2);
        enemy.add(balloom3);
        enemy.add(new Minvo(9,9,Sprite.minvo_right2.getFxImage()));


    }
    public void loadMap() {
        try {
            InputStream in = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            char[] cbuf = new char[31];
            int i = 0;
            while(i < 13) {
                String line = br.readLine();
                String numbers[] = line.split(" ");
                for(int j = 0; j < 31; j++) {
                    int num = Integer.parseInt(numbers[j]);
                    if(num == 0){
                        stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
                    } else if (num == 2) {
                        stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        brick.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    } else if ( num == 3) {
                        stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        brick.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        items.add(j,i);
                    } else {
                        stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    }
                    Map[i][j] = num;
                }
                i++;
            }
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
        bomberman.update();
        items.update();
        if(bomberman.isDeath()) {
            bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        }
        updateMap();

    }

    public void render() {
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g->g.render(gc));
        items.render(gc);
        brick.forEach(g->g.render(gc));
        enemy.forEach(g -> g.render(gc));
        bomberman.render(gc);

    }
}
