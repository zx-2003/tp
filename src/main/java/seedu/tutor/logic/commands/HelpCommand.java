package seedu.tutor.logic.commands;

import seedu.tutor.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Available commands:\n"
            + "add n/NAME p/PHONE e/email a/address \n"
            + "e.g., add n/James Ho p/22224444 e/jamesho@example.com"
            + "a/123, Clementi Rd, 1234665 t/friend t/colleague \n"
            + "delete INDEX e.g., delete 3 \n"
            + "list \n"
            + "clear \n"
            + "find KEYWORD [MORE KEYWORDS] e.g., find James Jake \n"
            + "help \n"
            + "edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]..."
            + "e.g.,edit 2 n/James Lee e/jameslee@example.com";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
