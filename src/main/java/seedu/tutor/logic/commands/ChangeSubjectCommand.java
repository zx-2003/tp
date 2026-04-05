package seedu.tutor.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.tutor.logic.commands.exceptions.CommandException;
import seedu.tutor.logic.parser.EditCommandParser;
import seedu.tutor.model.Model;
import seedu.tutor.model.label.Label;
import seedu.tutor.model.person.Person;

/**
 * Changes a subject across the whole list.
 */
public class ChangeSubjectCommand extends Command {

    private final Label oldSubject;
    private final Label newSubject;
    /**
     * Returns a Command object that changes a particular subject across the whole list.
     * @param oldSubject The name of the subject to be changed.
     * @param newSubject The name of the subject after changed.
     */
    protected ChangeSubjectCommand(Label oldSubject, Label newSubject) {
        this.oldSubject = oldSubject;
        this.newSubject = newSubject;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        List<Person> persons = model.getTutorMap().getPersonList();
        boolean isChanged = false;

        for (Person currentPerson : persons) {
            if (checkPersonContainSubject(currentPerson, this.oldSubject)) {
                Person personChangedSubject = createChangeSubjectPerson(currentPerson, this.oldSubject,
                        this.newSubject);
                model.setPerson(currentPerson, personChangedSubject);
                isChanged = true;
            }
        }

        if (isChanged) {
            return new CommandResult("Subject changed: " + oldSubject.labelName + " has changed to "
                + newSubject.labelName + ".");
        } else {
            return new CommandResult("No subject changed.");
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToChangeSubject}
     */
    private static Person createChangeSubjectPerson(Person personToChangeSubject, Label oldSubject, Label newSubject) {
        requireNonNull(personToChangeSubject);
        requireNonNull(oldSubject);
        requireNonNull(newSubject);

        Set<Label> updatedSubjects = new HashSet<>(personToChangeSubject.getSubjects()); // Original subjects
        updatedSubjects.remove(oldSubject);
        updatedSubjects.add(newSubject);

        return new Person(
                personToChangeSubject.getName(),
                personToChangeSubject.getPhone(),
                personToChangeSubject.getEmail(),
                personToChangeSubject.getAddress(),
                personToChangeSubject.getTags(),
                personToChangeSubject.getRelations(),
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
