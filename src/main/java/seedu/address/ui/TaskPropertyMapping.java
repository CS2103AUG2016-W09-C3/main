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
    private final static PropertyMap doneMap;
    
    static {
        // Priority : Background color
        priorityMap = new PropertyMap(PROPERTY_BACKGROUND);
        priorityMap.addTo("VERYHIGH", "#FFAAAA");
        priorityMap.addTo("HIGH", "#FFD0D0");
        priorityMap.addTo("MEDIUM", "#FFFFFF");
        priorityMap.addTo("LOW", "#C5F1C5");
        priorityMap.addTo("VERYLOW", "#95E195");
        
        // Done : Background color
        doneMap = new PropertyMap(PROPERTY_BACKGROUND);
        priorityMap.addTo("Done", "#A9DEE3");
    }
    
    @Override
    public String getPriorityColour(String priority){
        return priorityMap.get(priority);
    }

    @Override
    public String getDoneColour(String done) {
        return doneMap.get(done);
    }
    
    /*
     * Wrapper class for a hash map which maps Task Property : CSS Style String.
     * Makes adding property constants easier.
     * Also, returns a default value if the requested property does not exist.
     */
    private static class PropertyMap{
        private final String property;
        public final static HashMap<String, String> map = new HashMap<String, String>();
        
        public PropertyMap(String property){
            this.property = property;
        }
        
        public void addTo(String key, String value){
            map.put(key, getStyleString(property, value));
        }
        
        public String get(String key){
            if(map.containsKey(key)){
                return map.get(key);
            }
            return ""; // No property
        }
    }
    
    /*
     * Converts a CSS property and value into the CSS style string format.
     */
    private static String getStyleString(String property, String value){
        return property + ": " + value + ";";
    }

}
