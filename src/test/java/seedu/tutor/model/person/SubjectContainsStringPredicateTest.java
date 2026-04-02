package seedu.tutor.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.tutor.testutil.PersonBuilder;

public class SubjectContainsStringPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "math";
        String secondPredicateKeyword = "science";

        SubjectContainsStringPredicate firstPredicate = new SubjectContainsStringPredicate(firstPredicateKeyword);
        SubjectContainsStringPredicate secondPredicate = new SubjectContainsStringPredicate(secondPredicateKeyword);

        assertTrue(firstPredicate.equals(firstPredicate));

        SubjectContainsStringPredicate firstPredicateCopy = new SubjectContainsStringPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_subjectContainsKeyword_returnsTrue() {
        Person person = new PersonBuilder().withSubjects("Math").build();
        SubjectContainsStringPredicate predicate = new SubjectContainsStringPredicate("Math");
        assertTrue(predicate.test(person));

        predicate = new SubjectContainsStringPredicate("ath");
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_subjectContainsKeywordButInDifferentCase_returnsTrue() {
        Person person = new PersonBuilder().withSubjects("Science").build();
        SubjectContainsStringPredicate predicate = new SubjectContainsStringPredicate("science");
        assertTrue(predicate.test(person));

        predicate = new SubjectContainsStringPredicate("ScI");
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_subjectDoesNotContainKeyword_returnsFalse() {
        Person person = new PersonBuilder().withSubjects("English").build();
        SubjectContainsStringPredicate predicate = new SubjectContainsStringPredicate("Math");
        assertFalse(predicate.test(person));

        Person personWithoutSubject = new PersonBuilder().build();
        predicate = new SubjectContainsStringPredicate("English");
        assertFalse(predicate.test(personWithoutSubject));
    }

    @Test
    public void toStringMethod() {
        SubjectContainsStringPredicate predicate = new SubjectContainsStringPredicate("Math");
        String expected = SubjectContainsStringPredicate.class.getCanonicalName() + "{keyword=math}";
        assertEquals(expected, predicate.toString());
    }
}

