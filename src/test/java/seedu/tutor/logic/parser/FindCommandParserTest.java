package seedu.tutor.logic.parser;

import static seedu.tutor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tutor.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tutor.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.tutor.logic.commands.FindCommand;
import seedu.tutor.model.person.SubjectContainsStringPredicate;
import seedu.tutor.model.person.TagContainsStringPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validSubjectArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new SubjectContainsStringPredicate(Arrays.asList("Math", "Chinese")));
        assertParseSuccess(parser, "s/Math Chinese", expectedFindCommand);

        FindCommand expectedFindCommandWithWhitespace =
                new FindCommand(new SubjectContainsStringPredicate(Arrays.asList("English", "Math")));
        assertParseSuccess(parser, " \n s/   English    Math  ", expectedFindCommandWithWhitespace);
    }

    @Test
    public void parse_validTagArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new TagContainsStringPredicate(Arrays.asList("Friend", "Paid")));
        assertParseSuccess(parser, "t/Friend Paid", expectedFindCommand);

        FindCommand expectedFindCommandWithWhitespace =
                new FindCommand(new TagContainsStringPredicate(Arrays.asList("Friend", "Paid")));
        assertParseSuccess(parser, " \n t/    Friend    Paid  ", expectedFindCommandWithWhitespace);
    }

    @Test
    public void parse_invalidSubjectArgs_throwsParseException() {
        String expectedMessage = FindCommandParser.PREFIX_WARNINGS.get("s/");

        assertParseFailure(parser, "s/", expectedMessage);
        assertParseFailure(parser, "s/   ", expectedMessage);
    }

    @Test
    public void parse_invalidTagArgs_throwsParseException() {
        String expectedMessage = FindCommandParser.PREFIX_WARNINGS.get("t/");

        assertParseFailure(parser, "t/", expectedMessage);
        assertParseFailure(parser, "t/   ", expectedMessage);
    }

}
