package be.witspirit.pongfx;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Pong extends Application {

    private static int PLAYER_SCREEN_OFFSET = 100;

    private Rectangle2D screenBounds;

    private Ball ball;
    private Player player1;
    private Player player2;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Pong");

        StackPane overallPane = new StackPane();

        Pane canvas = buildGameArea();
        overallPane.getChildren().add(canvas);

        Pane controls = buildUiControls();
        overallPane.getChildren().add(controls);

        RandomSource random = new RandomSource();

        // It turns out this is not really the bottom of the screen... Don't known why...
//        Line bottomLine = new Line(0, screenBounds.getHeight(), screenBounds.getWidth(), screenBounds.getHeight());
//        bottomLine.setStroke(Paint.valueOf("RED"));
//        canvas.getChildren().add(bottomLine);

        Scene mainScene = new Scene(overallPane);
        primaryStage.setScene(mainScene);

        KeyMap keyMap = new KeyMap();
        keyMap.register(KeyCode.Q, new PressAction(primaryStage::close));

        keyMap.register(KeyCode.W, new PressAndReleaseAction(player1::up, player1::stopUp));
        keyMap.register(KeyCode.S, new PressAndReleaseAction(player1::down, player1::stopDown));

        keyMap.register(KeyCode.I, new PressAndReleaseAction(player2::up, player2::stopUp));
        keyMap.register(KeyCode.K, new PressAndReleaseAction(player2::down, player2::stopDown));

        keyMap.register(KeyCode.R, new PressAction(ball::reset));
        keyMap.register(KeyCode.L, new PressAction(() -> ball.launch(random.angle())));
        keyMap.register(KeyCode.F, new PressAction(ball::freeze));

        keyMap.listenOn(mainScene);

        AnimationLoop animation = new AnimationLoop(this::evaluateGame);
        animation.register(ball);
        animation.register(player1);
        animation.register(player2);
        animation.start();

        primaryStage.show();

    }

    private Pane buildUiControls() {
        BorderPane controlPane = new BorderPane();
        Background background = new Background(new BackgroundFill(Color.CYAN, null, null));

        GridPane scorePane = new GridPane();
        scorePane.setPrefHeight(150);
        scorePane.setHgap(400);
        scorePane.setAlignment(Pos.CENTER);
        controlPane.setTop(scorePane);

        scorePane.add(scoreUi(player1), 0, 0);
        scorePane.add(scoreUi(player2), 1, 0);

        return controlPane;
    }

    private Pane scoreUi(Player player) {
        BorderPane scoreArea = new BorderPane();
        Label score = new Label();
        score.setStyle("-fx-font-size: 30pt; -fx-text-fill : white");
        score.textProperty().bind(Bindings.convert(player.scoreProperty()));
        scoreArea.setCenter(score);
        return scoreArea;
    }


    private Pane buildGameArea() {
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: black;");

        screenBounds = Screen.getPrimary().getVisualBounds();
        System.out.println("Screen Width/Height: "+ screenBounds.getWidth()+"/"+ screenBounds.getHeight());
        Point2D screenCenter = new Point2D(screenBounds.getWidth() / 2, screenBounds.getHeight() / 2);

        ball = new Ball(screenCenter);
        canvas.getChildren().add(ball.getNode());

        player1 = new Player(new Point2D(PLAYER_SCREEN_OFFSET, screenBounds.getHeight() / 2));
        canvas.getChildren().add(player1.getNode());

        player2 = new Player(new Point2D(screenBounds.getMaxX() - PLAYER_SCREEN_OFFSET, screenBounds.getHeight() / 2));
        canvas.getChildren().add(player2.getNode());
        return canvas;
    }

    private void evaluateGame() {
        if (ball.getBounds().getMinX() <= screenBounds.getMinX()) {
            // Bounce left
            System.out.println("Left hit detected");

            // Score for right player ?
            player2.scoreProperty().set(player2.scoreProperty().get()+1);

            ball.freeze();
            ball.reset();
            player1.reset();
            player2.reset();

            // ball.goRight();
        } else if (ball.getBounds().getMaxX() >= screenBounds.getMaxX()) {
            // Bounce right
            System.out.println("Right hit detected");

            // Score for left player ?
            player1.scoreProperty().set(player1.scoreProperty().get()+1);

            ball.freeze();
            ball.reset();
            player1.reset();
            player2.reset();

            // ball.goLeft();
        } else if (ball.getBounds().getMinY() <= screenBounds.getMinY()) {
            // Bounce top
            System.out.println("Top hit detected");

            ball.goDown();

        } else if (ball.getBounds().getMaxY() >= screenBounds.getMaxY()) {
            // Bounce bottom
            System.out.println("Bottom hit detected");

            ball.goUp();
        }

        onBounce(player1, ball::goRight);
        onBounce(player2, ball::goLeft);

        checkScreenBounds(player1);
        checkScreenBounds(player2);
    }

    private void onBounce(Player player, Runnable action) {
        if (player.getBounds().intersects(ball.getBounds())) {
            action.run();
        }
    }

    private void checkScreenBounds(Player player) {
        if (player.getBounds().getMinY() <= screenBounds.getMinY()) {
            player.setMinYPosition(screenBounds.getMinY());
        } else if (player.getBounds().getMaxY() >= screenBounds.getMaxY()) {
            player.setMaxYPosition(screenBounds.getMaxY());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
