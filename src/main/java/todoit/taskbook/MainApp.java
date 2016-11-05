package todoit.taskbook;

import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import todoit.taskbook.commons.core.Config;
import todoit.taskbook.commons.core.EventsCenter;
import todoit.taskbook.commons.core.LogsCenter;
import todoit.taskbook.commons.core.Version;
import todoit.taskbook.commons.events.model.FilePathChangedEvent;
import todoit.taskbook.commons.events.ui.ExitAppRequestEvent;
import todoit.taskbook.commons.exceptions.DataConversionException;
import todoit.taskbook.commons.util.ConfigUtil;
import todoit.taskbook.commons.util.StringUtil;
import todoit.taskbook.logic.Logic;
import todoit.taskbook.logic.LogicManager;
import todoit.taskbook.model.*;
import todoit.taskbook.storage.Storage;
import todoit.taskbook.storage.StorageManager;
import todoit.taskbook.ui.Ui;
import todoit.taskbook.ui.UiManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(1, 0, 0, true);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    public MainApp() {}

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Welcome to ToDoIt! ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getTaskBookFilePath(), config.getUserPrefsFilePath());

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName){
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyTaskBook> taskBookOptional;
        ReadOnlyTaskBook initialData;
        try {
            taskBookOptional = storage.readTaskBook();
            if(!taskBookOptional.isPresent()){
                logger.info("Data file not found. Will be starting with an empty task list");
            }
            initialData = taskBookOptional.orElse(new TaskBook());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty task list");
            initialData = new TaskBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty task list");
            initialData = new TaskBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if(configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. " +
                    "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        initializedConfig.setConfigFilePath(configFilePathUsed);
        return initializedConfig;
    }

    protected UserPrefs initPrefs(Config config) {
        assert config != null;

        String prefsFilePath = config.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. " +
                    "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty task list");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting TaskBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("========================= [ We hope you enjoyed using ToDoIt! ] ==========================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }

    /**
     * Handles the change file path event.
     * Saves the new file path to both storage and config.
     * If either fail, revert to the old file path.
     */

    // @@author A0140155U
    @Subscribe
    public void handleFilePathChangedEvent(FilePathChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Config data changed, saving to file"));
        String oldFilePath = config.getTaskBookFilePath();
        try {
            storage.setTaskBookFilePath(event.filePath);
            storage.saveTaskBook(model.getTaskBook());
            config.setTaskBookFilePath(event.filePath);
            ConfigUtil.saveConfig(config, config.getConfigFilePath());
        } catch (IOException e) {
            storage.setTaskBookFilePath(oldFilePath);
            config.setTaskBookFilePath(oldFilePath);
            logger.warning("Failed to save config file, reverting to old : " + StringUtil.getDetails(e));
        }
    }
    // @@author  
    
    public static void main(String[] args) {
        launch(args);
    }
}
