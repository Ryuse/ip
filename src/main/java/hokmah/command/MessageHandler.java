package hokmah.command;

import static hokmah.Hokmah.DATE_TIME_FORMAT;
import static hokmah.Hokmah.LOGO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import hokmah.task.Task;


/**
 * Manages generation of formatted user messages.
 * Handles all UI text formatting and presentation logic.
 */
public class MessageHandler {



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
        String message = String.format("""
                Bleh! I've masked this task as done!
                %s
                Are you happy?""",
                task);

        return message;
    }

    /**
     * Generates task unmarking confirmation message.
     *
     * @param task The task that was unmarked
     */
    public String getUnmarkTaskMessage(Task task) {
        String message = String.format("""
                So you have not done this task yet?
                %s
                That's sad. I've masked it as such.""",
                task);

        return message;
    }

    /**
     * Generates confirmation message for deleted tasks.
     *
     * @param task The task that was removed
     * @return Formatted deletion confirmation message
     */
    public String getDeleteTaskMessage(Task task) {
        String message = String.format("""
                Ok sure, I've removed this task
                %s
                What else do you want?""",
                task);

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
        String message = String.format("""
                Ok sure, I've added this task:
                %s
                Now you have %s tasks in the list. What else do you want?""",
                task,
                taskCount);

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

                Just what are you trying to do?
                Can you ask something else?

                If you don't know what to ask you can use the 'help' command""";

        return message;
    }

    /**
     * Generates help information with available commands and formats.
     *
     * @return
     */
    public String getHelpMessage() {
        StringBuilder commandList = new StringBuilder();
        commandList.append("You seriously need help? Fine. Here is what I can do:\n")
                .append("list\n")
                .append("\t(Shows all the tasks in the list)\n")

                .append("todo [name]\n")
                .append("\t(Adds a todo task to the task list)\n")

                .append("deadline [name] /by [" + DATE_TIME_FORMAT + "]\n")
                .append("\t(Adds a deadline task to the task list)\n")

                .append("event [name] /from [" + DATE_TIME_FORMAT + "] /to [" + DATE_TIME_FORMAT + "]\n")
                .append("\t(Adds a event task to the task list)\n")

                .append("mark [task number]\n")
                .append("\t(Marks the task at [task number] in the task list as completed)\n")

                .append("unmark [task number]\n")
                .append("\t(Marks the task at [task number] in the task list as incomplete)\n")

                .append("delete [task number]\n")
                .append("\t(Deletes the task at [task number] in the task list)\n")

                .append("upcoming /on [" + DATE_TIME_FORMAT + "]\n")
                .append("\t(Shows all the tasks that are happening on the given date)\n")

                .append("find [keyword]\n")
                .append("\t(Finds tasks containing the specified keyword)\n")

                .append("bye\n")
                .append("\t(Only if you want to leave. It's not like I wanted you to be here.)");

        return commandList.toString();

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
        }

        StringBuilder message = new StringBuilder("Here are the matching tasks in your list:\n");

        String matchesString = matches.stream()
                .map(task -> (matches.indexOf(task) + 1) + "." + task)
                .collect(Collectors.joining("\n"));

        message.append(matchesString);
        return message.toString();

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

        String formattedDate = dateToCheck.format(DateTimeFormatter.ofPattern(Task.DATE_STRING_OUTPUT_FORMAT));
        message.append("Upcoming tasks on ")
                .append(formattedDate)
                .append(":");


        if (upcomingTasks.isEmpty()) {
            message.append("You have no upcoming tasks dummy.");
        } else {
            String upcomingTasksString = upcomingTasks.stream()
                                        .map(Task::toString)
                                        .collect(Collectors.joining("\n"));

            message.append(upcomingTasksString)
                    .append("\n\n")
                    .append("You have ")
                    .append(upcomingTasks.size())
                    .append("upcoming tasks. It's coming soon. Like your doom.");
        }

        return message.toString();
    }

    /**
     * Generates application welcome message and logo.
     *
     * @return
     */
    public String getWelcomeMessage() {
        String message = String.format("""
                I'm
                %s
                What do you want?
                %s
                """, LOGO, getMessageSeparatorLine());

        return message;

    }

    /**
     * Generates exit message when closing application.
     *
     * @return
     */
    public String getExitMessage() {
        String message = """
                Goodbye!

                I hope you don't come back soon!

                ヾ(＾ ∇ ＾).""";

        return message;
    }


}
