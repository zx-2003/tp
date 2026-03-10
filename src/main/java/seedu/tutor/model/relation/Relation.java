package seedu.tutor.model.relation;

import static java.util.Objects.requireNonNull;
import static seedu.tutor.commons.util.AppUtil.checkArgument;

public class Relation {

    public static final String MESSAGE_CONSTRAINTS = "Relations should contain name1/name2/relation1/relation2";
    public static final String VALIDATION_REGEX="(.+)/(.+)/(.+)/(.+)";

    public final String relationName;

    public Relation(String relationName) {
        requireNonNull(relationName);
        checkArgument(isValidRelationName(relationName), MESSAGE_CONSTRAINTS);
        this.relationName = relationName;
    }

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
        return relationName.equals(otherRelation.relationName);
    }

    public String toString() {
        return '[' + relationName + ']';
    }

}
