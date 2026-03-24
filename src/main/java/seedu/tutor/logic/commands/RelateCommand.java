package seedu.tutor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_RELATE_ADD;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_RELATE_DELETE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.tutor.commons.core.index.Index;
import seedu.tutor.logic.Messages;
import seedu.tutor.logic.commands.exceptions.CommandException;
import seedu.tutor.logic.parser.Prefix;
import seedu.tutor.model.Model;
import seedu.tutor.model.person.Person;
import seedu.tutor.model.relation.Relation;

/**
 * Add and/or delete multiple relation between two person in the list.
 * Bidirectional, Relation object is added or deleted on both Person object.
 * Relations are added first, then delete.
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

    private final Set<Relation> relationsToAdd;
    private final Set<Relation> relationsToDelete;

    /**
     * Return a command that add and/or delete multiple relation.
     * @param relationsToAdd A set of relation to be added.
     * @param relationsToDelete A set of relation to be deleted.
     */
    public RelateCommand(Set<Relation> relationsToAdd, Set<Relation> relationsToDelete) {
        requireNonNull(relationsToAdd);
        requireNonNull(relationsToDelete);
        this.relationsToAdd = relationsToAdd;
        this.relationsToDelete = relationsToDelete;
    }

    /**
     * A factory for the creation of subtypes of RelateCommand.
     * @param type Type of {@code RelationCommand} that is intended by the user.
     * @param relation The relation object that represent the relation between two contact.
     * @return Subtype of RelateCommand.
     */
    public static Command createCommand(Index index, RelateCommandType type, Relation relation) {

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

    private String getName1(Relation relation) {
        String[] args = relation.relationName.split("/");
        return args[0];
    }

    private String getName2(Relation relation) {
        String[] args = relation.relationName.split("/");
        return args[1];
    }

    private Set<Command> createCommands(Set<Relation> relations, RelateCommandType type, Model model)
            throws CommandException {
        Set<Command> commands = new HashSet<>();
        for (Relation relation: relations) {
            Index index1 = getIndex(getName1(relation), model);
            Index index2 = getIndex(getName2(relation), model);

            try {
                requireNonNull(index1);
                requireNonNull(index2);
            } catch (NullPointerException e) {
                Prefix prefix;
                if (type == RelateCommandType.ADD) {
                    prefix = PREFIX_RELATE_ADD;
                } else {
                    prefix = PREFIX_RELATE_DELETE;
                }
                throw new CommandException(Messages.PERSONS_DOES_NOT_EXIST + " By: " + prefix + relation.relationName);
            }

            Command command1 = createCommand(index1, type, relation);
            Command command2 = createCommand(index2, type, relation);

            // Shouldn't be null in all case
            requireNonNull(command1);
            requireNonNull(command2);

            commands.add(command1);
            commands.add(command2);
        }
        return commands;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        Set<Command> addCommands = createCommands(relationsToAdd, RelateCommandType.ADD, model);
        Set<Command> deleteCommands = createCommands(relationsToDelete, RelateCommandType.DELETE, model);
        List<Command> commands = new ArrayList<>(addCommands);
        commands.addAll(deleteCommands);

        CommandException exceptions = null;
        CommandResult results = null;

        for (Command command : commands) {
            try {
                CommandResult result = command.execute(model);
                results = CommandResult.merge(results, result);
            } catch (CommandException ce) {
                exceptions = CommandException.merge(exceptions, ce);
            }
        }

        if (exceptions != null) {
            throw exceptions;
        } else {
            return results;
        }
    }
}
