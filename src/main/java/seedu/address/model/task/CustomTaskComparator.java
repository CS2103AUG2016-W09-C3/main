//@@author A0139121R
package seedu.address.model.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class CustomTaskComparator implements Comparator<Task>{
    
    private ArrayList<String> attributes;
    private HashMap<String, Comparer> comparerMap;
    
    public CustomTaskComparator(ArrayList<String> sortByAttributes){
        this.comparerMap = new HashMap<String, Comparer>();
        this.comparerMap.put("name", new AlphabetComparer());
        this.comparerMap.put("priority", new PriorityComparer());
        this.comparerMap.put("date", new DateComparer());
        this.attributes = sortByAttributes;
    }


    public int compare(Task t1, Task t2) {
        assert(t1 != null && t2 != null);
        for(String attribute : attributes){
            Comparer attributeComparer = this.comparerMap.get(attribute);
            int compareResult = attributeComparer.compare(t1, t2);
            if(compareResult != 0){
                return compareResult;
            }
        }
        
        return 0;
    }
    
    interface Comparer {
        public int compare(Task t1, Task t2);
    }
    
    private class AlphabetComparer implements Comparer{
        public int compare(Task t1, Task t2){
            return t1.getName().toString().compareToIgnoreCase(t2.getName().toString());
        }
    }
    
    private class PriorityComparer implements Comparer{
        public int compare(Task t1, Task t2){
            //highest priority is of value 5, lowest is of value 1.
            return ((Integer) t2.getPriority().getEnumPriority()).compareTo((Integer) t1.getPriority().getEnumPriority());
        }
    }
    
    private class DateComparer implements Comparer{
        public int compare(Task t1, Task t2){
            if(t1.isDated() && t2.isDated()){
                DatedTask datedT1 = (DatedTask) t1;
                DatedTask datedT2 = (DatedTask) t2;
                LocalDateTime time1 = datedT1.getDateTime().getDateTime();
                LocalDateTime time2 = datedT2.getDateTime().getDateTime();
                
                return time1.compareTo(time2);
            } else if(t1.isDated() && !t2.isDated()){
                return -1;
            } else if(!t1.isDated() && t2.isDated()){
                return 1;
            } else {
                return 0;//both non dated tasks are equal
            }
        }
    }

    
}
