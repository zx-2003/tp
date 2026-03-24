package seedu.tutor.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Combines two CommandException object.
     * Only one of the input should be null.
     * @param e1 CommandException object 1.
     * @param e2 CommandException object 2.
     * @return combined CommandException object.
     */
    public static CommandException merge(CommandException e1, CommandException e2) {
        if (e1 == null) {
            return e2;
        } else if (e2 == null) {
            return e1;
        }

        String m1 = e1.getMessage();
        String m2 = e2.getMessage();
        if (m1.contains(m2)) {
            return e1;
        } else if (m2.contains(m1)) {
            return e2;
        } else {
            String combinedMessage = m1 + "\n" + m2;
            return new CommandException(combinedMessage);
        }
    }
}
