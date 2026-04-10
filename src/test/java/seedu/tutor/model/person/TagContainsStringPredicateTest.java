package seedu.tutor.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.tutor.testutil.PersonBuilder;

public class TagContainsStringPredicateTest {

    @Test
    public void equals() {
        TagContainsStringPredicate firstPredicate =
                new TagContainsStringPredicate(Arrays.asList("friend", "paid"));
        TagContainsStringPredicate secondPredicate =
                new TagContainsStringPredicate(Arrays.asList("study", "online"));

        assertTrue(firstPredicate.equals(firstPredicate));

        TagContainsStringPredicate firstPredicateCopy =
                new TagContainsStringPredicate(Arrays.asList("friend", "paid"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeyword_returnsTrue() {
        Person person = new PersonBuilder().withTags("friend").build();
        TagContainsStringPredicate predicate =
                new TagContainsStringPredicate(Arrays.asList("friend", "paid"));
        assertTrue(predicate.test(person));

        predicate = new TagContainsStringPredicate(Arrays.asList("study", "rie"));
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_tagContainsKeywordButInDifferentCase_returnsTrue() {
        Person person = new PersonBuilder().withTags("StudyBuddy").build();
        TagContainsStringPredicate predicate =
                new TagContainsStringPredicate(Arrays.asList("studybuddy", "friend"));
        assertTrue(predicate.test(person));

        predicate = new TagContainsStringPredicate(Arrays.asList("STUDY"));
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_tagDoesNotContainKeyword_returnsFalse() {
        Person person = new PersonBuilder().withTags("friend").build();
        TagContainsStringPredicate predicate =
                new TagContainsStringPredicate(Arrays.asList("study", "paid"));
        assertFalse(predicate.test(person));

        Person personWithoutTag = new PersonBuilder().build();
        predicate = new TagContainsStringPredicate(Arrays.asList("friend", "paid"));
        assertFalse(predicate.test(personWithoutTag));
    }

    @Test
    public void toStringMethod() {
        TagContainsStringPredicate predicate =
                new TagContainsStringPredicate(Arrays.asList("Friend", "Paid"));
        String expected = TagContainsStringPredicate.class.getCanonicalName() + "{keywords=[friend, paid]}";
        assertEquals(expected, predicate.toString());
    }
}

