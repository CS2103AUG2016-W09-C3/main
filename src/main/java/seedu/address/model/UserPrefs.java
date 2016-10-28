package seedu.address.model;

import seedu.address.commons.core.GuiSettings;

import java.util.ArrayList;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;
    // @@author A0140155U
    public ArrayList<CommandPreset> commandPresets = new ArrayList<>();
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
    public ObservableList<CommandPreset> getCommandPresets(){
        return FXCollections.observableArrayList(commandPresets);
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

}
