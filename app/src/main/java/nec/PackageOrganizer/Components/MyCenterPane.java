package nec.PackageOrganizer.Components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import nec.PackageOrganizer.Components.Statics.MyComponent;
import nec.PackageOrganizer.Controllers.MyController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import nec.PackageOrganizer.Components.Canvas.MyCanvas;
import nec.PackageOrganizer.Components.ObjectBox.MyImageView;

public final class MyCenterPane
        extends Pane
        implements MyComponent {

    private MyController controller;
    private SimpleStringProperty address = new SimpleStringProperty("");
    private SimpleBooleanProperty connectionSetUpInProgress = new SimpleBooleanProperty(false);

    public MyCenterPane(MyController controller) {
        super();

        this.controller = controller;

        address.addListener(e -> setUpGUI());
    }

    private void setUpGUI() {
        this.setPrefSize(2000, 2000);
        this.setBackground(Background.fill(Color.WHEAT));

        this.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getTarget() instanceof MyImageView
                    && ((MyImageView) e.getTarget())
                            .isDefaultStateProperty().get()) {
                MyImageView target = (MyImageView) e.getTarget();
                openConnectionLine(e);
            }
        });
    }

    @Override
    public String getAddress() {
        return this.address.getValue();
    }

    @Override
    public void setAddress(String address) {
        this.address.setValue(address);
    }

    private void openConnectionLine(MouseEvent e) {
        MyCanvas canvas = new MyCanvas();
        MyImageView target = (MyImageView) e.getTarget();

        // Find the edge of the imageView in the local coordinate system
        Bounds imageViewBounds = target.getBoundsInLocal();
        Bounds imageViewBoundsScreen = target.localToScreen(imageViewBounds);
        Bounds imageViewBoundsThis = this.screenToLocal(imageViewBoundsScreen);
        double imageViewEdgeX = (double) imageViewBoundsThis.getMaxX();
        double imageViewCenterY = (double) imageViewBoundsThis.getCenterY();

        // Bind canvas position with the edge of the imageview
//        canvas.layoutXProperty().bind(new SimpleDoubleProperty(imageViewEdgeX - 50));
//        canvas.layoutYProperty().bind(new SimpleDoubleProperty(imageViewCenterY - 50));
        canvas.layoutXProperty().set(imageViewEdgeX - 50);
        canvas.layoutYProperty().set(imageViewCenterY - 50);
        SimpleDoubleProperty initialLayoutX = new SimpleDoubleProperty(canvas.layoutXProperty().get());
        SimpleDoubleProperty initialLayoutY = new SimpleDoubleProperty(canvas.layoutYProperty().get());
        System.out.println("Canvas placed at: " + canvas.layoutXProperty().get() + ", " + canvas.layoutYProperty().get());

        // Monitor mouse position and bind the canvas' size to it
        SimpleDoubleProperty mouseX = new SimpleDoubleProperty();
        SimpleDoubleProperty mouseY = new SimpleDoubleProperty();
        this.addEventFilter(MouseEvent.MOUSE_MOVED, ev -> {
            mouseX.set(this.screenToLocal(ev.getScreenX(), ev.getScreenY()).getX());
            mouseY.set(this.screenToLocal(ev.getScreenX(), ev.getScreenY()).getY());
        });

        mouseX.subscribe(em -> {
            if ((mouseX.get() - canvas.layoutXProperty().get()) >= 0) {
                canvas.widthProperty().set(mouseX.subtract(canvas.layoutXProperty()).get());
                canvas.connectPoints(layoutXProperty().get(), layoutYProperty().get(), imageViewEdgeX, imageViewEdgeX);

            } else {
                canvas.layoutXProperty().set(mouseX.get());
                canvas.widthProperty().set(initialLayoutX.subtract(mouseX).get());
            }
        });

        mouseY.subscribe(ec -> {
            if ((mouseY.get() - canvas.layoutYProperty().get()) >= 0) {
                canvas.heightProperty().set((mouseY.subtract(canvas.layoutYProperty())).get());
            } else {
                canvas.layoutYProperty().set(mouseY.get());
                canvas.heightProperty().set(initialLayoutY.subtract(mouseY).get());
            }
        });

        // Create a single observable property that responds to changes to either
        // width or height, ie mouse movement horizontally or vertically
        SimpleDoubleProperty canvasSizeProperty = new SimpleDoubleProperty();
        canvasSizeProperty.bind(canvas.widthProperty().multiply(canvas.heightProperty()));

        // Call the method to draw the line from the origin of the canvas to
        // the mouse everytime the mouse moves
        canvasSizeProperty.subscribe(el -> {
            canvas.connectPoints(canvas.layoutXProperty().get(), canvas.layoutYProperty().get(), canvas.widthProperty().get(), canvas.heightProperty().get());
        });

        this.getChildren().addFirst(canvas);
    }

}
