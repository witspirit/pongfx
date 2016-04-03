package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Random;

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


        Label speedLabel = new Label();
        speedLabel.setStyle("-fx-background-color: white");
        // canvas.getChildren().add(speedLabel); // Let's not show it for now

        // Feels a bit ridiculous... Can't I combine the two change listeners ?
        ball.xVelocityProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                speedLabel.textProperty().setValue(newValue + "/" + ball.yVelocityProperty().doubleValue());
            }
        });
        ball.yVelocityProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                speedLabel.textProperty().setValue(ball.xVelocityProperty().doubleValue() + "/" + newValue);
            }
        });

        Random random = new Random();


        // It turns out this is not really the bottom of the screen... Don't known why...
//        Line bottomLine = new Line(0, screenBounds.getHeight(), screenBounds.getWidth(), screenBounds.getHeight());
//        bottomLine.setStroke(Paint.valueOf("RED"));
//        canvas.getChildren().add(bottomLine);

        Scene mainScene = new Scene(canvas);
        primaryStage.setScene(mainScene);

        mainScene.setOnKeyPressed(ke -> {
            switch (ke.getCode()) {
                case Q :
                    System.out.println("Q pressed... Exiting");
                    primaryStage.close();
                    break;
                case W :
                    // System.out.println("W pressed... Moving P1 Up");
                    player1.up();
                    break;
                case S :
                    // System.out.println("S pressed... Moving P1 Down");
                    player1.down();
                    break;
                case I :
                    // System.out.println("I pressed... Moving P2 Up");
                    player2.up();
                    break;
                case K :
                    // System.out.println("K pressed... Moving P2 Down");
                    player2.down();
                    break;
                case R :
                    ball.reset();
                    break;
                case L: {
                    double randomAngle = random.nextDouble() * (Math.PI * 2);
                    ball.launch(randomAngle);
                    break;
                }
                case F :
                    ball.freeze();
                    break;
            }

        });

        mainScene.setOnKeyReleased(ke -> {
            switch (ke.getCode()) {
                case W : player1.stopUp(); break;
                case S : player1.stopDown(); break;
                case I : player2.stopUp(); break;
                case K : player2.stopDown(); break;
            }
        });

        AnimationLoop animation = new AnimationLoop(this::evaluateGame);
        animation.register(ball);
        animation.register(player1);
        animation.register(player2);
        animation.start();

        primaryStage.show();

    }

    private void evaluateGame() {
        if (ball.getBounds().getMinX() <= screenBounds.getMinX()) {
            // Bounce left
            System.out.println("Left hit detected");

            // Score for right player ?

            ball.freeze();
            ball.reset();
            player1.reset();
            player2.reset();

            // ball.goRight();
        } else if (ball.getBounds().getMaxX() >= screenBounds.getMaxX()) {
            // Bounce right
            System.out.println("Right hit detected");

            // Score for left player ?

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
