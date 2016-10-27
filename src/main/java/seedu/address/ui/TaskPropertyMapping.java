// @@author A0140155U
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
    
    private static TaskPropertyMapping instance = null;
    
    static {
        // Priority : Background color
        priorityMap = new PropertyMap(PROPERTY_BACKGROUND);
        priorityMap.addTo("VERYHIGH", "radial-gradient(radius 100%, #BC0E00, #AC0E00)");
        priorityMap.addTo("HIGH", "radial-gradient(radius 100%, #940B00, #840B00)");
        priorityMap.addTo("MEDIUM", "radial-gradient(radius 100%, #555555, #444444)");
        priorityMap.addTo("LOW", "radial-gradient(radius 100%, #007114, #006114)");
        priorityMap.addTo("VERYLOW", "radial-gradient(radius 100%, #009019, #008019)");
        
        // Done : Background color
        doneMap = new PropertyMap(PROPERTY_BACKGROUND);
        doneMap.addTo("Done", "radial-gradient(radius 100%, #230575, #180564)");
    }
    
    // Singleton
    private TaskPropertyMapping(){}
    
    public static TaskPropertyMapping getInstance(){
        if(instance == null){
            instance = new TaskPropertyMapping();
        }
        return instance;
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
//@@author