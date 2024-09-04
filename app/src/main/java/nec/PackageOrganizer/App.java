package nec.PackageOrganizer;

import nec.PackageOrganizer.Controllers.MyController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    MyController controller;
    
    @Override
    public void start(Stage stage) {
        controller = new MyController();
        controller.getIsShutdown().subscribe(e -> stage.close());
        controller.setWindow(stage);
        
        Scene mainScene = new Scene((Parent)controller.getComponent("Root"), 900, 900);
        
        stage.setScene(mainScene);
        stage.setTitle("Package Organizer - New File");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
