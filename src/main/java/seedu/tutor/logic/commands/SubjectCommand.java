package seedu.tutor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_SUBJECT_DELETE;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_SUBJECT_EDIT;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_SUBJECT_RENAME;

import seedu.tutor.commons.core.index.Index;
import seedu.tutor.logic.commands.exceptions.CommandException;
import seedu.tutor.model.Model;
import seedu.tutor.model.label.Label;

/**
 * Changes the Subject field of a Person, or renames/deletes a subject across all Persons.
 */
public class SubjectCommand extends Command {

    public static final String COMMAND_WORD = "subject";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete subject(s) across all person "
            + "or edit subject(s) for a particular person "
            + "or rename a particular subject across all person.\n"
            + "Parameters: "
            + "[" + PREFIX_SUBJECT_DELETE + "SUBJECT1/SUBJECT2/SUBJECT3/...] "
            + "or "
            + "INDEX (must be a positive integer) + [" + PREFIX_SUBJECT_EDIT + "SUBJECT1/SUBJECT2/SUBJECT3/...] "
            + "or "
            + "[" + PREFIX_SUBJECT_RENAME + "OLD_SUBJECT/NEW_SUBJECT]\n"
            + "Example:\n"
            + COMMAND_WORD + " " + PREFIX_SUBJECT_DELETE + "Math/Science/Chinese/History\n"
            + COMMAND_WORD + " " + PREFIX_SUBJECT_DELETE + "Math/Biology\n"
            + COMMAND_WORD + " 1 " + PREFIX_SUBJECT_EDIT + "Math/Science/Chinese/History\n"
            + COMMAND_WORD + " 2 " + PREFIX_SUBJECT_EDIT + "Physic/Chemistry"
            + COMMAND_WORD + " " + PREFIX_SUBJECT_RENAME + "Math/AddMath\n";

    /**
     * Types of SubjectCommand
     */
    public enum SubjectCommandType {
        EDIT, DELETE, RENAME
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

    /**
     * Returns a concrete type of Command object that changes or deletes or edits subject(s).
     */
    private Command getCommand() throws CommandException {

        switch (this.type) {

        case RENAME:
            if (this.subjects.length != 2) {
                throw new CommandException("Input error: there should only have two subjects");
            }
            return new SubjectRenameCommand(subjects[0], subjects[1]);

        case DELETE:
            if (this.subjects.length == 0) {
                throw new CommandException("Input error: there should be at least one subject");
            }
            return new SubjectDeleteCommand(this.subjects);

        case EDIT:
            if (this.subjects.length == 0) {
                throw new CommandException("Input error: there should be at least one subject");
            }
            return new SubjectEditCommand(this.index, this.subjects);

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
