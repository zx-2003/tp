package seedu.tutor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_CHANGE_SUBJECT;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_DELETE_SUBJECT;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_EDIT_SUBJECT;

import seedu.tutor.commons.core.index.Index;
import seedu.tutor.logic.commands.exceptions.CommandException;
import seedu.tutor.model.Model;
import seedu.tutor.model.label.Label;

/**
 * Changes subject field of a particular person or, change or delete a particular subject across all person
 */
public class SubjectCommand extends Command {

    public static final String COMMAND_WORD = "subject";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit subject/s for a particular person "
            + "or delete subject/s across all person "
            + "or change a particular subject across all person.\n"
            + "Parameters: "
            + "[" + PREFIX_CHANGE_SUBJECT + "OLD_SUBJECT/NEW_SUBJECT] "
            + "or "
            + "[" + PREFIX_DELETE_SUBJECT + "SUBJECT1/SUBJECT2/SUBJECT3/...] "
            + "or "
            + "INDEX (must be a positive integer) + [" + PREFIX_EDIT_SUBJECT + "SUBJECT1/SUBJECT2/SUBJECT3/...]\n"
            + "Example: "
            + COMMAND_WORD + " " + PREFIX_CHANGE_SUBJECT + "Math/AddMath, "
            + COMMAND_WORD + " " + PREFIX_DELETE_SUBJECT + "Math/Science/Chinese/History, "
            + COMMAND_WORD + " " + PREFIX_DELETE_SUBJECT + "Math/Biology, "
            + COMMAND_WORD + " 1 " + PREFIX_EDIT_SUBJECT + "Math/Science/Chinese/History, "
            + COMMAND_WORD + " 2 " + PREFIX_EDIT_SUBJECT + "Physic/Chemistry";

    /**
     * Types of SubjectCommand
     */
    public enum SubjectCommandType {
        EDIT, DELETE, CHANGE
    }

    private final Index index;
    private final SubjectCommandType type;
    private final Label[] subjects;

    /**
     * Returns a SubjectCommand object that changes the subject fields.
     * @param type Type of the SubjectCommand.
     * @param subjects Subjects in Label type.
     */
    public SubjectCommand(Index index, SubjectCommandType type, Label[] subjects) {
        this.index = index;
        this.type = type;
        this.subjects = subjects;
    }

    private Command getCommand() throws CommandException {
        switch (this.type) {

        case CHANGE:
            if (this.subjects.length != 2) {
                throw new CommandException("Input error: there should only have two subjects");
            }
            return new ChangeSubjectCommand(subjects[0], subjects[1]);

        case DELETE:
            if (this.subjects.length == 0) {
                throw new CommandException("Input error: there should be at least one subject");
            }
            return new DeleteSubjectCommand(this.subjects);

        case EDIT:
        if (this.subjects.length == 0) {
            throw new CommandException("Input error: there should be at least one subject");
        }
        return new EditSubjectCommand(this.index, this.subjects);

        default:
            // should not reach here
            return null;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Command command = this.getCommand();
        requireNonNull(command);
        return command.execute(model);
    }
}
