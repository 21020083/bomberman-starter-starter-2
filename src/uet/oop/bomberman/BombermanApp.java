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
    public static void main(String[] args) {
        Application.launch(BombermanApp.class);
    }
    @Override
    public void start(Stage stage) throws Exception {
        CreateMenu();
        stage = MenuStage;
        finalStage = stage;
        stage.show();
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
                    createStagescene();
                    finalStage.show();


                    AnimationTimer timer = new AnimationTimer() {
                        @Override
                        public void handle(long l) {

                            Gameplay.render();
                            Gameplay.update();
                            BombermanGame.bomberman.eventHandle(Game);
                            if (!BombermanGame.bomberman.isAlive()  || BombermanGame.Countdown < 0) {
                                String res = "Game Over !!!";
                                Gameover(res);
                            }
                            if (BombermanGame.gameover) {
                                BombermanGame.level++;
                                String res = "YOU WIN !!!";
                                Gameover(res);
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
    public void createTextScene() {
        int posY = 460;
        Text textS = new Text(30, posY, "Score: ");
        Text textT = new Text(230, posY, "Time: ");

        textS.setFill(Color.WHITE);
        textS.setFont(new Font(20));

        textT.setFill(Color.WHITE);
        textT.setFont(new Font(20));

        textList.add(textS);
        textList.add(textT);

        Score = new Text(130, posY, String.valueOf(BombermanGame.Score));
        Score.setFill(Color.WHITE);
        Score.setFont(new Font(20));

        textList.add(Score);

        time = new Text(290, posY, String.valueOf(BombermanGame.Countdown / 60));
        time.setFill(Color.WHITE);
        time.setFont(new Font(20));

        textList.add(time);

        Text textL = new Text(600, posY, "Level: ");
        textL.setFill(Color.WHITE);
        textL.setFont(new Font(20));
        textList.add(textL);

        stage = new Text(670, posY, String.valueOf(BombermanGame.level));
        stage.setFill(Color.WHITE);
        stage.setFont(new Font(20));
        textList.add(stage);

        Text textLeft = new Text(400, posY, "Left: ");
        textLeft.setFill(Color.WHITE);
        textLeft.setFont(new Font(20));
        textList.add(textLeft);


    }
    private void InitStage() {
        canvas = new Canvas(Sprite.SCALED_SIZE * BombermanGame.WIDTH, Sprite.SCALED_SIZE * (BombermanGame.HEIGHT + 2));
        gc = canvas.getGraphicsContext2D();

        gameRoot = new Group();
        gameRoot.getChildren().add(canvas);
        gameRoot.getChildren().addAll(textList);

        Game = new Scene(gameRoot, Sprite.SCALED_SIZE * BombermanGame.WIDTH, Sprite.SCALED_SIZE * (BombermanGame.HEIGHT + 2), Color.BLACK);
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

        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        Start = new Button("", imageView);
        Start.setStyle("-fx-background-color: #000000; ");

        MenuPane.getChildren().add(Start);
        Start.setLayoutX(430);
        Start.setLayoutY(350);
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
        String res = "STAGE " + String.valueOf(BombermanGame.level);
        Text textOver = new Text(300, 240, res);

        textOver.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        textOver.setFill(Color.WHITE);
        Rectangle rec = new Rectangle(Menu_Width,Menu_Height);
        rec.setFill(Color.BLACK);
        Group st = new Group();
        st.getChildren().addAll(rec,textOver);
        gameRoot.getChildren().addAll(st);
        FadeTransition ft = new FadeTransition(new Duration(2000),st);
        ft.setFromValue(1);
        ft.setToValue(1);
        ft.setOnFinished(evt-> st.setVisible(false));
        ft.play();

    }
}
