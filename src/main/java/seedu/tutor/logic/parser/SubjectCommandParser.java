package seedu.tutor.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tutor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_SUBJECT_DELETE;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_SUBJECT_EDIT;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_SUBJECT_RENAME;

import seedu.tutor.commons.core.index.Index;
import seedu.tutor.logic.commands.SubjectCommand;
import seedu.tutor.logic.parser.exceptions.ParseException;
import seedu.tutor.model.label.Label;

/**
 * Parses input arguments and returns a new SubjectCommand object
 */
public class SubjectCommandParser implements Parser<SubjectCommand> {

    private static final String SUBJECT_NAME_ERROR = "Subject name should be alphanumerical only.\n";

    /**
     * Parses the given {@code String} of arguments in the context of the SubjectCommand
     * and returns a SubjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SubjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT_EDIT, PREFIX_SUBJECT_RENAME,
                PREFIX_SUBJECT_DELETE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            index = null;
        }

        if (countChar(args, '\\') != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SubjectCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_SUBJECT_RENAME).isPresent()) {
            String userInput = argMultimap.getValue(PREFIX_SUBJECT_RENAME).get();
            String[] subjects = userInput.split("/");
            if (subjects.length != 2 || userInput.endsWith("/") || index != null) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SubjectCommand.MESSAGE_USAGE));
            }
            Label[] inputSubjects = getSubjectLabels(subjects);
            return new SubjectCommand(null, SubjectCommand.SubjectCommandType.RENAME, inputSubjects);
        }

        if (argMultimap.getValue(PREFIX_SUBJECT_DELETE).isPresent()) {
            String userInput = argMultimap.getValue(PREFIX_SUBJECT_DELETE).get();
            String[] subjects = userInput.split("/");
            if (subjects.length == 0 || userInput.endsWith("/") || index != null) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SubjectCommand.MESSAGE_USAGE));
            }
            Label[] inputSubjects = getSubjectLabels(subjects);
            return new SubjectCommand(null, SubjectCommand.SubjectCommandType.DELETE, inputSubjects);
        }

        if (argMultimap.getValue(PREFIX_SUBJECT_EDIT).isPresent()) {
            String userInput = argMultimap.getValue(PREFIX_SUBJECT_EDIT).get();
            String[] subjects = userInput.split("/");
            if (subjects.length == 0 || userInput.endsWith("/") || index == null) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SubjectCommand.MESSAGE_USAGE));
            }
            Label[] inputSubjects = getSubjectLabels(subjects);
            return new SubjectCommand(index, SubjectCommand.SubjectCommandType.EDIT, inputSubjects);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SubjectCommand.MESSAGE_USAGE));
    }

    /**
     * Counts the amount of a character in a String object.
     * @param s The String object.
     * @param c The character to be counted.
     */
    private int countChar(String s, char c) {
        int count = 0;
        for (char temp: s.toCharArray()) {
            if (temp == c) {
                count++;
            }
        }
        return count;
    }

    /**
     * Changes String array into Label array.
     * @param subjectStrings A String array.
     * @return A Label array.
     * @throws ParseException if non-alphanumerical character is found in the String array.
     */
    private Label[] getSubjectLabels(String[] subjectStrings) throws ParseException {
        Label[] subjects = new Label[subjectStrings.length];
        for (int i = 0; i < subjectStrings.length; i++) {
            Label temp;
            try {
                temp = ParserUtil.parseTag(subjectStrings[i]);
            } catch (ParseException pe) {
                throw new ParseException(SUBJECT_NAME_ERROR);
            }
            subjects[i] = temp;
        }
        return subjects;
    }
}
