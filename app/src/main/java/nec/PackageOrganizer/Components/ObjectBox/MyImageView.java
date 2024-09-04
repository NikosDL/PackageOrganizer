package nec.PackageOrganizer.Components.ObjectBox;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class MyImageView extends StackPane {

    private ImageView imageView;

    private SimpleBooleanProperty connectionInProgress = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty connected = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty defaultState = new SimpleBooleanProperty();

    public MyImageView(Image image) {
        super();
        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(this.heightProperty());
        imageView.fitWidthProperty().bind(this.widthProperty());
        imageView.setMouseTransparent(true);
        this.getChildren().add(imageView);

        defaultState.bind(Bindings.not(connectionInProgress.and(connected)));

        this.setOnMouseEntered(e -> {
            this.getChildren().addLast(new MyImageViewHoverOverlay());
        });

        this.setOnMouseExited(e -> {
            if (this.getChildren().getLast() instanceof MyImageViewHoverOverlay) {
                this.getChildren().removeLast();
            }
        });

        this.setOnMousePressed(e -> {
            if (this.getChildren().getLast() instanceof MyImageViewHoverOverlay) {
                this.getChildren().getLast().setOpacity(0.7);
            }
        });

        this.setOnMouseReleased(e -> {
            if (this.getChildren().getLast() instanceof MyImageViewHoverOverlay) {
                this.getChildren().getLast().setOpacity(0.5);
            }
        });
    }

    class MyImageViewHoverOverlay extends Pane {

        private MyImageViewHoverOverlay() {
            super();
            this.setBackground(Background.fill(Color.WHITE));
            this.setOpacity(0.5);
            this.setMouseTransparent(true);
        }
    }

    public SimpleBooleanProperty isDefaultStateProperty() {
        return this.defaultState;
    }
}
