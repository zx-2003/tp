package seedu.tutor.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.tutor.model.ReadOnlyTutorMap;
import seedu.tutor.model.TutorMap;
import seedu.tutor.model.label.Label;
import seedu.tutor.model.person.Address;
import seedu.tutor.model.person.Email;
import seedu.tutor.model.person.Name;
import seedu.tutor.model.person.Person;
import seedu.tutor.model.person.Phone;
import seedu.tutor.model.relation.Relation;

/**
 * Contains utility methods for populating {@code TutorMap} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), getRelationSet(), getSubjectSet("Maths", "Science")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), getRelationSet(), getSubjectSet("English")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), getRelationSet(), getSubjectSet("Chinese")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), getRelationSet(), getSubjectSet("Science")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), getRelationSet(), getSubjectSet("Maths")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), getRelationSet(), getSubjectSet("English")),
        };
    }

    public static ReadOnlyTutorMap getSampleTutorMap() {
        TutorMap sampleAb = new TutorMap();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Label> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Label::new)
                .collect(Collectors.toSet());
    }

    public static Set<Label> getSubjectSet(String... strings) {
        return Arrays.stream(strings)
                .map(Label::new)
                .collect(Collectors.toSet());
    }

    public static Set<Relation> getRelationSet(String... strings) {
        return Arrays.stream(strings)
                .map(Relation::new)
                .collect(Collectors.toSet());
    }

}
