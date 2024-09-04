package nec.PackageOrganizer.Controllers;

import nec.PackageOrganizer.Components.MyCenterPane;
import nec.PackageOrganizer.Components.Statics.MyComponent;
import nec.PackageOrganizer.Components.Statics.MyMenuBar;
import nec.PackageOrganizer.Components.ObjectBox.MyObjectBox;
import nec.PackageOrganizer.Components.Statics.MyScrollPane;

public final class MyComponentFactory {

    private final MyController controller;

    protected MyComponentFactory(MyController controller) {
        this.controller = controller;
    }

    public MyCenterPane newCenterPane(MyComponent node) {
        MyCenterPane newPane = new MyCenterPane(this.controller);
        newPane.setAddress(node.getAddress() + "/Canvas");
        controller.registerComponent(newPane.getAddress(), newPane);

        return newPane;
    }

    public MyScrollPane newScrollPane(MyComponent node) {
        MyScrollPane newScroll = new MyScrollPane(this.controller);
        newScroll.setAddress(node.getAddress() + "/ScrollPane");
        controller.registerComponent(newScroll.getAddress(), newScroll);

        return newScroll;
    }

    public MyMenuBar newMenuBar(MyComponent node) {
        MyMenuBar newMenuBar = new MyMenuBar(this.controller);
        newMenuBar.setAddress(node.getAddress() + "/MenuBar");
        controller.registerComponent(newMenuBar.getAddress(), newMenuBar);

        return newMenuBar;
    }

    public MyObjectBox newObjectBox(MyComponent node) {
        MyObjectBox newObjectBox = new MyObjectBox(this.controller);
        newObjectBox.setAddress(node.getAddress() + "/ObjectBox:" + MyObjectBox.getBoxesCreated());
        controller.registerComponent(newObjectBox.getAddress(), newObjectBox);

        MyObjectBox.incrementCreatedCounter();
        return newObjectBox;
    }

    public void addNewBoxToCanvas() {
        MyComponent asMyComponent = this.controller.getComponent("Root/ScrollPane/Canvas");
        MyObjectBox boxToAdd = this.newObjectBox(asMyComponent);
        ((MyCenterPane) asMyComponent).getChildren().add(boxToAdd);
    }
}
