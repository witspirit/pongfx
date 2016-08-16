package be.witspirit.pongfx;

import be.witspirit.pongfx.state.StateMachine;
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

    private int targetScore = 5;
    private Player lastScore = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Pong");

        StackPane overallPane = new StackPane();

        Pane canvas = buildGameArea();
        overallPane.getChildren().add(canvas);

        Pane controls = buildUiControls();
        overallPane.getChildren().add(controls);

        Pane feedback = buildFeedbackUi();
        // overallPane.getChildren().add(feedback);

        RandomSource random = new RandomSource();

        // It turns out this is not really the bottom of the screen... Don't known why...
//        Line bottomLine = new Line(0, screenBounds.getHeight(), screenBounds.getWidth(), screenBounds.getHeight());
//        bottomLine.setStroke(Paint.valueOf("RED"));
//        canvas.getChildren().add(bottomLine);

        StateMachine stateMachine = new StateMachine(screenBounds, ball, player1, player2);

        Scene mainScene = new Scene(overallPane);
        primaryStage.setScene(mainScene);

        KeyMap keyMap = new KeyMap();
        keyMap.register(KeyCode.Q, new PressAction(primaryStage::close));

        keyMap.register(KeyCode.W, new PressAndReleaseAction(player1::up, player1::stopUp));
        keyMap.register(KeyCode.S, new PressAndReleaseAction(player1::down, player1::stopDown));

        keyMap.register(KeyCode.I, new PressAndReleaseAction(player2::up, player2::stopUp));
        keyMap.register(KeyCode.K, new PressAndReleaseAction(player2::down, player2::stopDown));

        keyMap.register(KeyCode.R, new PressAction(ball::reset));
        keyMap.register(KeyCode.SPACE, new PressAction(stateMachine::launch));
        keyMap.register(KeyCode.P, new PressAction(ball::freeze));

        keyMap.listenOn(mainScene);

        AnimationLoop animation = new AnimationLoop(stateMachine::evaluateGame);
        animation.register(ball);
        animation.register(player1);
        animation.register(player2);
        animation.start();

        primaryStage.show();

    }

    private Pane buildFeedbackUi() {
        GridPane grid = new GridPane();
        RowConstraints rowConstraints = new RowConstraints();
        grid.getRowConstraints().add(rowConstraints);
        rowConstraints.setPercentHeight(100);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        grid.getColumnConstraints().addAll(columnConstraints, columnConstraints);

        VBox p1Feedback = new VBox();
        p1Feedback.setAlignment(Pos.CENTER);

        Label p1Label = new Label("Winner");
        p1Label.setStyle("-fx-font-size: 30pt; -fx-text-fill : white");
        p1Feedback.getChildren().add(p1Label);

        grid.add(p1Feedback, 0, 0);

        VBox p2Feedback = new VBox();
        p2Feedback.setAlignment(Pos.CENTER);

        Label p2Label = new Label("Loser");
        p2Label.setStyle("-fx-font-size: 30pt; -fx-text-fill : white");
        p2Feedback.getChildren().add(p2Label);

        grid.add(p2Feedback, 1, 0);

        return grid;
    }

    private Pane buildUiControls() {
        BorderPane controlPane = new BorderPane();

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

        player1 = new Player(new Point2D(PLAYER_SCREEN_OFFSET, screenBounds.getHeight() / 2), screenBounds.getMinY(), screenBounds.getMaxY());
        canvas.getChildren().add(player1.getNode());

        player2 = new Player(new Point2D(screenBounds.getMaxX() - PLAYER_SCREEN_OFFSET, screenBounds.getHeight() / 2), screenBounds.getMinY(), screenBounds.getMaxY());
        canvas.getChildren().add(player2.getNode());
        return canvas;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
