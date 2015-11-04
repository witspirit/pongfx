package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Pong extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Pong");
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: black;");

        primaryStage.setScene(new Scene(canvas));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
