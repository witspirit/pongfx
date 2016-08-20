package be.witspirit.pongfx;

import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Represents the Winner/Loser feedback UI
 */
public class FeedbackUi {
    private GridPane topComponent;

    private StringProperty p1FeedbackProperty;
    private StringProperty p2FeedbackProperty;

    public FeedbackUi() {
        buildUi();
    }

    private void buildUi() {
        GridPane grid = new GridPane();
        RowConstraints rowConstraints = new RowConstraints();
        grid.getRowConstraints().add(rowConstraints);
        rowConstraints.setPercentHeight(100);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        grid.getColumnConstraints().addAll(columnConstraints, columnConstraints);

        VBox p1Feedback = new VBox();
        p1Feedback.setAlignment(Pos.CENTER);

        Label p1Label = new Label();
        p1FeedbackProperty = p1Label.textProperty();
        p1Label.setStyle("-fx-font-size: 30pt; -fx-text-fill : white");
        p1Feedback.getChildren().add(p1Label);

        grid.add(p1Feedback, 0, 0);

        VBox p2Feedback = new VBox();
        p2Feedback.setAlignment(Pos.CENTER);

        Label p2Label = new Label();
        p2FeedbackProperty = p2Label.textProperty();
        p2Label.setStyle("-fx-font-size: 30pt; -fx-text-fill : white");
        p2Feedback.getChildren().add(p2Label);

        grid.add(p2Feedback, 1, 0);

        topComponent = grid;
    }

    public FeedbackUi addToContainer(Pane container) {
        container.getChildren().add(topComponent);
        return this;
    }

    public StringProperty p1FeedbackProperty() {
        return p1FeedbackProperty;
    }

    public StringProperty p2FeedbackProperty() {
        return p2FeedbackProperty;
    }
}
