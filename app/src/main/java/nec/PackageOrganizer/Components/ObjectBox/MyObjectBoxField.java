package nec.PackageOrganizer.Components.ObjectBox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MyObjectBoxField extends HBox {

    public MyObjectBoxField() {
        super();
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(5, 0, 5, 0));
        this.setSpacing(2);
        this.setPrefSize(220, 25);
        this.getChildren().addAll(getLeftDot(), getLeftButton(), getTextField(), getRightButton(), getRightDot());
    }

    private Button getLeftButton() {
        Button toRet = new Button("<");
        toRet.setPrefSize(5, 5);

        return toRet;
    }

    private TextField getTextField() {
        TextField field = new TextField();
        field.setPromptText("Field/Method");
        field.setAlignment(Pos.CENTER);
        field.setPrefSize(220, 20);

        return field;
    }

    private Button getRightButton() {
        Button toRet = new Button(">");
        toRet.setPrefSize(5, 5);

        return toRet;
    }

    private StackPane getRightDot() {
        try {
            Image image = new Image("/images/right-inactive.png");
            StackPane viewPane = new MyImageView(image);
            ImageView view = (ImageView) viewPane.getChildren().getFirst();
            
            
            return viewPane;
        } catch (Exception e) {
            System.out.println("Image not found");
            return null;
        }

    }

    private StackPane getLeftDot() {
        try {
            Image image = new Image("/images/left-inactive.png");
            StackPane viewPane = new MyImageView(image);
            ImageView view = (ImageView) viewPane.getChildren().getFirst();
            
            return viewPane;
        } catch (Exception e) {
            System.out.println("Image not found");
            return null;
        }
    }
}
