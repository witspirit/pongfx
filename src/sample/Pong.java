package sample;

import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;

public class Pong extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Pong");
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: black;");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        System.out.println("Screen Width/Height: "+screenBounds.getWidth()+"/"+screenBounds.getHeight());

        Player player1 = Player.left(screenBounds);
        canvas.getChildren().add(player1.getNode());

        Player player2 = Player.right(screenBounds);
        canvas.getChildren().add(player2.getNode());

        Ball ball = Ball.create(screenBounds);
        canvas.getChildren().add(ball.getNode());

        Label speedLabel = new Label();
        speedLabel.setStyle("-fx-background-color: white");
        // canvas.getChildren().add(speedLabel); // Let's not show it for now

        // Feels a bit ridiculous... Can't I combine the two change listeners ?
        ball.xVelocityProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                speedLabel.textProperty().setValue(newValue + "/" + ball.getyVelocity());
            }
        });
        ball.yVelocityProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                speedLabel.textProperty().setValue(ball.getxVelocity() + "/" + newValue);
            }
        });


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

        mainScene.setOnKeyReleased(ke -> {
            switch (ke.getCode()) {
                case W : player1.stopUp(); break;
                case S : player1.stopDown(); break;
                case I : player2.stopUp(); break;
                case K : player2.stopDown(); break;
            }
        });

        AnimationLoop animation = new AnimationLoop();
        animation.register(ball);
        animation.register(player1);
        animation.register(player2);
        animation.start();

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
