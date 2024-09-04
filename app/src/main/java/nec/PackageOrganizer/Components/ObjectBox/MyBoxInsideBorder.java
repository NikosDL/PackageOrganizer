package nec.PackageOrganizer.Components.ObjectBox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MyBoxInsideBorder extends StackPane {
    public MyBoxInsideBorder() {
        super();
        this.setPrefSize(100, 20);
        this.setMinSize(100, 20);
        this.setMaxSize(100, 20);
        
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(5, 10, 5, 10));
        this.getChildren().add(getBorderRectangle());
    }
    
    private Rectangle getBorderRectangle() {
        Rectangle toRet = new Rectangle(100, 3);
        toRet.setFill(Color.BLACK);
        
        return toRet;
    }
}
