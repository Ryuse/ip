package hokmah.command;

import static hokmah.Hokmah.DATE_TIME_FORMAT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import hokmah.task.Task;


/**
 * Manages generation of formatted user messages.
 * Handles all UI text formatting and presentation logic.
 */
public class MessageHandler {

    /**
     * Constructs a UI handler with default settings.
     * Initializes necessary components for user interaction.
     */
    public MessageHandler() {
    }

    /**
     * Gives a decorative separator line.
     *
     * @return
     */
    public String getMessageSeparatorLine() {
        return "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+";
    }

    /**
     * Generates task marking confirmation message.
     *
     * @param task The task that was marked as done
     */
    public String getMarkTaskMessage(Task task) {
        String message = "Bleh! I've masked this task as done!\n"
                + task
                + "\nAre you happy?";

        return message;
    }

    /**
     * Generates task unmarking confirmation message.
     *
     * @param task The task that was unmarked
     */
    public String getUnmarkTaskMessage(Task task) {
        String message = "So you have not done this task yet?"
                + "\n" + task
                + "\n" + "That's sad. I've masked it as such.";

        return message;
    }

    /**
     * Generates confirmation message for deleted tasks.
     *
     * @param task The task that was removed
     * @return Formatted deletion confirmation message
     */
    public String getDeleteTaskMessage(Task task) {
        String message = "I've removed this task:\n"
                + task
                + "What else do you want?";

        return message;
    }

    /**
     * Generates task addition confirmation message.
     *
     * @param task      The newly added task
     * @param taskCount The new total number of tasks
     * @return message
     */
    public String getAddTaskMessage(Task task, int taskCount) {
        String message = "Ok sure, I've added this task:"
                + "\n" + task
                + "\n" + "Now you have " + taskCount + " tasks in the list. What else do you want?";

        return message;
    }

    /**
     * Generates message for unrecognized commands.
     * Provides guidance on help command usage.
     *
     * @return
     */
    public String getUnsupportedCommandMessage() {
        String message = """
                That's not right
                Just what are you trying to do? Can you ask something else?
                If you don't know what to ask you can use the 'help' command""";

        return message;
    }

    /**
     * Generates help information with available commands and formats.
     *
     * @return
     */
    public String getHelpMessage() {
        String commandListString = "You seriously need help? Fine. Here is what I can do:\n"
                + "list\n\t(Shows all the tasks in the list)\n"
                + "todo [name]\n\t(Adds a todo task to the task list)\n"
                + "deadline [name] /by [" + DATE_TIME_FORMAT + "]\n\t(Adds a deadline task to the task list)\n"
                + "event [name] /from [" + DATE_TIME_FORMAT + "] /to [" + DATE_TIME_FORMAT
                + "]\n\t(Adds a event task to the task list)\n"
                + "mark [task number]\n\t (Marks the task at [task number] in the task list as completed)\n"
                + "unmark [task number]\n\t (Marks the task at [task number] in the task list as incomplete)\n"
                + "delete [task number]\n\t (Deletes the task at [task number] in the task list)\n"
                + "upcoming /on [" + DATE_TIME_FORMAT
                + "]\n\t (Shows all the tasks that are happening on the given date)\n"
                + "find [keyword]\n\t (Finds tasks containing the specified keyword)\n"
                + "bye\n\t(Only if you want to leave. It's not like I wanted you to be here.)";

        return commandListString;

    }

    /**
     * Generates search results message.
     *
     * @param matches List of matching tasks
     * @param keyword Search term used
     * @return Formatted results message or 'no matches' message
     */
    public String getFindMessage(ArrayList<Task> matches, String keyword) {
        //Printing
        if (matches.isEmpty()) {
            return "No tasks found containing: " + keyword;
        } else {
            StringBuilder message = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matches.size(); i++) {
                message.append((i + 1)).append(".").append(matches.get(i));
            }
            return message.toString();
        }
    }

    /**
     * Generates upcoming tasks message for specified date.
     *
     * @param upcomingTasks List of tasks occurring on target date
     * @param dateToCheck   Date being checked for upcoming tasks
     * @return Formatted list of upcoming tasks or empty state message
     */
    public String getUpcomingTasksOnMessage(ArrayList<Task> upcomingTasks, LocalDateTime dateToCheck) {
        StringBuilder message = new StringBuilder();


        message.append("Upcoming tasks on ")
                .append(dateToCheck.format(DateTimeFormatter.ofPattern(Task.DATE_STRING_OUTPUT_FORMAT)))
                .append(":");

        for (Task task : upcomingTasks) {
            message.append(task);
        }

        if (upcomingTasks.isEmpty()) {
            message.append("You have no upcoming tasks dummy.");
        } else {
            message.append("You have ")
                    .append(upcomingTasks.size())
                    .append(" upcoming tasks. It's coming soon. Like your doom.");
        }

        return message.toString();
    }

    /**
     * Generates application welcome message and logo.
     *
     * @return
     */
    public String getWelcomeMessage() {
        String logo = ",--.  ,--.         ,--.                         ,--.     \n"
                + "|  '--'  |  ,---.  |  |,-.  ,--,--,--.  ,--,--. |  ,---. \n"
                + "|  .--.  | | .-. | |     /  |        | ' ,-.  | |  .-.  |\n"
                + "|  |  |  | ' '-' ' |  \\  \\  |  |  |  | \\ '-'  | |  | |  |\n"
                + "`--'  `--'  `---'  `--'`--' `--`--`--'  `--`--' `--' `--' \n";

        String message = "I'm\n"
                + logo
                + "\n"
                + "What do you want?"
                + getMessageSeparatorLine();
        return message;

    }

    /**
     * Generates exit message when closing application.
     *
     * @return
     */
    public String getExitMessage() {
        String message = "Goodbye! I hope you don't come back soon! ヾ(＾ ∇ ＾).";

        return message;
    }


}
