package seedu.tutor.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.tutor.logic.commands.exceptions.CommandException;
import seedu.tutor.model.Model;
import seedu.tutor.model.label.Label;
import seedu.tutor.model.person.Person;

/**
 * Deletes subject(s) across displayed persons.
 */
public class SubjectDeleteCommand extends Command {

    private final Label[] subjectsToDelete;

    /**
     * Returns a DeleteSubjectCommand object that deletes subject(s) across displayed persons.
     * @param subjectsToDelete An array of subject(s) as Label object to be deleted.
     */
    protected SubjectDeleteCommand(Label[] subjectsToDelete) {
        requireNonNull(subjectsToDelete);
        this.subjectsToDelete = subjectsToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> persons = model.getFilteredPersonList();
        Set<Label> deletedSubjects = new HashSet<>();

        for (Person currentPerson : persons) {
            for (Label subjectToDelete : subjectsToDelete) {
                if (checkPersonContainSubject(currentPerson, subjectToDelete)) {
                    Person personDeletedSubject = createDeleteSubjectPerson(currentPerson, subjectToDelete);
                    model.setPerson(currentPerson, personDeletedSubject);
                    currentPerson = personDeletedSubject;
                    deletedSubjects.add(subjectToDelete);
                }
            }
        }

        StringBuilder result = new StringBuilder("Deleted subject(s): ");
        for (Label subject: deletedSubjects) {
            result.append(subject.labelName);
            result.append(" ");
        }

        if (!deletedSubjects.isEmpty()) {
            return new CommandResult(result.toString());
        } else {
            return new CommandResult("No subject deleted.");
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToDeleteSubject}
     */
    private static Person createDeleteSubjectPerson(Person personToDeleteSubject, Label subjectToDelete) {
        requireNonNull(personToDeleteSubject);
        requireNonNull(subjectToDelete);

        Set<Label> updatedSubjects = new HashSet<>(personToDeleteSubject.getSubjects()); // Original subjects
        updatedSubjects.remove(subjectToDelete);

        return new Person(
                personToDeleteSubject.getName(),
                personToDeleteSubject.getPhone(),
                personToDeleteSubject.getEmail(),
                personToDeleteSubject.getAddress(),
                personToDeleteSubject.getTags(),
                personToDeleteSubject.getRelations(),
                updatedSubjects
        );
    }

    /**
     * Checks if a subject is in the subject field of a person.
     * @param personToCheck The person to check.
     * @param subject The subject.
     * @return True if contain else false.
     */
    private static boolean checkPersonContainSubject(Person personToCheck, Label subject) {
        requireNonNull(personToCheck);
        requireNonNull(subject);

        Set<Label> subjects = new HashSet<>(personToCheck.getSubjects());
        return subjects.contains(subject);
    }
}
