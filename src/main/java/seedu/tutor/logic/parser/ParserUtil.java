package seedu.tutor.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.tutor.commons.core.index.Index;
import seedu.tutor.commons.util.StringUtil;
import seedu.tutor.logic.parser.exceptions.ParseException;
import seedu.tutor.model.label.Label;
import seedu.tutor.model.person.Address;
import seedu.tutor.model.person.Email;
import seedu.tutor.model.person.Name;
import seedu.tutor.model.person.Phone;
import seedu.tutor.model.relation.Relation;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Label parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Label.isValidTagName(trimmedTag)) {
            throw new ParseException(Label.MESSAGE_CONSTRAINTS);
        }
        return new Label(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Label> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Label> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String subject} into a {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Subject} is invalid.
     */
    public static Label parseSubject(String subject) throws ParseException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!Label.isValidTagName(trimmedSubject)) {
            throw new ParseException(Label.MESSAGE_CONSTRAINTS);
        }
        return new Label(trimmedSubject);
    }

    /**
     * Parses {@code Collection<String> subjects} into a {@code Set<Tag>}.
     */
    public static Set<Label> parseSubjects(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Label> subjectSet = new HashSet<>();
        for (String tagName : tags) {
            subjectSet.add(parseTag(tagName));
        }
        return subjectSet;
    }


    /**
     * Parses a {@code String relations} into a {@code Relation}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code relation} is invalid.
     */
    public static Relation parseRelation(String relation) throws ParseException {
        requireNonNull(relation);
        String trimmedRelation = relation.trim();
        if (!Relation.isValidRelationName(trimmedRelation)) {
            throw new ParseException(Relation.MESSAGE_CONSTRAINTS);
        }

        // Trim each segment
        String[] parts = trimmedRelation.split("/");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        // Ensure that names are different
        if (parts[0].equals(parts[1])) {
            throw new ParseException(Relation.MESSAGE_SAME_PERSON);
        }

        String trimmedReconstructed = String.join("/", parts);

        return new Relation(trimmedReconstructed);
    }

    /**
     * Parses {@code Collection<String> relations} into a {@code Set<Relation>}.
     */
    public static Set<Relation> parseRelations(Collection<String> relations) throws ParseException {
        requireNonNull(relations);
        final Set<Relation> relationSet = new HashSet<>();
        for (String relationName : relations) {
            relationSet.add(parseRelation(relationName));
        }
        return relationSet;
    }

}
