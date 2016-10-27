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
    
    private final static PropertyMap lightPriorityMap;
    private final static PropertyMap lightDoneMap;
    private final static PropertyMap cardDoneMap;
    
    private static TaskPropertyMapping instance = null;
    
    static {
        // Light - Priority : Background color
        lightPriorityMap = new PropertyMap(PROPERTY_BACKGROUND);
        lightPriorityMap.addTo("VERYHIGH", "radial-gradient(center 50% 50%, radius 50%, #C20E00, #A70E00)");
        lightPriorityMap.addTo("HIGH", "radial-gradient(center 50% 50%, radius 50%, #D19900, #C08800)");
        lightPriorityMap.addTo("MEDIUM", "radial-gradient(center 50% 50%, radius 50%, #555555, #444444)");
        lightPriorityMap.addTo("LOW", "radial-gradient(center 50% 50%, radius 50%, #007614, #005B14)");
        lightPriorityMap.addTo("VERYLOW", "radial-gradient(center 50% 50%, radius 50%, #00A625, #009519)");
        
        // Light - Done : Background color
        lightDoneMap = new PropertyMap(PROPERTY_BACKGROUND);
        lightDoneMap.addTo("Done", "radial-gradient(radius 100%, #3408B1, #2C08A1)");
        
        // Card - Done : Background color
        cardDoneMap = new PropertyMap(PROPERTY_BACKGROUND);
        cardDoneMap.addTo("Done", "#606060");
        cardDoneMap.addTo("Not done", "radial-gradient(radius 100%, #202020, #101010)");
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
    public String getLightPriorityColour(String priority){
        return lightPriorityMap.get(priority);
    }

    @Override
    public String getLightDoneColour(String done) {
        return lightDoneMap.get(done);
    }
    
    @Override
    public String getCardDoneColour(String done) {
        return cardDoneMap.get(done);
    }
    
    /*
     * Wrapper class for a hash map which maps Task Property : CSS Style String.
     * Makes adding property constants easier.
     * Also, returns a default value if the requested property does not exist.
     */
    private static class PropertyMap{
        private final String property;
        public final HashMap<String, String> map = new HashMap<String, String>();
        
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