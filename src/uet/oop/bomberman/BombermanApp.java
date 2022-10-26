package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class BombermanApp extends Application {


    public static GraphicsContext gc;
    public static Canvas canvas;
    public static Scene Game;
    public static Group gameRoot;
    public static BombermanGame Gameplay;
    private java.util.List<Text> textList = new ArrayList<>();
    private Text Score;
    private Text time;
    private Text stage;
    private Text health;

    private static final  int Menu_Width = BombermanGame.WIDTH * Sprite.SCALED_SIZE;
    private static final  int Menu_Height = (BombermanGame.HEIGHT+2) * Sprite.SCALED_SIZE;
    private  Stage finalStage;
    private AnchorPane MenuPane;
    private Scene Menu;
    private Stage MenuStage;
    private Button Continue;
    private Button Start;
    private AnimationTimer timer;
    public static void main(String[] args) {
        Application.launch(BombermanApp.class);
    }
    @Override
    public void start(Stage stage) throws Exception {
        CreateMenu();
        stage = MenuStage;
        finalStage = stage;
        finalStage.show();
        Start.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    try {
                        initNewGame();
                        createTextScene();
                        InitStage();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    finalStage.setScene(Game);

                     timer = new AnimationTimer() {
                        @Override
                        public void handle(long l) {
                            if (BombermanGame.health == 0 || BombermanGame.Countdown < 0) {
                                String res = "Game Over !!!";
                                Gameover(res);
                                Sound.stop();

                            } else if (BombermanGame.nextStage) {
                                try {
                                    initNextStage();
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                update();
                                Gameplay.render();
                                Gameplay.update();
                                BombermanGame.bomberman.eventHandle(Game);
                            }

                        }
                    };
                    timer.start();
                }
            }
        });

    }
    public void initNewGame() throws FileNotFoundException {
        Gameplay = new BombermanGame();
        Gameplay.Score = 0;
        Gameplay.level = 1;
    }
    public void initNextStage() throws FileNotFoundException {
        if(BombermanGame.level == BombermanGame.Maxlevel) {
            Gameover(" YOU WIN ");
        } else {
            BombermanGame.nextStage = false;
            BombermanGame.level++;
            createStagescene();
            Gameplay.loadMap();
            BombermanGame.bomberman.setX(Sprite.SCALED_SIZE);
            BombermanGame.bomberman.setY(Sprite.SCALED_SIZE);
        }

    }
    public void createTextScene() {
        textList.clear();
        int posY = 460;
        Text textS = new Text(30, posY, "Score: ");
        Text textT = new Text(230, posY, "Time: ");

        textS.setFill(Color.BLACK);
        textS.setFont(new Font(20));

        textT.setFill(Color.BLACK);
        textT.setFont(new Font(20));

        textList.add(textS);
        textList.add(textT);

        Score = new Text(100, posY, String.valueOf(BombermanGame.Score));
        Score.setFill(Color.BLACK);
        Score.setFont(new Font(20));

        textList.add(Score);

        time = new Text(290, posY, String.valueOf(BombermanGame.Countdown / 60));
        time.setFill(Color.BLACK);
        time.setFont(new Font(20));

        textList.add(time);

        Text textL = new Text(600, posY, "Level: ");
        textL.setFill(Color.BLACK);
        textL.setFont(new Font(20));
        textList.add(textL);

        stage = new Text(670, posY, String.valueOf(BombermanGame.level));
        stage.setFill(Color.BLACK);
        stage.setFont(new Font(20));
        textList.add(stage);

        Text textLeft = new Text(400, posY, "Left: ");
        textLeft.setFill(Color.BLACK);
        textLeft.setFont(new Font(20));
        textList.add(textLeft);

        health = new Text(440, posY, String.valueOf(BombermanGame.health));
        health.setFill(Color.BLACK);
        health.setFont(new Font(20));
        textList.add(health);


    }
    public void update() {
        Score.setText(String.valueOf(BombermanGame.Score));
        time.setText(String.valueOf(BombermanGame.Countdown-- / 60));
        health.setText(String.valueOf(BombermanGame.health));
    }
    private void InitStage() {
        canvas = new Canvas(Sprite.SCALED_SIZE * BombermanGame.WIDTH, Sprite.SCALED_SIZE * (BombermanGame.HEIGHT + 2));
        gc = canvas.getGraphicsContext2D();

        gameRoot = new Group();
        gameRoot.getChildren().add(canvas);
        gameRoot.getChildren().addAll(textList);

        Game = new Scene(gameRoot, Sprite.SCALED_SIZE * BombermanGame.WIDTH, Sprite.SCALED_SIZE * (BombermanGame.HEIGHT + 2), Color.BLACK);
        createStagescene();
    }

    private void CreateMenu() {
        MenuPane = new AnchorPane();
        Menu = new Scene(MenuPane,Menu_Width,Menu_Height);
        MenuStage = new Stage();
        MenuStage.setScene(Menu);
        createBackGround();
        CreateContinueButton();
        CreateStartButton();
    }
    private void CreateStartButton() {
        InputStream input = getClass().getResourceAsStream("/button/start.png");
        InputStream input1 = getClass().getResourceAsStream("/button/arrow.png");

        Image image = new Image(input);
        Image arrow = new Image(input1);
        ImageView imageView = new ImageView(image);
        ImageView imageViewArrow = new ImageView(arrow);
        imageViewArrow.setFitHeight(16);
        imageViewArrow.setPreserveRatio(true);
        Start = new Button("", imageView);
        Start.setStyle("-fx-background-color: #000000; ");

        Button Arrow = new Button("", imageViewArrow);
        Arrow.setStyle("-fx-background-color: #000000; ");
        Arrow.setLayoutX(425);
        Arrow.setLayoutY(350);
        Arrow.setVisible(false);

        MenuPane.getChildren().addAll(Start,Arrow);
        Start.setLayoutX(430);
        Start.setLayoutY(350);
        Start.setOnMouseEntered(mouseEvent -> {
            Start.setTranslateX(20);
            Arrow.setVisible(true);
        });
        Start.setOnMouseExited(mouseEvent -> {
            Start.setTranslateX(0);
            Arrow.setVisible(false);
        });
    }
    private void CreateContinueButton() {
        InputStream input = getClass().getResourceAsStream("/button/continue.png");

        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        Continue = new Button("", imageView);
        Continue.setStyle("-fx-background-color: #000000; ");

        MenuPane.getChildren().add(Continue);
        Continue.setLayoutX(430);
        Continue.setLayoutY(400);
    }
    private void createBackGround() {
        Image back = new Image("/Background/background.png", Menu_Width, Menu_Height, false, true);
        BackgroundImage backMenu = new BackgroundImage(back, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        MenuPane.setBackground(new Background(backMenu));
    }
    private void Gameover(String s) {
        timer.stop();
        Group Root = new Group();
        Text textOver = new Text(250, 240, s);

        textOver.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        textOver.setFill(Color.WHITE);

        Rectangle rec = new Rectangle(Menu_Width,Menu_Height);
        rec.setFill(Color.BLACK);

        Root.getChildren().addAll(rec,textOver);
        gameRoot.getChildren().add(Root);

        FadeTransition ft = new FadeTransition(new Duration(3000),Game.getRoot());
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(evt -> finalStage.setScene(Menu));
        ft.play();
    }
    private void createStagescene() {

        Sound.stop();

        String res = "STAGE " + String.valueOf(BombermanGame.level);
        Text textOver = new Text(300, 240, res);

        textOver.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        textOver.setFill(Color.WHITE);

        Rectangle rec = new Rectangle(Menu_Width,Menu_Height);
        rec.setFill(Color.BLACK);

        Group st = new Group();
        st.getChildren().addAll(rec,textOver);
        gameRoot.getChildren().addAll(st);
        Sound.Startstage();


        FadeTransition ft = new FadeTransition(new Duration(4200),st);
        ft.setFromValue(1);
        ft.setToValue(1);
        ft.setOnFinished(evt-> {
            gameRoot.getChildren().remove(st);
            Sound.stop();
            Sound.play(Sound.BGM);
        });
        ft.play();



    }
}
