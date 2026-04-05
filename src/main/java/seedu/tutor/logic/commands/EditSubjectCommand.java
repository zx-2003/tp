package seedu.tutor.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.tutor.commons.core.index.Index;
import seedu.tutor.logic.Messages;
import seedu.tutor.logic.commands.exceptions.CommandException;
import seedu.tutor.logic.parser.EditCommandParser;
import seedu.tutor.model.Model;
import seedu.tutor.model.label.Label;
import seedu.tutor.model.person.Person;

/**
 * Edits a person's subject field using xor operation.
 */
public class EditSubjectCommand extends Command {

    private final Index index;
    private final Label[] subjectsToEdits;

    /**
     * Returns a EditSubjectCommand object which edits a peron's subject field with xor operation.
     * @param index The index of the person to be edited.
     * @param subjectsToEdits The subjects to be added or removed.
     */
    protected EditSubjectCommand(Index index, Label[] subjectsToEdits) {
        this.index = index;
        this.subjectsToEdits = subjectsToEdits;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEditSubject = lastShownList.get(index.getZeroBased());
        Set<Label> oldSubjects = personToEditSubject.getSubjects();
        Set<Label> mutableOldSubjects = new HashSet<>(oldSubjects);
        Set<Label> newSubjects = subjectsXorOperation(mutableOldSubjects,
                new HashSet<>(Arrays.asList(subjectsToEdits)));

        Person edittedPerson = createEditSubjectPerson(personToEditSubject, newSubjects);
        model.setPerson(personToEditSubject, edittedPerson);

        StringBuilder result = new StringBuilder("Edited " + edittedPerson.getName()
                + "'s subject field, now contains: ");
        for (Label subject: edittedPerson.getSubjects()) {
            result.append(subject.labelName);
            result.append(" ");
        }
        return new CommandResult(result.toString());
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEditSubject}
     */
    private static Person createEditSubjectPerson(Person personToEditSubject, Set<Label> newSubjects) {
        requireNonNull(personToEditSubject);
        requireNonNull(newSubjects);

        return new Person(
                personToEditSubject.getName(),
                personToEditSubject.getPhone(),
                personToEditSubject.getEmail(),
                personToEditSubject.getAddress(),
                personToEditSubject.getTags(),
                personToEditSubject.getRelations(),
                newSubjects
        );
    }

    /**
     * Merges two Collection objects using xor operation.
     * @param collection1 The first Collection object.
     * @param collection2 The second Collection object.
     * @return The merge result in a Set object.
     */
    private static Set<Label> subjectsXorOperation(Collection<Label> collection1,
                                                   Collection<Label> collection2) {
        for (Label subject: collection2) {
            if (collection1.contains(subject)) {
                collection1.remove(subject);
            } else {
                collection1.add(subject);
            }
        }

        return new HashSet<>(collection1);
    }
}
