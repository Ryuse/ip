package hokmah.command;

import static hokmah.Hokmah.EXIT_COMMANDS;

import java.util.Arrays;

import hokmah.exception.HokmahException;

/**
 * Parses and routes user input to appropriate command processors.
 * Acts as bridge between raw input and command execution.
 */
public class InputHandler {

    private final CommandHandler commandHandler;


    /**
     * Initializes with command handler dependency.
     *
     * @param commandHandler Command processor instance
     */
    public InputHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    /**
     * Processes and executes user input commands.
     *
     * @param input Raw command string from user
     * @return Result message from command execution
     * @throws HokmahException For invalid commands or parameters
     */
    public String process(String input) throws HokmahException {
        assert !input.isBlank() : "Empty input received";
        assert commandHandler != null : "Missing command handler";

        String[] inputArray = input.split(" ", 2);
        String command = inputArray[0];

        boolean isExitCommand = Arrays.asList(EXIT_COMMANDS).contains(command);
        if (isExitCommand) {
            return commandHandler.exit();
        }

        switch (command) {
        case "list":
            return commandHandler.showList();
        case "find":
            return commandHandler.findCommand(inputArray);
        case "mark":
            assert inputArray.length == 2 : "Missing index for " + command;
            int mark_id = Integer.parseInt(inputArray[1]);

            return commandHandler.markTask(mark_id);
        case "unmark":
            assert inputArray.length == 2 : "Missing index for " + command;
            int unmark_id = Integer.parseInt(inputArray[1]);

            return commandHandler.unmarkTask(unmark_id);
        case "delete":
            assert inputArray.length == 2 : "Missing index for " + command;
            int delete_id = Integer.parseInt(inputArray[1]);

            return commandHandler.deleteTask(delete_id);
        case "todo":
            return commandHandler.addTodo(inputArray);
        case "deadline":
            return commandHandler.addDeadline(inputArray);
        case "event":
            return commandHandler.addEvent(inputArray);
        case "help":
            return commandHandler.help();
        case "upcoming":
            return commandHandler.upcomingTasksOn(inputArray);
        default:
            return commandHandler.unsupportedCommand();
        }
    }
}
