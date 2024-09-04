package nec.PackageOrganizer.Components.ObjectBox;

import nec.PackageOrganizer.Controllers.MyController;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nec.PackageOrganizer.Components.Statics.MyComponent;

public final class MyObjectBox
        extends VBox
        implements MyComponent {

    private MyController controller;
    private SimpleStringProperty address = new SimpleStringProperty("");

    private double lastClickXLocal;
    private double lastClickYLocal;
    private ScrollPane viewPort;
    private SimpleDoubleProperty currentHScroll;
    private SimpleDoubleProperty currentVScroll;
    private SimpleDoubleProperty viewPortWidth;
    private SimpleDoubleProperty viewPortHeight;

    private SimpleDoubleProperty titleHeightProperty;
    private SimpleDoubleProperty topInsideBorderHeightProperty;
    private SimpleDoubleProperty fieldContainerHeightProperty;
    private SimpleDoubleProperty bottomInsideBorderHeightProperty;
    private SimpleDoubleProperty methodContainerHeightProperty;
    private SimpleDoubleProperty childrenHeightProperty;

    private static int boxesCreated = 1;
//
//    private String styleUnfocused = ""
//            + "-fx-border-color: black;"
//            + "-fx-border-radius: 5;"
//            + "-fx-border-style: solid;"
//            + "-fx-border-width: 2px";
//
    private String styleFocused = ""
            + "-fx-border-color: black;"
            + "-fx-border-radius: 5;"
            + "-fx-border-style: solid;"
            + "-fx-border-width: 5px";

    public MyObjectBox(MyController controller) {
        this(10, controller);
    }

    public MyObjectBox(double spacing, MyController controller) {
        super(spacing);
        this.controller = controller;

        this.currentHScroll = new SimpleDoubleProperty(0);
        this.currentVScroll = new SimpleDoubleProperty(0);
        this.viewPortWidth = new SimpleDoubleProperty(0);
        this.viewPortHeight = new SimpleDoubleProperty(0);

        this.viewPort = (ScrollPane) controller.getComponent("Root/ScrollPane");
        this.currentHScroll.bind(this.viewPort.hvalueProperty());
        this.currentVScroll.bind(this.viewPort.vvalueProperty());
        this.viewPortWidth.bind(this.viewPort.widthProperty());
        this.viewPortHeight.bind(this.viewPort.heightProperty());

        this.titleHeightProperty = new SimpleDoubleProperty();
        this.topInsideBorderHeightProperty = new SimpleDoubleProperty();
        this.fieldContainerHeightProperty = new SimpleDoubleProperty();
        this.bottomInsideBorderHeightProperty = new SimpleDoubleProperty();
        this.methodContainerHeightProperty = new SimpleDoubleProperty();

        this.childrenHeightProperty = new SimpleDoubleProperty();

        address.addListener(e -> {
            setUpGUI();
            setUpEventHandlers();
        });
    }

    private void setUpGUI() {
        this.setShape(getRoundedShape());
        this.setStyle(this.styleFocused);
//        this.setPrefSize(200, 300);
        this.setMinSize(300, 300);
        this.setMaxWidth(300);
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(10, 0, 10, 0));
        this.setBackground(Background.fill(Color.ALICEBLUE));

        this.getChildren().addAll(getPlaceholderTitle(),
                getTopInsideBorder(),
                getPlaceholderFieldBox(),
                getBottomInsideBorder(),
                getPlaceholderMethodBox());

        this.childrenHeightProperty.bind(
                this.titleHeightProperty
                        .add(this.topInsideBorderHeightProperty)
                        .add(this.fieldContainerHeightProperty)
                        .add(this.bottomInsideBorderHeightProperty)
                        .add(this.methodContainerHeightProperty)
        );

        this.heightProperty().subscribe(num -> System.out.println("Box Height: " + this.heightProperty().getValue()));
        
        this.childrenHeightProperty.subscribe(num -> {
            System.out.println("Title Height: " + this.titleHeightProperty.getValue());
            System.out.println("Top Border Height: " + this.topInsideBorderHeightProperty.getValue());
            System.out.println("Field Cont Height: " + this.fieldContainerHeightProperty.getValue());
            System.out.println("Bottom Border Height: " + this.bottomInsideBorderHeightProperty.getValue());
            System.out.println("Method Cont Height: " + this.methodContainerHeightProperty.getValue());
            System.out.println("Sum Height: " + this.childrenHeightProperty.getValue());

            if (this.childrenHeightProperty.getValue() > (this.heightProperty().getValue() - 50)) {
                this.setHeight(this.childrenHeightProperty.getValue() + 60);
            }
        });

        VBox.setVgrow(this.getChildren().get(2), Priority.ALWAYS);
//        VBox.setVgrow(this.getChildren().get(4), Priority.NEVER);
    }

    private MyBoxTitleContainer getPlaceholderTitle() {
        MyBoxTitleContainer titleContainer = new MyBoxTitleContainer();
        titleContainer.setOnMouseEntered(e -> {
            if (titleContainer.isFocused()) {
                titleContainer.setCursor(Cursor.TEXT);
            }
        });
        titleContainer.setOnMouseExited(e
                -> titleContainer.setCursor(Cursor.DEFAULT));

        this.titleHeightProperty.bind(titleContainer.heightProperty());

        return titleContainer;
    }

    private MyBoxFieldContainer getPlaceholderFieldBox() {
        MyBoxFieldContainer fieldContainer = new MyBoxFieldContainer();

        this.fieldContainerHeightProperty.bind(fieldContainer.heightProperty());

        return fieldContainer;
    }

    private MyBoxMethodContainer getPlaceholderMethodBox() {
        MyBoxMethodContainer methodContainer = new MyBoxMethodContainer();

        this.methodContainerHeightProperty.bind(methodContainer.heightProperty());

        return methodContainer;
    }

    private MyBoxInsideBorder getTopInsideBorder() {
        MyBoxInsideBorder border = new MyBoxInsideBorder();

        this.topInsideBorderHeightProperty.bind(border.heightProperty());

        return border;
    }

    private MyBoxInsideBorder getBottomInsideBorder() {
        MyBoxInsideBorder border = new MyBoxInsideBorder();

        this.bottomInsideBorderHeightProperty.bind(border.heightProperty());

        return border;
    }

    private void setUpEventHandlers() {
//        this.focusedProperty().subscribe(val -> {
//            if (val) {
//                this.setStyle(this.styleFocused);
//            } else {
//                this.setStyle(this.styleUnfocused);
//            }
//        });
//        this.setOnMouseClicked(e -> this.requestFocus());

        this.setOnMousePressed(e -> {
            this.lastClickXLocal = e.getX();
            this.lastClickYLocal = e.getY();
        });
        this.setOnMouseDragged(e -> this.dragBox(e));

        this.setOnMouseDragReleased(e -> {
            this.layoutXProperty().unbind();
            this.layoutYProperty().unbind();
        });
    }

    private Pane getParentAsPane() {
        return (Pane) this.getParent();
    }

    private Rectangle getRoundedShape() {
        Rectangle rect = new Rectangle(200, 100);
        rect.setArcHeight(5);
        rect.setArcWidth(5);

        return rect;
    }

    // TODO: Use FluetAPI to replace all doubles with DoubleProperty's
    private void dragBox(MouseEvent e) {
        double sceneXLoc = e.getSceneX();
        double sceneYLoc = e.getSceneY();
        double sidePadding = this.viewPort.getPadding().getLeft();
        double topPadding = this.viewPort.getPadding().getTop();
        double menuBarHeight = ((MenuBar) controller.getComponent("Root/MenuBar")).getHeight();
        double viewPortWidthVal = this.viewPortWidth.getValue();
        double viewPortHeightVal = this.viewPortHeight.getValue();
        double hScrollVal = this.currentHScroll.getValue();
        double vScrollVal = this.currentVScroll.getValue();

        SimpleDoubleProperty fieldXProperty
                = new SimpleDoubleProperty(sceneXLoc - sidePadding + hScrollVal
                        * (getParentAsPane().getPrefWidth() - viewPortWidthVal + sidePadding)
                        - lastClickXLocal);
        SimpleDoubleProperty fieldYProperty
                = new SimpleDoubleProperty(sceneYLoc - topPadding - menuBarHeight
                        + vScrollVal * (getParentAsPane().getPrefHeight() - viewPortHeightVal + topPadding)
                        - lastClickYLocal);

        this.layoutXProperty().bind(fieldXProperty);
        this.layoutYProperty().bind(fieldYProperty);
    }

    public static int getBoxesCreated() {
        return boxesCreated;
    }

    public static void incrementCreatedCounter() {
        boxesCreated++;
    }

    @Override
    public String getAddress() {
        return this.address.getValue();
    }

    @Override
    public void setAddress(String address) {
        this.address.setValue(address);
    }
}
