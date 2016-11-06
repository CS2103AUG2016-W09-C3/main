package todoit.taskbook.logic;

import javafx.collections.ObservableList;
import todoit.taskbook.commons.core.ComponentManager;
import todoit.taskbook.commons.core.LogsCenter;
import todoit.taskbook.logic.commands.Command;
import todoit.taskbook.logic.commands.CommandResult;
import todoit.taskbook.logic.parser.Parser;
import todoit.taskbook.model.Model;
import todoit.taskbook.model.task.ReadOnlyTask;
import todoit.taskbook.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
    }

    // @@author A0140155U
    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        CommandResult cr = command.execute();
        if(command.createsNewState()){
            model.saveState(commandText);
        }
        return cr;
    }
    // @@author

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
