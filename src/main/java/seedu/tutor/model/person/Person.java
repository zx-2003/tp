package seedu.tutor.model.person;

import static seedu.tutor.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.tutor.commons.util.ToStringBuilder;
import seedu.tutor.model.label.Label;
import seedu.tutor.model.relation.Relation;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Label> tags = new HashSet<>();
    private final Set<Relation> relations = new HashSet<>();
    // private final String subject;
    private final Set<Label> subject = new HashSet<>();

    /**
     * Complete constructor for person, other constructors kept for dependency to be removed over time
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Set<Label> tags, Set<Relation> relations, Set<Label> subject) {
        requireAllNonNull(name, phone, email, address, tags, relations, subject);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.relations.addAll(relations);
        this.subject.addAll(subject);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Set<Label> getSubjects() {
        return Collections.unmodifiableSet(subject);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Label> getTags() {
        return Collections.unmodifiableSet(tags);
    }


    public Set<Relation> getRelations() {
        return Collections.unmodifiableSet(relations);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns a set of all relations formatted for display.
     * For example, given current Person = Amy,
     * Relation "Amy/Bob/teacher/student"  or "Bob/Amy/student/teacher"
     * will be formatted as "Bob (student)"
     */
    public Set<String> formatRelationNames() {
        Set<String> formattedRelationNames = new HashSet<>();
        for (Relation relation : relations) {

            // First person is self
            String[] args = relation.relationName.split("/");
            String otherPerson = args[1];
            String otherPersonRelation = args[3];

            if (Objects.equals(args[1], name.fullName)) { // Second person is self
                otherPerson = args[0];
                otherPersonRelation = args[2];
            }

            formattedRelationNames.add(String.format(otherPerson + " (" + otherPersonRelation + ")"));
        }
        return formattedRelationNames;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                // && relations.equals(otherPerson.relations)
                && subject.equals(otherPerson.subject);
        // Relations field is ignore so that update of personToDelete is not needed after deletion of relations.
    }

    @Override
    public int hashCode() {
        // Use this method for custom fields hashing instead of implementing your own

        // return Objects.hash(name, phone, email, address, tags, relations, subject);
        // Relations field is ignore so that update of personToDelete is not needed after deletion of relations.
        return Objects.hash(name, phone, email, address, tags, subject);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("relations", relations)
                .add("subjects", subject)
                .toString();
    }
}
