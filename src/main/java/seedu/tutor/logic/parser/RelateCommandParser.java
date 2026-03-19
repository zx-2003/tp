package seedu.tutor.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tutor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_RELATE_ADD;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_RELATE_DELETE;

import java.util.HashSet;
import java.util.Set;

import seedu.tutor.logic.commands.RelateCommand;
import seedu.tutor.logic.parser.exceptions.ParseException;
import seedu.tutor.model.relation.Relation;

/**
 * Parses input arguments and returns a new RelateCommand object
 */
public class RelateCommandParser implements Parser<RelateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RelateCommand
     * and returns a RelateCommand object for execution.
     * @param args The full user's input.
     * @return A RelateCommand object.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RelateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_RELATE_ADD, PREFIX_RELATE_DELETE);

        // errors
        if (argMultimap.getAllValues(PREFIX_RELATE_ADD).isEmpty()
                && argMultimap.getAllValues(PREFIX_RELATE_DELETE).isEmpty() || !validCommand(args)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelateCommand.MESSAGE_USAGE));
        }

        Set<Relation> relationsToAdd = new HashSet<>();
        Set<Relation> relationsToDelete = new HashSet<>();
        if (!argMultimap.getAllValues(PREFIX_RELATE_ADD).isEmpty()) {
            for (String rel : argMultimap.getAllValues(PREFIX_RELATE_ADD)) {
                relationsToAdd.add(ParserUtil.parseRelation(rel));
            }
        }
        if (!argMultimap.getAllValues(PREFIX_RELATE_DELETE).isEmpty()) {
            for (String rel : argMultimap.getAllValues(PREFIX_RELATE_DELETE)) {
                relationsToDelete.add(ParserUtil.parseRelation(rel));
            }
        }

        return new RelateCommand(relationsToAdd, relationsToDelete);
    }

    /**
     * A linear string checker.
     * @param args The user's input.
     * @return The validity of the user's input for relate command.
     */
    private boolean validCommand(String args) {
        args = args.trim();
        int len = args.length();
        int accu = 0;
        char[] arr = args.toCharArray();
        int index = 2;

        if (!(args.startsWith("a\\") || args.startsWith("d\\"))) {
            return false;
        }

        while (index != len) {
            Character current = arr[index];
            if (current.equals('/')) {
                accu++;
            } else if (current.equals('\\')) {
                accu = 0;
            }

            if (accu > 3) {
                return false;
            }
            index++;
        }
        return true;
    }
}
