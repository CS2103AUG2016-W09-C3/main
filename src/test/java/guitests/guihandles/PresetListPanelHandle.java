package guitests.guihandles;


import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.model.CommandPreset;
import seedu.address.testutil.TestUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the preset list.
 */
public class PresetListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

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
    
    public PresetCardHandle navigateToPreset(String description) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<CommandPreset> preset = getListView().getItems().stream().filter(p -> p.getDescription().equals(description)).findAny();
        if (!preset.isPresent()) {
            throw new IllegalStateException("Name not found: " + description);
        }

        return navigateToPreset(preset.get());
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

    public PresetCardHandle getPresetCardHandle(CommandPreset person) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> personCardNode = nodes.stream()
                .filter(n -> new PresetCardHandle(guiRobot, primaryStage, n).isSamePreset(person))
                .findFirst();
        if (personCardNode.isPresent()) {
            return new PresetCardHandle(guiRobot, primaryStage, personCardNode.get());
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
