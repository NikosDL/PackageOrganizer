package nec.PackageOrganizer.Components.ObjectBox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MyBoxMethodContainer extends VBox {

    public MyBoxMethodContainer() {
        super(2);
        this.setPrefSize(300, 100);
        this.setPadding(new Insets(5));
        this.setAlignment(Pos.CENTER);

        this.getChildren().addAll(new MyObjectBoxField(), new MyAddFieldButton());
        getButton().setOnAction(e -> addNewField());
    }
    
    public Button getButton() {
        return (Button) this.getChildren().getLast();
    }
    
    private void addNewField() {
        this.getChildren().add(this.getChildren().size() - 1, new MyObjectBoxField());
    }
}
