package seedu.tutor.logic.parser;

import static seedu.tutor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tutor.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tutor.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.tutor.logic.commands.FindCommand;
import seedu.tutor.model.person.SubjectContainsStringPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validSubjectArgs_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(new SubjectContainsStringPredicate("Math"));
        assertParseSuccess(parser, "s/Math", expectedFindCommand);

        FindCommand expectedFindCommandWithWhitespace =
                new FindCommand(new SubjectContainsStringPredicate("Science"));
        assertParseSuccess(parser, " \n s/   Science  ", expectedFindCommandWithWhitespace);
    }

    @Test
    public void parse_invalidSubjectArgs_throwsParseException() {
        String expectedMessage = "Keyword missing! Please specify a non-space, "
                + "non-slash keyword (subject) after 's/' \n"
                + "Example: find s/Math, find s/Science";

        assertParseFailure(parser, "s/", expectedMessage);
        assertParseFailure(parser, "s/   ", expectedMessage);
    }

}
