package nec.PackageOrganizer.Components.Statics;

import nec.PackageOrganizer.Controllers.MyController;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import nec.PackageOrganizer.Components.ObjectBox.MyImageView;

public final class MyRoot 
        extends BorderPane 
        implements MyComponent {

    private final MyController controller;
    private final SimpleStringProperty address = new SimpleStringProperty("");

    public MyRoot(MyController controller) {
        super();
        this.controller = controller;
        this.setPrefSize(900, 900);
        this.setBackground(Background.fill(Color.WHEAT));

        this.address.addListener(e -> {
            this.setCenter(controller.getFactory().newScrollPane(this));
            this.setTop(controller.getFactory().newMenuBar(this));
        });
    }

    @Override
    public String getAddress() {
        return this.address.getValue();
    }

    @Override
    public void setAddress(String address) {
        this.address.setValue(address);;
    }
}
