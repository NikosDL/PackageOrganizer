package nec.PackageOrganizer.Controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import nec.PackageOrganizer.Components.Statics.MyComponent;
import nec.PackageOrganizer.Components.ObjectBox.MyObjectBox;

public final class MySaveLoadController {

    private MyController mainController;
    private final FileChooser myFileChooser;
    private File lastSavedIn;

    protected MySaveLoadController(MyController mainControl) {
        mainController = mainControl;
        myFileChooser = new FileChooser();
        lastSavedIn = new File(System.getProperty("user.home"));
        configureFileChooser(myFileChooser);
    }

    protected void saveBoxData(Pane canvas) {
        try {
            myFileChooser.setTitle("Save File");
            File dest = myFileChooser
                    .showSaveDialog(mainController.getWindow());
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(dest));

            for (Node box : canvas.getChildren()) {
                MyObjectBox myBox = (MyObjectBox) box;
                String toWrite = ((MyComponent) myBox).getAddress() + ",";
                toWrite = toWrite.concat(String.valueOf(myBox.getLayoutX()) + ",");
                toWrite = toWrite.concat(String.valueOf(myBox.getLayoutY()) + "\n");
                writer.write(toWrite);
            }
            writer.close();
            this.lastSavedIn = dest;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected List<List<String>> loadBoxData() {
        try {
            List<List<String>> toReturn = new ArrayList<>();
            myFileChooser.setTitle("Load File");
            File dest = myFileChooser.showOpenDialog(mainController.getWindow());
            if (dest != null) {
                BufferedReader reader = new BufferedReader(new FileReader(dest));
                String line;
                while ((line = reader.readLine()) != null) {
                    List<String> boxData = Arrays.asList(line.split(","));
                    toReturn.add(boxData);
                }
                return toReturn;
            } else {
                throw new Exception();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setInitialDirectory(this.lastSavedIn);
    }
}
