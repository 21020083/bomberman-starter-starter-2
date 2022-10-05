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
    public static Entity[][] Map = new Entity[13][31];
    public static final int HEIGHT = 13;
    public String path = "/levels/Level1.txt";
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<MovingObject> enemy = new ArrayList<>();
    private Bomber bomberman = new Bomber(1,1,Sprite.player_right.getFxImage());
    private Balloom balloom = new Balloom(13,2,Sprite.balloom_left3.getFxImage()) ;
    private Kondoria kondoria = new Kondoria(27,6,Sprite.kondoria_right1.getFxImage());
    private List<Entity> stillObjects = new ArrayList<>();
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
                    if(cbuf[j] == '*'){
                        Map[i][j] = new Brick(j, i, Sprite.brick.getFxImage());
                        brick.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    } else if (cbuf[j] == '#') {
                        Map[i][j] = new Wall(j, i, Sprite.wall.getFxImage());
                    } else {
                        Map[i][j] = new Grass(j, i, Sprite.grass.getFxImage());
                    }
                }
                i++;
            }
            in.close();
            br.close();
        }
        catch (Exception e){

        }
    }

    public void renderMap(GraphicsContext gc) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Map[i][j].render(gc);
            }
        }
    }

    public void update() {
        enemy.forEach(Entity::update);
        bomberman.update();
        //brick.forEach(Brick::update);
        if(bomberman.ContactwithEnemy(enemy)) {
            bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        }

    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderMap(gc);
        //brick.forEach(g->g.render(gc));
        enemy.forEach(g -> g.render(gc));
        bomberman.render(gc);

    }
}
