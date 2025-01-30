public class InputHandler {

    private CommandHandler commandHandler;

    public InputHandler(CommandHandler commandHandler){
        this.commandHandler = commandHandler;
    }

    public void process(String input) throws HokmahException {
        String[] inputArray = input.split(" ", 2);
        String command = inputArray[0];
        switch (command){
            case "bye":
                commandHandler.exit();
                break;
            case "list":
                commandHandler.showList();
                break;
            case "mark":
                int mark_id = Integer.parseInt(inputArray[1]);
                commandHandler.markTask(mark_id);
                break;
            case "unmark":
                int unmark_id = Integer.parseInt(inputArray[1]);
                commandHandler.unmarkTask(unmark_id);
                break;
            case "delete":
                int delete_id = Integer.parseInt(inputArray[1]);
                commandHandler.deleteTask(delete_id);
                break;
            case "todo":
                commandHandler.addTodo(inputArray);
                break;
            case "deadline":
                commandHandler.addDeadline(inputArray);
                break;
            case "event":
                commandHandler.addEvent(inputArray);
                break;
            case "help":
                commandHandler.help();
                break;
            case "upcoming":
                commandHandler.upcomingTasksOn(inputArray);
                break;
            default:
                commandHandler.unsupportedCommand();
                break;
        }
    }
}
