package sample;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;

public class Pong extends Application {

    public static final int PLAYER_WIDTH = 50;
    public static final int PLAYER_HEIGHT = 200;
    public static final Paint PLAYER_COLOR = Paint.valueOf("WHITE");
    public static final int PLAYER_SCREEN_OFFSET = 100;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Pong");
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: black;");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        System.out.println("Screen Height/Width: "+screenBounds.getHeight()+"/"+screenBounds.getWidth());

        Player player1 = Player.left(screenBounds);
        canvas.getChildren().add(player1.getNode());

        Player player2 = Player.right(screenBounds);
        canvas.getChildren().add(player2.getNode());

        Ball ball = Ball.create(screenBounds);
        canvas.getChildren().add(ball.getNode());

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
                case L:
                    ball.launch();
                    break;
                case F :
                    ball.freeze();
                    break;
            }

        });


        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
