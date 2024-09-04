package nec.PackageOrganizer.Components.Statics;

import nec.PackageOrganizer.Controllers.MyController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import nec.PackageOrganizer.Components.ObjectBox.MyImageView;

public final class MyScrollPane
        extends ScrollPane
        implements MyComponent {

    private MyController controller;
    private SimpleStringProperty address = new SimpleStringProperty("");

    public MyScrollPane(MyController controller) {
        super();

        this.controller = controller;

        this.address.addListener(e -> {
            setUpGUI();
            setUpHandlers();
        });
    }

    private void setUpGUI() {
        this.setPrefSize(900, 900);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setFitToHeight(false);
        this.setFitToWidth(false);
        this.setBackground(Background.fill(Color.BURLYWOOD));

        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setContent(controller.getFactory().newCenterPane(this));
    }

    private void setUpHandlers() {
        this.setOnKeyPressed(e -> {
            if (this.isFocused()) {
                KeyCode code = e.getCode();
                switch (code) {
                    case KeyCode.DOWN -> {
                        this.setVvalue(this.getVvalue() + 5);
                        break;
                    }
                    case KeyCode.UP -> {
                        this.setVvalue(this.getVvalue() - 5);
                        break;
                    }
                    case KeyCode.RIGHT -> {
                        this.setHvalue(this.getHvalue() + 5);
                        break;
                    }
                    case KeyCode.LEFT -> {
                        this.setHvalue(this.getHvalue() - 5);
                        break;
                    }
                    default -> {
                        break;
                    }
                }
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

}
