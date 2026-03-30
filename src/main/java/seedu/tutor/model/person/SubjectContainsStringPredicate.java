package seedu.tutor.model.person;

import java.util.Set;
import java.util.function.Predicate;

import seedu.tutor.commons.util.ToStringBuilder;
import seedu.tutor.model.label.Label;

/**
 * Tests that a {@code Person}'s {@code Subject} string contains a specific substring.
 */
public class SubjectContainsStringPredicate implements Predicate<Person> {

    private final String string;

    public SubjectContainsStringPredicate(String name) {
        this.string = name.toLowerCase();
    }

    @Override
    public boolean test(Person person) {
        Set<Label> subjects = person.getSubjects();
        for (Label s : subjects) {
            if (s.tagName.toLowerCase().contains(string.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SubjectContainsStringPredicate)) {
            return false;
        }

        SubjectContainsStringPredicate otherNameContainsKeywordsPredicate = (SubjectContainsStringPredicate) other;
        return string.equals(otherNameContainsKeywordsPredicate.string);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", string).toString();
    }
}
