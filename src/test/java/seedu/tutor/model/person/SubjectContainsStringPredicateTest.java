package seedu.tutor.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.tutor.testutil.PersonBuilder;

public class SubjectContainsStringPredicateTest {

    @Test
    public void equals() {
        SubjectContainsStringPredicate firstPredicate =
                new SubjectContainsStringPredicate(Arrays.asList("math", "english"));
        SubjectContainsStringPredicate secondPredicate =
                new SubjectContainsStringPredicate(Arrays.asList("science", "chinese"));

        assertTrue(firstPredicate.equals(firstPredicate));

        SubjectContainsStringPredicate firstPredicateCopy =
                new SubjectContainsStringPredicate(Arrays.asList("math", "english"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_subjectContainsKeyword_returnsTrue() {
        Person person = new PersonBuilder().withSubjects("Math").build();
        SubjectContainsStringPredicate predicate =
                new SubjectContainsStringPredicate(Arrays.asList("Math", "Chinese"));
        assertTrue(predicate.test(person));

        predicate = new SubjectContainsStringPredicate(Arrays.asList("zzz", "ath"));
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_subjectContainsKeywordButInDifferentCase_returnsTrue() {
        Person person = new PersonBuilder().withSubjects("Science").build();
        SubjectContainsStringPredicate predicate =
                new SubjectContainsStringPredicate(Arrays.asList("science", "math"));
        assertTrue(predicate.test(person));

        predicate = new SubjectContainsStringPredicate(Arrays.asList("ScI"));
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_subjectDoesNotContainKeyword_returnsFalse() {
        Person person = new PersonBuilder().withSubjects("English").build();
        SubjectContainsStringPredicate predicate =
                new SubjectContainsStringPredicate(Arrays.asList("Math", "Chinese"));
        assertFalse(predicate.test(person));

        Person personWithoutSubject = new PersonBuilder().build();
        predicate = new SubjectContainsStringPredicate(Arrays.asList("English", "Science"));
        assertFalse(predicate.test(personWithoutSubject));
    }

    @Test
    public void toStringMethod() {
        SubjectContainsStringPredicate predicate =
                new SubjectContainsStringPredicate(Arrays.asList("Math", "Chinese"));
        String expected = SubjectContainsStringPredicate.class.getCanonicalName() + "{keywords=[math, chinese]}";
        assertEquals(expected, predicate.toString());
    }
}

