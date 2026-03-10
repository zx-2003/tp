package seedu.tutor.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.tutor.commons.exceptions.IllegalValueException;
import seedu.tutor.model.relation.Relation;


public class JsonAdaptedRelation {

    private final String relationName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedRelation(String relationName) {
        this.relationName = relationName;
    }

    /**
     * Converts a given {@code relation} into this class for Jackson use.
     */
    public JsonAdaptedRelation(Relation source) {
        relationName = source.relationName;
    }

    @JsonValue
    public String getTagName() {
        return relationName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Relation toModelType() throws IllegalValueException {
        if (!Relation.isValidRelationName(relationName)) {
            throw new IllegalValueException(Relation.MESSAGE_CONSTRAINTS);
        }
        return new Relation(relationName);
    }
}
