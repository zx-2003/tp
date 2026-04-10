package seedu.tutor.logic.parser;

import static seedu.tutor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Map;

import seedu.tutor.logic.commands.FindCommand;
import seedu.tutor.logic.parser.exceptions.ParseException;
import seedu.tutor.model.person.AddressContainsStringPredicate;
import seedu.tutor.model.person.EmailContainsStringPredicate;
import seedu.tutor.model.person.NameContainsKeywordsPredicate;
import seedu.tutor.model.person.PhoneNumberContainsStringPredicate;
import seedu.tutor.model.person.RelationContainsStringPredicate;
import seedu.tutor.model.person.SubjectContainsStringPredicate;
import seedu.tutor.model.person.TagContainsStringPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final String WARNING = "Keyword missing! Please specify a non-space, non-slash keyword after ";

    public static final Map<String, String> PREFIX_WARNINGS = Map.of(
            "r/", WARNING + "'r/' \nExample: find r/Alex Yeoh, find r/parent",
            "n/", WARNING + "'n/' \nExample: find n/Bob, find n/Alice Bob",
            "p/", WARNING + "'p/' \nExample: find p/12345678",
            "a/", WARNING + "'a/' \nExample: find a/Woodlands, find a/Blk",
            "t/", WARNING + "'t/' \nExample: find t/friend, find t/homework",
            "s/", WARNING + "'s/' \nExample: find s/Math, find s/Science",
            "e/", WARNING + "'e/' \nExample: find e/gmail, find e/alexyeoh123@fakemail.com"
    );


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

        if (!beginsWithValidPrefix(trimmedArgs)) {
            throw new ParseException("Prefix missing/invalid! Find must be followed by either "
                    + "'n/', 'a/', 'p/', 'e/', 's/', 'r/' or 't/' "
                    + "depending on what field is being searched for.");
        }

        assert trimmedArgs.length() > 1 : "Arg should have length of at least 2";
        String prefix = trimmedArgs.substring(0, 2);
        String trimmed = trimmedArgs.substring(2).trim();
        String slashRegex = "[ /]+$";

        if (trimmed.isEmpty() || trimmed.matches(slashRegex)) {
            throw new ParseException(PREFIX_WARNINGS.get(prefix));
        }

        switch (prefix) {
        case "r/":
            return new FindCommand(new RelationContainsStringPredicate(trimmed));
        case "n/":
            String[] nameKeywords = trimmed.split("\\s+");
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        case "p/":
            return new FindCommand(new PhoneNumberContainsStringPredicate(trimmed));
        case "a/":
            return new FindCommand(new AddressContainsStringPredicate(trimmed));
        case "t/":
            String[] tagKeywords = trimmed.split("\\s+");
            return new FindCommand(new TagContainsStringPredicate(Arrays.asList(tagKeywords)));
        case "s/":
            String[] subjectKeywords = trimmed.split("\\s+");
            return new FindCommand(new SubjectContainsStringPredicate(Arrays.asList(subjectKeywords)));
        case "e/":
            return new FindCommand(new EmailContainsStringPredicate(trimmed));
        default:
            throw new ParseException("An unexpected error has occurred.");
        }

    }

    /**
     * Checks if the string begins with a valid prefix "e/", "a/", "n/", "r/", "s/", "t/", or "p/".
     * @param string String being checked.
     * @return true if string is valid, false otherwise.
     */
    private boolean beginsWithValidPrefix(String string) {
        String[] validPrefixes = { "e/", "a/", "n/", "r/", "s/", "t/", "p/" };

        for (String validPrefix : validPrefixes) {
            if (string.startsWith(validPrefix)) {
                return true;
            }
        }

        return false;

    }

}
