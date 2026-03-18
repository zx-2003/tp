package seedu.tutor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_RELATE_ADD;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_RELATE_DELETE;

import javafx.collections.ObservableList;
import seedu.tutor.commons.core.index.Index;
import seedu.tutor.logic.Messages;
import seedu.tutor.logic.commands.exceptions.CommandException;
import seedu.tutor.model.Model;
import seedu.tutor.model.person.Person;
import seedu.tutor.model.relation.Relation;


/**
 * Add or delete a relation between two person in the list.
 * Bidirectional, Relation object is added or deleted on both Person object.
 */
public class RelateCommand extends Command {

    /**
     * enum for types of RelateCommand
     */
    public enum RelateCommandType {
        ADD, DELETE
    }

    public static final String COMMAND_WORD = "relate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add or delete a relation between two person.\n"
            + "Parameters: "
            + "[" + PREFIX_RELATE_ADD + "RELATION] "
            + "or "
            + "[" + PREFIX_RELATE_DELETE + "RELATION]\n"
            + "RELATION format: [Person1/Person2/Relation Name1/Relation Name2]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_RELATE_ADD + "Linq/Keiran/teammate/teammate ";

    public static final String MESSAGE_RELATE_SUCCESS = "Updated relation to Person: %1$s";

    private final RelateCommandType type;
    private final Relation relation;
    private final String name1;
    private final String name2;

    /**
     * Return a Command object that executes two sub-RelateCommand.
     * @param relation The relation object that represent the relation between two contact.
     * @param type The type of RelateCommand.
     */
    public RelateCommand(Relation relation, RelateCommandType type) {
        requireNonNull(relation);
        this.type = type;
        this.relation = relation;
        String[] args = relation.relationName.split("/");
        this.name1 = args[0];
        this.name2 = args[1];
    }

    /**
     * A factory for the creation of subtypes of RelateCommand.
     * @param type Type of {@code RelationCommand} that is intended by the user.
     * @param relation The relation object that represent the relation between two contact.
     * @return Subtype of RelateCommand.
     */
    public static RelateCommand create(Index index, RelateCommandType type, Relation relation) {

        requireNonNull(index);
        requireNonNull(type);
        requireNonNull(relation);

        switch (type) {

        case ADD -> {
            return new RelateAddCommand(index, relation);
        }

        case DELETE -> {
            return new RelateDeleteCommand(index, relation);
        }

        default -> {
            // should not reach here
            return null;
        }

        }
    }

    /**
     * Returns the index of a person with the name.
     * @param name The name of the person.
     * @param model
     * @return The index in the form of Index object.
     */
    private Index getIndex(String name, Model model) {
        ObservableList<Person> persons = model.getTutorMap().getPersonList();
        int index = -1;

        // I'm not very sure if this always returns the correct index
        for (int i = 0; i < persons.size(); i++) {
            Person currentPerson = persons.get(i);
            if (currentPerson.getName().toString().equals(name)) {
                index = i;
            }
        }

        if (index != -1) {
            return Index.fromZeroBased(index);
        } else {
            return null;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Index index1 = getIndex(this.name1, model);
        Index index2 = getIndex(this.name2, model);

        try {
            requireNonNull(index1);
            requireNonNull(index2);

        } catch (NullPointerException e) {
            throw new CommandException(Messages.PERSONS_DOES_NOT_EXIST);
        }

        RelateCommand command1 = create(index1, this.type, this.relation);
        RelateCommand command2 = create(index2, this.type, this.relation);

        requireNonNull(command1);
        requireNonNull(command2);
        CommandResult result1 = command1.execute(model);
        CommandResult result2 = command2.execute(model);

        return CommandResult.merge(result1, result2);
    }
}
