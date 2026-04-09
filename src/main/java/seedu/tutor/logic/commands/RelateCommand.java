package seedu.tutor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tutor.logic.commands.RelateDeleteCommand.MESSAGE_INVALID_RELATION_TO_DELETE;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_RELATE_ADD;
import static seedu.tutor.logic.parser.CliSyntax.PREFIX_RELATE_DELETE;

import java.util.ArrayList;
import java.util.HashMap;
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
 * Adds and/or deletes multiple relation between two person in the list.
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds or deletes a relation between two persons.\n"
            + "Parameters: "
            + "[" + PREFIX_RELATE_ADD + "RELATION]... "
            + "[" + PREFIX_RELATE_DELETE + "RELATION]...\n"

            + "Example: " + COMMAND_WORD + " "
            + PREFIX_RELATE_ADD + "Linq/Keiran/teammate/teammate\n"

            + "Notes: \n"
            + "⚠ " + PREFIX_RELATE_ADD + ", " + PREFIX_RELATE_DELETE + " uses backslash (\\), not forward slash (/).\n"
            + "- RELATION format: [Person1/Person2/Relation-Name1/Relation-Name2]\n"
            + "- " + PREFIX_RELATE_ADD + ", " + PREFIX_RELATE_DELETE
            + "are optional, but at least one must be present.\n"
            + "- " + PREFIX_RELATE_ADD + ", " + PREFIX_RELATE_DELETE + " can be used once or multiple times.";

    private final Set<Relation> relationsToAdd;
    private final Set<Relation> relationsToDelete;
    private final HashMap<String, Index> nameToIndexCache = new HashMap<>();

    /**
     * Returns a command that add and/or delete multiple relation.
     * @param relationsToAdd A set of relation to be added.
     * @param relationsToDelete A set of relation to be deleted.
     */
    public RelateCommand(Set<Relation> relationsToAdd, Set<Relation> relationsToDelete) {
        requireNonNull(relationsToAdd);
        requireNonNull(relationsToDelete);
        for (Relation relation: relationsToAdd) {
            if (relationsToDelete.contains(relation)) {
                relationsToDelete.remove(relation);
                relationsToAdd.remove(relation);
            }
        }
        this.relationsToAdd = relationsToAdd;
        this.relationsToDelete = relationsToDelete;
    }

    /**
     * Returns a concrete type of Command object that add or delete a relation.
     * @param type Type of {@code RelationCommand} that is intended by the user.
     * @param relation The relation object that represent the relation between two contact.
     * @return The concrete command for adding or deleting relation.
     */
    private static Command createCommand(Index index, RelateCommandType type, Relation relation) {

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
     * @param model The TutorMap model.
     * @return The index in the form of Index object.
     */
    private Index getIndex(String name, Model model) {

        if (nameToIndexCache.containsKey(name)) {
            return this.nameToIndexCache.get(name);
        }

        ObservableList<Person> persons = model.getTutorMap().getPersonList();
        int personIndex = -1;

        // I'm not very sure if this always returns the correct index
        for (int i = 0; i < persons.size(); i++) {
            Person currentPerson = persons.get(i);
            if (currentPerson.getName().toString().equals(name)) {
                personIndex = i;
            }
        }

        if (personIndex != -1) {
            Index index = Index.fromZeroBased(personIndex);
            this.nameToIndexCache.put(name, index);
            return index;
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
                throw new CommandException(Messages.PERSONS_DOES_NOT_EXIST + ": " + prefix + relation.relationName);
            }

            // Checks the presence of relations
            boolean isRelationExist1 = relationExist(index1, relation, model);
            boolean isRelationExist2 = relationExist(index2, relation, model);
            if (type == RelateCommandType.ADD && (isRelationExist1 || isRelationExist2)) {
                throw new CommandException(Messages.RELATIONS_ALREADY_EXIST + ": "
                        + PREFIX_RELATE_ADD + relation.relationName);
            } else if (type == RelateCommandType.DELETE && !(isRelationExist1 && isRelationExist2)) {
                throw new CommandException(MESSAGE_INVALID_RELATION_TO_DELETE
                        + ": " + PREFIX_RELATE_DELETE + relation.relationName);
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

        CommandResult results = null;

        for (Command command : commands) {
            CommandResult result = command.execute(model);
            results = CommandResult.merge(results, result);
        }

        if (!commands.isEmpty()) {
            return results;
        } else {
            return new CommandResult("No relation added or deleted.");
        }
    }

    /**
     * Checks if a relation already exist.
     * @param relation The Relation object to check.
     * @param model The model.
     * @return True if the Relation exists, else false.
     */
    private boolean relationExist(Index index, Relation relation, Model model) {
        Set<Relation> relations = model.getTutorMap().getPersonList().get(index.getZeroBased()).getRelations();
        return relations.contains(relation);
    }
}
