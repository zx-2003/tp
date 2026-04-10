package seedu.tutor.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.tutor.commons.util.ToStringBuilder;
import seedu.tutor.logic.Messages;
import seedu.tutor.model.Model;
import seedu.tutor.model.person.Person;

/**
 * Finds and lists all persons in address book whose selected field contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons whose selected field contains any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Can be used to find people by name, phone number, email, address, subject, relation, or tag. \n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "(Finding by name) Example: " + COMMAND_WORD + " n/alice bob charlie\n"
            + "(Finding by relation) Example: " + COMMAND_WORD + " r/parent\n"
            + "(Finding by subject) Example: " + COMMAND_WORD + " s/math chinese\n"
            + "(Finding by address) Example: " + COMMAND_WORD + " a/Street 21 \n"
            + "(Finding by email) Example: " + COMMAND_WORD + " e/fakemail.com \n"
            + "(Finding by phone number) Example: " + COMMAND_WORD + " p/999 \n"
            + "(Finding by tag) Example: " + COMMAND_WORD + " t/paid unpaid";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
