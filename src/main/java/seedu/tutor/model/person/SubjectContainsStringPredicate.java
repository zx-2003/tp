package seedu.tutor.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.tutor.commons.util.ToStringBuilder;
import seedu.tutor.model.label.Label;

/**
 * Tests that a {@code Person}'s {@code Subject} string contains a specific substring.
 */
public class SubjectContainsStringPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public SubjectContainsStringPredicate(String keyword) {
        this(List.of(keyword));
    }

    /**
     * Constructs a predicate using multiple keywords.
     * Each keyword is trimmed, lowercased, and empty tokens are ignored.
     *
     * @param keywords Keywords to match against a person's subject labels.
     */
    public SubjectContainsStringPredicate(List<String> keywords) {
        requireNonNull(keywords);
        this.keywords = keywords.stream()
                .map(String::trim)
                .filter(keyword -> !keyword.isEmpty())
                .map(String::toLowerCase)
                .toList();
    }

    @Override
    public boolean test(Person person) {
        Set<Label> subjects = person.getSubjects();
        for (Label s : subjects) {
            String subjectName = s.labelName.toLowerCase();
            if (keywords.stream().anyMatch(subjectName::contains)) {
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
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
