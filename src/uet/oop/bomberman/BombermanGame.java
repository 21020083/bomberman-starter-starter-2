package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.movingobject.Balloom;
import uet.oop.bomberman.entities.movingobject.Bomber;
import uet.oop.bomberman.entities.movingobject.Kondoria;
import uet.oop.bomberman.entities.movingobject.MovingObject;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static char[][] Map = new char[13][31];
    public static final int HEIGHT = 13;
    public String path = "/levels/Level1.txt";
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<MovingObject> enemy = new ArrayList<>();
    private Bomber bomberman = new Bomber(1,1,Sprite.player_right.getFxImage());
    private Balloom balloom = new Balloom(13,2,Sprite.balloom_left3.getFxImage()) ;
    private Kondoria kondoria = new Kondoria(27,6,Sprite.kondoria_right1.getFxImage());
    private List<Entity> stillObjects = new ArrayList<>();



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
                long end = System.nanoTime();
                System.out.println(stillObjects.size());
            }
        };
        timer.start();
        loadMap();
        createMap();

        enemy.add(balloom);
        enemy.add(kondoria);

    }
    public void loadMap() {
        try {
            InputStream in = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            char[] cbuf = new char[31];
            int i = 0;
            while(i < 13) {
                cbuf = br.readLine().toCharArray();
                for(int j = 0; j < 31; j++) {
                    Map[i][j] = cbuf[j];
                }
                i++;
            }
            in.close();
            br.close();
        }
        catch (Exception e){

        }
    }

    public void createMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (Map[j][i]== '#') {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }  else if ( Map[j][i] == '*') {
                    object = new Brick(i, j);
                } else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    public void update() {
        enemy.forEach(Entity::update);
        bomberman.update();

    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        enemy.forEach(g -> g.render(gc));
        bomberman.render(gc);
    }
}
