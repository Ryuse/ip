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
        assert task != null : "Null task in Mark message";

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
        assert task != null : "Null task in Unmark message";

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
        assert task != null : "Null task in Delete message";

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
        assert task != null : "Null task in AddTask message";

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
        String message = "You seriously need help? Fine. I'll show you what I can do.";

        return message;
    }

    /**
     * Generates search results message.
     *
     * @param matches List of matching tasks
     * @param keyword Search term used
     * @return Formatted results message or 'no matches' message
     */
    public String getFindMessage(ArrayList<Task> matches, String keyword) {
        assert keyword != null : "Null search keyword";
        assert matches != null : "Null matches list";

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
        assert dateToCheck != null : "Null date in upcoming tasks";

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
