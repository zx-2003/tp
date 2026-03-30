package seedu.tutor.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.tutor.commons.exceptions.IllegalValueException;
import seedu.tutor.model.label.Label;
import seedu.tutor.model.person.Address;
import seedu.tutor.model.person.Email;
import seedu.tutor.model.person.Name;
import seedu.tutor.model.person.Person;
import seedu.tutor.model.person.Phone;
import seedu.tutor.model.relation.Relation;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> subject = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedRelation> relations = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("subject") List<JsonAdaptedTag> subject,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("relations") List<JsonAdaptedRelation> relations) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (subject != null) {
            this.subject.addAll(subject);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (relations != null) {
            this.relations.addAll(relations);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        subject.addAll(source.getSubjects().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        relations.addAll(source.getRelations().stream()
                .map(JsonAdaptedRelation::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Label> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        final List<Label> personSubjects = new ArrayList<>();
        for (JsonAdaptedTag subject : subject) {
            personSubjects.add(subject.toModelType());
        }

        final List<Relation> personRelations = new ArrayList<>();
        for (JsonAdaptedRelation relation: relations) {
            personRelations.add(relation.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Label> modelSubject = new HashSet<>(personSubjects);

        final Set<Label> modelTags = new HashSet<>(personTags);

        final Set<Relation> modelRelations = new HashSet<>(personRelations);

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelRelations, modelSubject);
    }

}
