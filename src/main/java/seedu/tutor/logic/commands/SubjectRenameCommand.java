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
 * Renames a subject across the whole list.
 */
public class SubjectRenameCommand extends Command {

    private final Label oldSubject;
    private final Label newSubject;
    /**
     * Returns a Command object that renames a particular subject across the whole list.
     * @param oldSubject The name of the subject to be changed.
     * @param newSubject The name of the subject after changed.
     */
    protected SubjectRenameCommand(Label oldSubject, Label newSubject) {
        requireNonNull(oldSubject);
        requireNonNull(newSubject);
        this.oldSubject = oldSubject;
        this.newSubject = newSubject;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> persons = model.getFilteredPersonList();
        boolean isChanged = false;

        for (Person currentPerson : persons) {
            if (checkPersonContainSubject(currentPerson, this.oldSubject)) {
                Person personChangedSubject = createRenameSubjectPerson(currentPerson, this.oldSubject,
                        this.newSubject);
                model.setPerson(currentPerson, personChangedSubject);
                isChanged = true;
            }
        }

        if (isChanged) {
            return new CommandResult("Subject renamed: " + oldSubject.labelName + " has renamed to "
                + newSubject.labelName + ".");
        } else {
            return new CommandResult("No subject renamed.");
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToRenameSubject}
     */
    private static Person createRenameSubjectPerson(Person personToRenameSubject, Label oldSubject, Label newSubject) {
        requireNonNull(personToRenameSubject);
        requireNonNull(oldSubject);
        requireNonNull(newSubject);

        Set<Label> updatedSubjects = new HashSet<>(personToRenameSubject.getSubjects()); // Original subjects
        updatedSubjects.remove(oldSubject);
        updatedSubjects.add(newSubject);

        return new Person(
                personToRenameSubject.getName(),
                personToRenameSubject.getPhone(),
                personToRenameSubject.getEmail(),
                personToRenameSubject.getAddress(),
                personToRenameSubject.getTags(),
                personToRenameSubject.getRelations(),
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
