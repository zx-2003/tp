package seedu.tutor.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.tutor.logic.parser.Prefix;
import seedu.tutor.model.person.Person;
import seedu.tutor.model.relation.Relation;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String PERSONS_DOES_NOT_EXIST = "The provided person name does not exist.";
    public static final String RELATIONS_ALREADY_EXIST = "The relation already exists.";
    public static final String REPEATED_ARGUMENT = "Repeated argument(s) detected.";
    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {

        StringBuilder relations = new StringBuilder();
        for (Relation relation: person.getRelations()) {
            relations.append(relation.toString());
        }

        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Relations: ")
                .append(relations)
                .append("; Subject: ");
        person.getSubjects().forEach(builder::append);
        builder.append(" Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

}
