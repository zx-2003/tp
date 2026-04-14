package seedu.tutor.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tutor.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should contain 3 digits in the main body. "
                    + "Country code and area code may be included in the parentheses and "
                    + "must contain only numbers with an optional `+` after the opening bracket. "
                    + "You may use a single space or dash between digits for readability. "
                    + "Examples: (+65) 9876 5432, (+1)(202) 555-0123, 98765432";
    public static final String VALIDATION_REGEX = "^(?=(?:\\D*\\d){3,})(\\(\\+?\\d+\\))?"
            + "(\\(\\d+\\))?[\\s-]?\\d([\\s-]?\\d){2,}$";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
