package nec.PackageOrganizer.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nec.PackageOrganizer.Components.MyCenterPane;
import nec.PackageOrganizer.Components.Statics.MyComponent;
import nec.PackageOrganizer.Components.ObjectBox.MyObjectBox;
import nec.PackageOrganizer.Components.Statics.MyRoot;

public final class MyController {

    private Stage currentWindow;
    private MyRoot view;
    private MyComponentFactory componentFactory;
    private SimpleBooleanProperty isShutdown;
    private Map<String, MyComponent> componentMap;

    public MyController() {
        componentMap = new HashMap<>();
        componentFactory = new MyComponentFactory(this);

        this.view = new MyRoot(this);
        this.view.setAddress("Root");
        registerComponent(this.view.getAddress(), this.view);
        isShutdown = new SimpleBooleanProperty(false);
    }

    public MyComponentFactory getFactory() {
        return this.componentFactory;
    }

    public void closeApp() {
        isShutdown.set(true);
    }

    public void createNewFile() {
        ScrollPane pane = (ScrollPane) componentMap.get("Root/ScrollPane");
        componentMap.remove("Root/ScrollPane/Canvas");
        pane.setContent(this.componentFactory.newCenterPane((MyComponent) pane));
    }

    public void clearCanvas() {
        MyComponentMapParser compParser = new MyComponentMapParser(this);
        List<String> addressesToRemove = compParser.getNonParentDirectory("Root/ScrollPane/Canvas");
        for (String address : addressesToRemove) {
            this.unregisterComponent(address);
        }

        Pane canvas = (Pane) componentMap.get("Root/ScrollPane/Canvas");
        canvas.getChildren().clear();
    }

    public void registerComponent(String position, MyComponent node) {
        componentMap.put(position, node);
    }

    public void unregisterComponent(String position) {
        componentMap.remove(position);
    }

    public MyComponent getComponent(String position) {
        return componentMap.get(position);
    }

    public Map<String, MyComponent> getComponentMap() {
        return this.componentMap;
    }

    public void setWindow(Stage stage) {
        this.currentWindow = stage;
    }

    public Stage getWindow() {
        return this.currentWindow;
    }
    
    public SimpleBooleanProperty getIsShutdown() {
        return this.isShutdown;
    }

    public void saveFile() {
        MySaveLoadController saveLoadControl = new MySaveLoadController(this);
        saveLoadControl.saveBoxData(
                (Pane) componentMap.get("Root/ScrollPane/Canvas"));
    }

    public void loadFile() {
        MySaveLoadController saveLoadControl = new MySaveLoadController(this);
        List<List<String>> boxData = saveLoadControl.loadBoxData();
        if (boxData != null) {
            this.clearCanvas();

            MyParser parser = new MyParser(this, boxData);
            MyObjectBox[] boxesToLoad = parser.getBoxes();

            MyComponent asMyComponent = this.componentMap.get("Root/ScrollPane/Canvas");
            for (MyObjectBox boxToAdd : boxesToLoad) {
                ((MyCenterPane) asMyComponent).getChildren().add(boxToAdd);
                this.registerComponent(boxToAdd.getAddress(), boxToAdd);
            }
        }
    }
}
