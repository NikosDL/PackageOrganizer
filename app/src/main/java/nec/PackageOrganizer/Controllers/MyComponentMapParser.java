package nec.PackageOrganizer.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nec.PackageOrganizer.Components.Statics.MyComponent;

public final class MyComponentMapParser {
    private final MyController mainController;
    private final Map<String, MyComponent> componentMap;
    
    protected MyComponentMapParser(MyController mainController) {
        this.mainController = mainController;
        this.componentMap = mainController.getComponentMap();
    }
    
    protected List<String> getNonParentDirectory(String address) {
        List<String> addressHits = new ArrayList<>();
        
        for (String name : componentMap.keySet()) {
            if (name.contains(address) && !address.endsWith(address)) {
                addressHits.add(name);
            }
        }
        
        return addressHits;
    }
}
