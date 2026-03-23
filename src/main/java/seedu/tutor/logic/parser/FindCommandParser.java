package seedu.tutor.logic.parser;

import static seedu.tutor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.tutor.logic.commands.FindCommand;
import seedu.tutor.logic.parser.exceptions.ParseException;
import seedu.tutor.model.person.NameContainsKeywordsPredicate;
import seedu.tutor.model.person.RelationContainsStringPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.startsWith("r/")) {
            System.out.println(trimmedArgs);
            String[] split = trimmedArgs.split("/");
            if (split.length < 1) {
                throw new ParseException("Name missing! Please specify a name after 'r/'"
                        + "Example: find r/Alex Yeoh");
            }

            String name = trimmedArgs.split("/")[1];

            return new FindCommand(new RelationContainsStringPredicate(name));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
