package seedu.tutor.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.tutor.logic.parser.exceptions.ParseException;

public class RelateCommandParserTest {

    private final RelateCommandParser parser = new RelateCommandParser();
    private final String[] validFormat = new String[] {" a\\test/test1/test/test", " a\\1/1a/1/1 a\\2/2a/2/2",
        "  d\\3/3a/3/3", " d\\4/4a/4/4 d\\5/5a/5/5 a\\6/6a/6/6",
        "    a\\ a  dqwd asd/ awd wd cawf / awefaw  awefsdfweg/sdfw wef    d\\sfwgrtb/  rg wrv/rgfr gree/erfe"};
    private final String[] invalidFormat = new String[] {" a\\", " d\\", " a\\1", " d\\2/2a", " a\\3/3a/3",
        " d\\5/5a/5/5/5", " na\\test/test1/test/test"};
    private final String[] extraArgs = new String[] {" n/bruh a\\test/test1/test/test", " a\\1/1a/1/1 e/ a\\2/2a/2/2",
        " d\\4/4a/4/4 d\\5/5a/5/5 p/0123456 a\\6/6a/6/6"};
    private final String[] samePerson = new String[] {" a\\test/test/test/test", " a\\1/1/1/1 a\\2/2/2/2",
        "  d\\3/3/3/3 a\\1/2/3/4", " d\\4/4a/4/4 d\\5/5/5/5 a\\6/6/6/6"};

    @Test
    public void parse_validFormatInput_success() {
        int expectedExceptionsAmount = 0;
        int actualExceptionsAmount = 0;
        for (String input: validFormat) {
            try {
                parser.parse(input);
            } catch (ParseException e) {
                actualExceptionsAmount++;
            }
        }
        assertEquals(expectedExceptionsAmount, actualExceptionsAmount);
    }

    @Test
    public void parse_invalidFormatInput_failure() {
        int expectedExceptionAmount = invalidFormat.length;
        int actualExceptionAmount = 0;
        for (String input: invalidFormat) {
            try {
                parser.parse(input);
            } catch (ParseException e) {
                actualExceptionAmount++;
            }
        }
        assertEquals(expectedExceptionAmount, actualExceptionAmount);
    }

    @Test
    public void parse_extraArgsInput_failure() {
        int expectedExceptionAmount = extraArgs.length;
        int actualExceptionAmount = 0;
        for (String input: extraArgs) {
            try {
                parser.parse(input);
            } catch (ParseException e) {
                actualExceptionAmount++;
            }
        }
        assertEquals(expectedExceptionAmount, actualExceptionAmount);
    }

    @Test
    public void parse_samePersonInput_failure() {
        int expectedExceptionAmount = samePerson.length;
        int actualExceptionAmount = 0;
        for (String input: samePerson) {
            try {
                parser.parse(input);
            } catch (ParseException e) {
                actualExceptionAmount++;
            }
        }
        assertEquals(expectedExceptionAmount, actualExceptionAmount);
    }

}
