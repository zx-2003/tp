package seedu.tutor.logic.commands;

import seedu.tutor.logic.commands.exceptions.CommandException;
import seedu.tutor.logic.parser.EditCommandParser;
import seedu.tutor.logic.parser.exceptions.ParseException;
import seedu.tutor.model.Model;
import seedu.tutor.model.label.Label;
import seedu.tutor.model.person.Person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class ChangeSubjectCommand extends Command{

    private final Label oldSubject;
    private final Label newSubject;
    private final EditCommandParser parser = new EditCommandParser();

    public ChangeSubjectCommand(String oldSubject, String newSubject) {
        this.oldSubject = new Label(oldSubject);
        this.newSubject = new Label(newSubject);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        List<Person> persons = model.getTutorMap().getPersonList();
        List<Command> editCommands= new ArrayList<>();

        for (int index = 0; index < persons.size(); index++) {
            Person person = persons.get(index);
            Set<Label> subjects = person.getSubjects();
            if (subjects.contains(oldSubject)) {
                subjects.remove(oldSubject);
                subjects.add(newSubject);
                Set<String> args = new HashSet<>();
                for (Label label: subjects) {
                    args.add(label.labelName);
                }
                StringBuilder input = new StringBuilder(" " + (index + 1) + "s/");
                for (String subject: args) {
                    input.append(subject);
                    input.append(" ");
                }
                EditCommand editCommand;
                try {
                    editCommand = parser.parse(input.toString());
                } catch (ParseException pe) {
                    throw new CommandException("Unknown error, by ChangeSubjectCommand");
                }
                editCommands.add(editCommand);
            }
        }

        CommandResult commandResult = null;
        for (Command editCommand: editCommands) {
            CommandResult temp = editCommand.execute(model);
            commandResult = CommandResult.merge(commandResult, temp);
        }
        return commandResult;
    }
}
