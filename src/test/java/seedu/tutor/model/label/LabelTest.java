package seedu.tutor.model.label;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tutor.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LabelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Label(null));
    }

    @Test
    public void constructor_invalidLabelName_throwsIllegalArgumentException() {
        String invalidLabelName = "";
        assertThrows(IllegalArgumentException.class, () -> new Label(invalidLabelName));
    }

    @Test
    public void isValidLabelName() {
        // null label name
        assertThrows(NullPointerException.class, () -> Label.isValidLabelName(null));

        // invalid label names
        assertFalse(Label.isValidLabelName(""));
        assertFalse(Label.isValidLabelName(" "));
        assertFalse(Label.isValidLabelName("math science"));

        // valid label names
        assertTrue(Label.isValidLabelName("math"));
        assertTrue(Label.isValidLabelName("Math2"));
        assertTrue(Label.isValidLabelName("CS2103T"));
    }

    @Test
    public void equals() {
        Label math = new Label("math");
        Label mathCopy = new Label("math");
        Label science = new Label("science");

        assertTrue(math.equals(math));
        assertTrue(math.equals(mathCopy));
        assertFalse(math.equals(1));
        assertFalse(math.equals(science));
    }

}
