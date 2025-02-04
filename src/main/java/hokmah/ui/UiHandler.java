package hokmah.ui;


import hokmah.task.Task;
import static hokmah.Hokmah.DATE_TIME_FORMAT;

/**
 * Manages user interface interactions and formatted output.
 * Handles display of messages, lists, and decorative elements.
 */
public class UiHandler {

    /**
     * Constructs a UI handler with default settings.
     * Initializes necessary components for user interaction.
     */
    public UiHandler() {
    }

    /**
     * Displays decorative separator line.
     */
    public void showLine() {
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
    }

    /**
     * Displays task marking confirmation message.
     *
     * @param task The task that was marked as done
     */
    public void showMarkTaskMessage(Task task) {
        System.out.println("Bleh! I've masked this task as done!");
        System.out.println(task);
        System.out.println("Are you happy?");
    }

    /**
     * Displays task unmarking confirmation message.
     *
     * @param task The task that was unmarked
     */
    public void showUnmarkTaskMessage(Task task) {
        System.out.println("So you have not done this task yet?");
        System.out.println(task);
        System.out.println("That's sad. I've masked it as such");
    }

    /**
     * Displays task addition confirmation message.
     *
     * @param task The newly added task
     * @param taskCount The new total number of tasks
     */
    public void showAddTaskMessage(Task task, int taskCount) {
        System.out.println("Ok sure, I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " tasks in the list. What else do you want?");
    }

    /**
     * Displays message for unrecognized commands.
     * Provides guidance on help command usage.
     */
    public void showUnsupportedCommandMessage() {
        System.out.println("""
                That's not right
                Just what are you trying to do? Can you ask something else?
                If you don't know what to ask you can use the 'help' command""");
    }

    /**
     * Displays help information with available commands and formats.
     */
    public void showHelpMessage() {
        System.out.println("You seriously need help? Fine. Here is what I can do:");
        String taskList = "list\n\t(Shows all the tasks in the list)\n" +
                "todo [name]\n\t(Adds a todo task to the task list)\n" +
                "deadline [name] /by [" + DATE_TIME_FORMAT + "]\n\t(Adds a deadline task to the task list)\n" +
                "event [name] /from [" + DATE_TIME_FORMAT + "] /to [" + DATE_TIME_FORMAT + "]\n\t(Adds a event task to the task list)\n" +
                "mark [task number]\n\t (Marks the task at [task number] in the task list as completed)\n" +
                "unmark [task number]\n\t (Marks the task at [task number] in the task list as incomplete)\n" +
                "delete [task number]\n\t (Deletes the task at [task number] in the task list)\n" +
                "upcoming /on [" + DATE_TIME_FORMAT + "]\n\t (Shows all the tasks that are happening on the given date)\n" +
                "find [keyword]\n\t (Finds tasks containing the specified keyword)\n" +
                "bye\n\t(Only if you want to leave. It's not like I wanted you to be here.)";
        System.out.println(taskList);

    }

    /**
     * Displays application welcome message and logo.
     */
    public void showWelcomeMessage() {
        String logo = ",--.  ,--.         ,--.                         ,--.     \n"
                + "|  '--'  |  ,---.  |  |,-.  ,--,--,--.  ,--,--. |  ,---. \n"
                + "|  .--.  | | .-. | |     /  |        | ' ,-.  | |  .-.  |\n"
                + "|  |  |  | ' '-' ' |  \\  \\  |  |  |  | \\ '-'  | |  | |  |\n"
                + "`--'  `--'  `---'  `--'`--' `--`--`--'  `--`--' `--' `--' \n";

        String message = "I'm\n" +
                logo + "\n" +
                "What do you want?";
        System.out.println(message);
        showLine();
    }

    /**
     * Displays exit message when closing application.
     */
    public void showExitMessage() {
        String message = "Goodbye! I hope you come back soon! ヾ(＾ ∇ ＾).";
        System.out.println(message);
    }


}
