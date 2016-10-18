package seedu.address.ui;

import java.util.HashMap;

public class StyleMappings implements Styler{
    private final static String PROPERTY_BACKGROUND = "-fx-background-color";
    
    private final static PropertyMap priorityMap;
    static {
        priorityMap = new PropertyMap(PROPERTY_BACKGROUND, "#FFFFFF");
        priorityMap.addTo("VERYHIGH", "#FFAAAA");
        priorityMap.addTo("HIGH", "#FFD0D0");
        priorityMap.addTo("MEDIUM", "#FFFFFF");
        priorityMap.addTo("LOW", "#C5F1C5");
        priorityMap.addTo("VERYLOW", "#95E195");
    }
    
    public String getPriorityColour(String priority){
        return priorityMap.get(priority);
    }
    
    private static class PropertyMap{
        private final String property;
        private final String defaultProperty;
        public final static HashMap<String, String> map = new HashMap<String, String>();
        
        public PropertyMap(String property, String defaultProperty){
            this.property = property;
            this.defaultProperty = defaultProperty;
        }
        
        public void addTo(String key, String value){
            map.put(key, getStyleString(property, value));
        }
        
        public String get(String key){
            if(map.containsKey(key)){
                return map.get(key);
            }
            return defaultProperty;
        }
    }

    private static String getStyleString(String property, String value){
        return property + ": " + value + ";";
    }
}
