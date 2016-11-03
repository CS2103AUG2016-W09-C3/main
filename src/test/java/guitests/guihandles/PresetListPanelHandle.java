// @@author A0140155U
package guitests.guihandles;


import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import todoit.taskbook.TestApp;
import todoit.taskbook.model.CommandPreset;
import todoit.taskbook.model.task.ReadOnlyTask;
import todoit.taskbook.testutil.TestUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the preset list.
 */
public class PresetListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#presetCard";

    private static final String PRESET_LIST_VIEW_ID = "#presetListView";

    public PresetListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<CommandPreset> getSelectedPresets() {
        ListView<CommandPreset> presetList = getListView();
        return presetList.getSelectionModel().getSelectedItems();
    }

    public ListView<CommandPreset> getListView() {
        return (ListView<CommandPreset>) getNode(PRESET_LIST_VIEW_ID);
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point= TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }
    
    /**
     * Returns true if the {@code presets} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, CommandPreset... presets) {
        List<CommandPreset> presetsInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + presets.length > presetsInList.size()){
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < presets.length; i++) {
            if (!presetsInList.get(startPosition + i).getCommand().equals(presets[i].getCommand()) ||
                    !presetsInList.get(startPosition + i).getDescription().equals(presets[i].getDescription())){
                return false;
            }
        }

        return true;
    }
    
    public PresetCardHandle navigateToPreset(String description) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<CommandPreset> preset = getListView().getItems().stream().filter(p -> p.getDescription().equals(description)).findAny();
        if (!preset.isPresent()) {
            throw new IllegalStateException("Name not found: " + description);
        }

        return navigateToPreset(preset.get());
    }

    /**
     * Returns true if the list is showing the oreset details correctly and in correct order.
     * @param presets A list of person in the correct order.
     */
    public boolean isListMatching(CommandPreset... presets) {
        return this.isListMatching(0, presets);
    }
    
    /**
     * Returns true if the list is showing the presets details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param presets A list of presets in the correct order.
     */
    public boolean isListMatching(int startPosition, CommandPreset... presets) throws IllegalArgumentException {
        if (presets.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " presets");
        }
        assertTrue(this.containsInOrder(startPosition, presets));
        for (int i = 0; i < presets.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndPreset(getPresetCardHandle(startPosition + i), presets[i])) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Navigates the listview to display and select the preset.
     */
    public PresetCardHandle navigateToPreset(CommandPreset preset) {
        int index = getPresetIndex(preset);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getPresetCardHandle(preset);
    }
    
    /**
     * Returns the position of the preset given, {@code NOT_FOUND} if not found in the list.
     */
    public int getPresetIndex(CommandPreset targetPreset) {
        List<CommandPreset> prsetsInList = getListView().getItems();
        for (int i = 0; i < prsetsInList.size(); i++) {
            if(prsetsInList.get(i).getDescription().equals(targetPreset.getDescription())){
                return i;
            }
        }
        return NOT_FOUND;
    }



    public PresetCardHandle getPresetCardHandle(int index) {
        return getPresetCardHandle(new CommandPreset(getListView().getItems().get(index)));
    }

    public PresetCardHandle getPresetCardHandle(CommandPreset preset) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> presetCardNode = nodes.stream()
                .filter(n -> new PresetCardHandle(guiRobot, primaryStage, n).isSamePreset(preset))
                .findFirst();
        if (presetCardNode.isPresent()) {
            return new PresetCardHandle(guiRobot, primaryStage, presetCardNode.get());
        } else {
            return null;
        }
    }

    
    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfPresets() {
        return getListView().getItems().size();
    }
}
