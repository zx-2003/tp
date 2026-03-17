package seedu.tutor.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.tutor.commons.core.index.Index;
import seedu.tutor.commons.util.ToStringBuilder;
import seedu.tutor.logic.Messages;
import seedu.tutor.logic.commands.exceptions.CommandException;
import seedu.tutor.model.Model;
import seedu.tutor.model.person.Person;
import seedu.tutor.model.relation.Relation;

/**
 * Deletes relation(s) from specified Person.
 */
public class RelateDeleteCommand extends RelateCommand {

    public static final String MESSAGE_INVALID_RELATION_TO_DELETE = "The relation does not exist in Person";

    private final Index index;
    private final Relation relationToDelete;

    /**
     * Creates a RelateDeleteCommand.
     * This constructor is package-private to restrict creation
     * to the command factory in {@link RelateCommand}.
     */
    RelateDeleteCommand(Index index, Relation relationToDelete) {
        super(relationToDelete, RelateCommandType.DELETE);
        requireNonNull(index);
        requireNonNull(relationToDelete);

        this.index = index;
        this.relationToDelete = relationToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> persons = model.getTutorMap().getPersonList();

        if (index.getZeroBased() >= persons.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDeleteRelation = persons.get(index.getZeroBased());

        // Validate existence of Relation in Person
        Set<Relation> originalRelations = personToDeleteRelation.getRelations();

        Relation relationSearched = null;
        for (Relation relation: originalRelations) {
            if (relation.equals(relationToDelete)) {
                relationSearched = relation;
                break;
            }
        }

        if (relationSearched == null) {
            throw new CommandException(MESSAGE_INVALID_RELATION_TO_DELETE);
        }

        Person addedRelationPerson = createDeleteRelationPerson(personToDeleteRelation, relationSearched);
        model.setPerson(personToDeleteRelation, addedRelationPerson);
        return new CommandResult(String.format(MESSAGE_RELATE_SUCCESS, Messages.format(addedRelationPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToAddRelation}
     */
    private static Person createDeleteRelationPerson(Person personToDeleteRelation, Relation relationToDelete) {
        assert personToDeleteRelation != null;

        Set<Relation> updatedRelations = new HashSet<>(personToDeleteRelation.getRelations()); // original relations
        updatedRelations.remove(relationToDelete);

        return new Person(
                personToDeleteRelation.getName(),
                personToDeleteRelation.getPhone(),
                personToDeleteRelation.getEmail(),
                personToDeleteRelation.getAddress(),
                personToDeleteRelation.getTags(),
                updatedRelations,
                personToDeleteRelation.getSubject()
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RelateDeleteCommand)) {
            return false;
        }

        RelateDeleteCommand otherRelateDeleteCommand = (RelateDeleteCommand) other;
        return index.equals(otherRelateDeleteCommand.index)
                && relationToDelete.equals(otherRelateDeleteCommand.relationToDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("relationToDelete", relationToDelete)
                .toString();
    }
}
