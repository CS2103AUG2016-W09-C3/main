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
    
    /*
     * Commands modifying the favorite panel are banned because it is possible to create an infinite loop:
     * 
     * favorite Loop 1 c/favorite 2
     * favorite Loop 2 c/favorite 1
     * 
     */
    public static final String[] BANNED_PRESET_COMMANDS = {"favorite", "unfavorite"};
    
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
     * The problem is PresetListPanel can only bind to ObservableList,
     * and ObservableList cannot be serialized to JSON.
     * 
     * So, separate lists have to be used.
     */
    
    // @@author
    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public UserPrefs(){
        loadDefaultPresets();
        this.setGuiSettings(500, 500, 0, 0);
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    // @@author A0140155U
    public ObservableList<CommandPreset> initCommandPresets(){
        // Initialize internalList after JSON has been loaded
        removeBannedPresets();
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
    public void addPreset(CommandPreset commandPreset) throws IllegalValueException {
        assert commandPreset != null;
        if(isBannedCommand(commandPreset.getCommand())){
            throw new IllegalValueException("Cannot add banned command");
        }
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

    public void clearPresets(){
        commandPresets.clear();
    }
    
    private void loadDefaultPresets() {
        commandPresets.clear();
        commandPresets.add(new CommandPreset("list", "List all undone"));
        commandPresets.add(new CommandPreset("list df/all", "List all done and undone"));
        commandPresets.add(new CommandPreset("list ds/today 12:00 am de/tomorrow 12:00 am", "List all today"));
        commandPresets.add(new CommandPreset("list ds/today 12:00 am de/3 days from now 12:00 am", "List all in next 3 days"));
        commandPresets.add(new CommandPreset("list ds/last monday 12:00 am de/next monday 12:00 am", "List all in this week"));
        commandPresets.add(new CommandPreset("list s/date", "List sorted by date"));
        commandPresets.add(new CommandPreset("list s/name", "List sorted by name"));
        commandPresets.add(new CommandPreset("list s/priority", "List sorted by priority"));
    }
   
    private void removeBannedPresets() {
        commandPresets.removeIf(commandPreset -> isBannedCommand(commandPreset.getCommand()));
    }
    
    private boolean isBannedCommand(String command){
        for(String bannedCommand : BANNED_PRESET_COMMANDS){
            if(command.trim().toLowerCase().startsWith(bannedCommand)){
                return true;
            }
        }
        return false;
    }
    // @@author

}
