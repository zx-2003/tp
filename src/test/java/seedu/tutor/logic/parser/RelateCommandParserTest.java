package seedu.tutor.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.tutor.logic.parser.exceptions.ParseException;

public class RelateCommandParserTest {

    private final RelateCommandParser parser = new RelateCommandParser();
    private final String[] validInput = new String[] {"a\\test/test/test/test", "a\\1/1/1/1 a\\2/2/2/2", "d\\3/3/3/3",
        "d\\4/4/4/4 d\\5/5/5/5 a\\6/6/6/6",
        "    a\\ a  dqwd asd/ awd wd cawf / awefaw  awefsdfweg/sdfw wef    d\\sfwgrtb/  rg wrv/rgfr gree/erfe"};
    private final String[] invalidInput = new String[] {"a\\", "d\\", "a\\1", "d\\2/2", "a\\3/3/3", "d\\5/5/5/5/5",
        "na\\test/test/test/test"};
    private final String[] extraArgs = new String[] {"n/bruh a\\test/test/test/tes", "a\\1/1/1/1 e/ a\\2/2/2/2",
        "d\\3/3/3/3", "d\\4/4/4/4 d\\5/5/5/5 p/0123456 a\\6/6/6/6"};

    @Test
    public void parse_validInput_success() {
        int expect = validInput.length;
        int test = 7;
        for (String input: validInput) {
            try {
                parser.parse(input);
            } catch (ParseException e) {
                test--;
            }
        }
        assertEquals(expect, test);
    }

    @Test
    public void parse_invalidInputFormat_failure() {
        int expect = invalidInput.length;
        int test = 0;
        for (String input: invalidInput) {
            try {
                parser.parse(input);
            } catch (ParseException e) {
                test++;
            }
        }
        assertEquals(expect, test);
    }

    @Test
    public void parse_extraArgsInput_failure() {
        int expect = extraArgs.length;
        int test = 0;
        for (String input: extraArgs) {
            try {
                parser.parse(input);
            } catch (ParseException e) {
                test++;
            }
        }
        assertEquals(expect, test);
    }
}
