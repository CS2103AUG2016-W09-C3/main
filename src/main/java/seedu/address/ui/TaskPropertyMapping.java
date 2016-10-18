package seedu.address.ui;

import java.util.HashMap;

/*
 * Class for converting task properties to CSS style strings which
 * are used to style UI elements.
 * 
 * Task properties must be a string.
 * 
 * Styles are usually strings in the form:
 * style-name : value;
 * which can be easily applied by using setStyle();
 */

public class TaskPropertyMapping implements TaskStyleMapping{
    private final static String PROPERTY_BACKGROUND = "-fx-background-color";
    
    private final static PropertyMap priorityMap;
    
    static {
        // Priority : Background color
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
    
    /*
     * Wrapper class for a hash map which maps Task Property : CSS Style String.
     * Makes adding property constants easier.
     * Also, returns a default value if the requested property does not exist.
     */
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
    
    /*
     * Converts a CSS property and value into the CSS style string format.
     */
    private static String getStyleString(String property, String value){
        return property + ": " + value + ";";
    }
}
