package seedu.tutor.logic.commands;

import seedu.tutor.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String DEFAULT_HELP_MESSAGE = "Available commands:"
            + "(type `help [COMMAND]` to see details of each command, eg. `help add`):\n"
            + "- list\n"
            + "- add\n"
            + "- edit\n"
            + "- delete\n"
            + "- clear\n"
            + "- relate\n"
            + "- subject\n"
            + "- find \n"
            + "- exit \n";

    private final String commandString;

    private String helpMessage;

    public HelpCommand(String commandString) {
        this.commandString = commandString.trim().toLowerCase();
    }

    @Override
    public CommandResult execute(Model model) {

        switch (commandString) {

        case AddCommand.COMMAND_WORD:
            helpMessage = AddCommand.MESSAGE_USAGE;
            break;

        case EditCommand.COMMAND_WORD:
            helpMessage = EditCommand.MESSAGE_USAGE;
            break;

        case DeleteCommand.COMMAND_WORD:
            helpMessage = DeleteCommand.MESSAGE_USAGE;
            break;

        case ClearCommand.COMMAND_WORD:
            helpMessage = ClearCommand.MESSAGE_USAGE;
            break;

        case FindCommand.COMMAND_WORD:
            helpMessage = FindCommand.MESSAGE_USAGE;
            break;

        case ListCommand.COMMAND_WORD:
            helpMessage = ListCommand.MESSAGE_USAGE;
            break;

        case ExitCommand.COMMAND_WORD:
            helpMessage = ExitCommand.MESSAGE_USAGE;
            break;

        case RelateCommand.COMMAND_WORD:
            helpMessage = RelateCommand.MESSAGE_USAGE;
            break;

        case SubjectCommand.COMMAND_WORD:
            helpMessage = SubjectCommand.MESSAGE_USAGE;
            break;

        default:
            helpMessage = DEFAULT_HELP_MESSAGE;
        }

        return new CommandResult(helpMessage, true, false);
    }
}
