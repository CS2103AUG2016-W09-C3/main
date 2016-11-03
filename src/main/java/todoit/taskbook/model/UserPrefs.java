package todoit.taskbook.model;

import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import todoit.taskbook.commons.core.GuiSettings;
import todoit.taskbook.commons.exceptions.IllegalValueException;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;
    // @@author A0140155U
    
    // This list stores the list of command presets, which are serialized to preferences.json
    private ArrayList<CommandPreset> commandPresets = new ArrayList<>();
    
    // This list stores an observable list of command presets, which the cards in the PresetListPanel are binded to.
    @JsonIgnore 
    private ObservableList<CommandPreset> internalList = FXCollections.observableArrayList();
    
    /*
     * Both lists store the exact same data.
     * 
     * One is used to display on the panel and the other is used to save to a JSON file.
     * 
     * Ideally I'd use just one list, but PresetListPanel can only bind to ObservableList, and
     * ObservableList cannot be serialized to JSON.
     * 
     * So, I used separate lists. A bit hacky, but I didn't really have a choice...
     */
    
    // @@author
    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public UserPrefs(){
        this.setGuiSettings(500, 500, 0, 0);
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    // @@author A0140155U
    public ObservableList<CommandPreset> initCommandPresets(){
        // Initialize internalList after JSON has been loaded
        internalList = FXCollections.observableArrayList(commandPresets);
        return internalList;
    }
    // @@author
    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof UserPrefs)){ //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs)other;

        return Objects.equals(guiSettings, o.guiSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings);
    }

    @Override
    public String toString(){
        return guiSettings.toString();
    }

    // @@author A0140155U
    public void addPreset(CommandPreset commandPreset) {
        assert commandPreset != null;
        commandPresets.add(commandPreset);
        internalList.add(commandPreset);
    }

    public String removePreset(int index) throws IllegalValueException{
        if(index < 0 || index >= commandPresets.size()){
            throw new IllegalValueException("Index out of range.");
        }
        String removedCommandDesc = commandPresets.get(index).getDescription();
        commandPresets.remove(index);
        internalList.remove(index);
        return removedCommandDesc;
    }
    
    public int getNumPresets(){
        return commandPresets.size();
    }
    // @@author

}
