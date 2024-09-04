package nec.PackageOrganizer.Components.ObjectBox;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;

public class MyBoxTitleContainer extends TextField {
    String title;
    
    public MyBoxTitleContainer() {
        super();
        this.setPrefSize(120, 35);
        this.setMinSize(120, 35);
        this.setMaxSize(120, 35);
        this.setPromptText("Title");
        this.setAlignment(Pos.CENTER);
        this.setCursor(Cursor.DEFAULT);
    }
}
