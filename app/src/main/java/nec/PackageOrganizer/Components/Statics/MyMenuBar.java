package nec.PackageOrganizer.Components.Statics;

import nec.PackageOrganizer.Controllers.MyController;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public final class MyMenuBar
        extends MenuBar
        implements MyComponent {

    private MyController controller;
    private SimpleStringProperty address = new SimpleStringProperty("");

    public MyMenuBar(MyController controller) {
        super();
        this.controller = controller;
        address.addListener(e -> setUpGUI());
    }

    private void setUpGUI() {
        Menu controlMenu = new Menu("File");
        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction(e -> this.controller.createNewFile());
        
        MenuItem saveFile = new MenuItem("Save");
        saveFile.setOnAction(e -> this.controller.saveFile());
        
        MenuItem loadFile = new MenuItem("Load");
        loadFile.setOnAction(e -> this.controller.loadFile());

        MenuItem closeWindow = new MenuItem("Exit");
        closeWindow.setOnAction(e -> this.controller.closeApp());
        controlMenu.getItems().addAll(newFile, saveFile, loadFile, closeWindow);

        Menu editMenu = new Menu("Edit");
        MenuItem addBox = new MenuItem("Add");
        addBox.setOnAction(e -> this.controller.getFactory().addNewBoxToCanvas());
        editMenu.getItems().add(addBox);
        
        MenuItem clearCanvas = new MenuItem("Clear");
        clearCanvas.setOnAction(e -> this.controller.clearCanvas());
        editMenu.getItems().add(clearCanvas);

        this.getMenus().addAll(controlMenu, editMenu);
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
