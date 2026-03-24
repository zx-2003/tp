package seedu.tutor.model.relation;

import static java.util.Objects.requireNonNull;
import static seedu.tutor.commons.util.AppUtil.checkArgument;

/**
 * Represents a Relation in the tutormap.
 * Guarantees: immutable; name is valid as declared in {@link #isValidRelationName(String)}
 */
public class Relation {

    public static final String MESSAGE_CONSTRAINTS = "Relations should contain name1/name2/relation1/relation2";
    public static final String VALIDATION_REGEX = "([^/]+)/([^/]+)/([^/]+)/([^/]+)";

    public final String relationName;
    public final String reverseRelationName;

    /**
     * Constructs a {@code Relation}.
     *
     * @param relationName A valid relation name.
     */
    public Relation(String relationName) {
        requireNonNull(relationName);
        checkArgument(isValidRelationName(relationName), MESSAGE_CONSTRAINTS);
        this.relationName = relationName;
        this.reverseRelationName = reverseRelation(relationName);
    }

    private String reverseRelation(String relationName) {
        String[] args = relationName.split("/");
        String temp = args[0];
        args[0] = args[1];
        args[1] = temp;
        temp = args[2];
        args[2] = args[3];
        args[3] = temp;
        return String.join("/", args);
    }

    /**
     * Returns true if a given string is a valid relation name.
     */
    public static boolean isValidRelationName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Relation)) {
            return false;
        }

        Relation otherRelation = (Relation) other;
        return relationName.equals(otherRelation.relationName)
                || reverseRelationName.equals(otherRelation.relationName);
    }

    @Override
    public int hashCode() {
        String biggerRelation;
        if (reverseRelationName.compareTo(relationName) > 0) {
            biggerRelation = reverseRelationName;
        } else {
            biggerRelation = relationName;
        }
        return biggerRelation.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + relationName + ']';
    }

    /**
     * returns a new Relation object with updated person's name.
     * @param oldPerson Person's name before update.
     * @param newPerson Person's name after update.
     */
    public Relation changePerson(String oldPerson, String newPerson) {
        String[] args = relationName.split("/");
        if (args[0].equals(oldPerson)) {
            args[0] = newPerson;
        } else if (args[1].equals(oldPerson)) {
            args[1] = newPerson;
        }
        String relation = String.join("/", args);
        return new Relation(relation);
    }

}
