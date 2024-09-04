package nec.PackageOrganizer.Controllers;

import java.util.List;
import nec.PackageOrganizer.Components.ObjectBox.MyObjectBox;

public final class MyParser {
    private MyObjectBox[] boxArray;
    private MyController mainController;
    
    protected MyParser(MyController mainController, List<List<String>> boxData) {
        this.mainController = mainController;
        int boxNum = boxData.size();
        
        boxArray = new MyObjectBox[boxNum];
        for (int i = 0; i < boxNum; i++) {
            List<String> singleBoxInfo = boxData.get(i);
            MyObjectBox box = new MyObjectBox(mainController);
            box.setAddress(singleBoxInfo.get(0));
            box.setLayoutX(Double.parseDouble(singleBoxInfo.get(1)));
            box.setLayoutY(Double.parseDouble(singleBoxInfo.get(2)));
            boxArray[i] = box;
        }
    }
    
    protected MyObjectBox[] getBoxes() {
        return this.boxArray;
    }
}
