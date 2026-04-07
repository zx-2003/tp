package seedu.tutor.logic.commands;

import static seedu.tutor.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tutor.logic.commands.HelpCommand.DEFAULT_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.tutor.model.Model;
import seedu.tutor.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(DEFAULT_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(""), model, expectedCommandResult, expectedModel);
    }
}
