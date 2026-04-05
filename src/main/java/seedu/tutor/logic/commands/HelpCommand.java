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
            + "add n/NAME p/PHONE e/EMAIL a/ADDRESS [s/SUBJECT]... [t/TAG]...\n"
            + "e.g., add n/James Ho p/91234567 e/jamesho@example.com "
            + "a/123, Clementi Rd, 1234665 s/math t/friend t/colleague\n\n"
            + "delete INDEX e.g., delete 3 \n\n"
            + "list\n\n"
            + "clear\n\n"
            + "find KEYWORD [MORE KEYWORDS] e.g., find James Jake\n\n"
            + "help\n\n"
            + "edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]... [s/SUBJECT]...\n"
            + "e.g., edit 2 n/James Lee e/jameslee@example.com\n\n"
            + "relate [a\\RELATION]... [d\\RELATION]...\n"
            + "e.g., relate a\\James Lee Junior/James Lee/Son/Father";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
