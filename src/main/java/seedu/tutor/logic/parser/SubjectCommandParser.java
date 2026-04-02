package seedu.tutor.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tutor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_CHANGE_SUBJECT;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_DELETE_SUBJECT;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_EDIT_SUBJECT;

import seedu.tutor.commons.core.index.Index;
import seedu.tutor.logic.commands.SubjectCommand;
import seedu.tutor.logic.parser.exceptions.ParseException;
import seedu.tutor.model.label.Label;

/**
 * Parses input arguments and returns a new SubjectCommand object
 */
public class SubjectCommandParser implements Parser<SubjectCommand> {

    private static final String SUBJECT_NAME_ERROR = "Subject name should be alphanumerical.\n";

    /**
     * Parses the given {@code String} of arguments in the context of the SubjectCommand
     * and returns a SubjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SubjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EDIT_SUBJECT, PREFIX_CHANGE_SUBJECT,
                PREFIX_DELETE_SUBJECT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            index = null;
        }

        if (countChar(args, '\\') > 1) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT + SubjectCommand.MESSAGE_USAGE);
        }

        if (argMultimap.getValue(PREFIX_CHANGE_SUBJECT).isPresent()) {
            String temp0 = argMultimap.getValue(PREFIX_CHANGE_SUBJECT).get();
            String[] temp1 = temp0.split("/");
            if (temp1.length != 2 || temp0.endsWith("/") || index != null) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT + SubjectCommand.MESSAGE_USAGE);
            }
            Label[] labels = new Label[2];
            try {
                labels[0] = ParserUtil.parseTag(temp1[0]);
                labels[1] = ParserUtil.parseTag(temp1[1]);
            } catch (ParseException pe) {
                throw new ParseException(SUBJECT_NAME_ERROR);
            }
            return new SubjectCommand(null, SubjectCommand.SubjectCommandType.CHANGE, labels);
        }

        if (argMultimap.getValue(PREFIX_DELETE_SUBJECT).isPresent()) {
            String temp0 = argMultimap.getValue(PREFIX_DELETE_SUBJECT).get();
            String[] temp1 = temp0.split("/");
            if (temp1.length == 0 || temp0.endsWith("/") || index != null) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT + SubjectCommand.MESSAGE_USAGE);
            }
            Label[] labels = new Label[temp1.length];
            for (int i = 0; i < temp1.length; i++) {
                Label temp;
                try {
                    temp = ParserUtil.parseTag(temp1[i]);
                } catch (ParseException pe) {
                    throw new ParseException(SUBJECT_NAME_ERROR);
                }
                labels[i] = temp;
            }
            return new SubjectCommand(null, SubjectCommand.SubjectCommandType.DELETE, labels);
        }

        if (argMultimap.getValue(PREFIX_EDIT_SUBJECT).isPresent()) {
            String temp0 = argMultimap.getValue(PREFIX_EDIT_SUBJECT).get();
            String[] temp1 = temp0.split("/");
            if (temp1.length == 0 || temp0.endsWith("/") || index == null) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT + SubjectCommand.MESSAGE_USAGE);
            }
            Label[] labels = new Label[temp1.length];
            for (int i = 0; i < temp1.length; i++) {
                Label temp;
                try {
                    temp = ParserUtil.parseTag(temp1[i]);
                } catch (ParseException pe) {
                    throw new ParseException(SUBJECT_NAME_ERROR);
                }
                labels[i] = temp;
            }
            return new SubjectCommand(index, SubjectCommand.SubjectCommandType.EDIT, labels);
        }

        // should not reach here
        return null;
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
}
