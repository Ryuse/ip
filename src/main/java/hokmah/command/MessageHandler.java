package hokmah.command;


import static hokmah.Hokmah.DATETIME_OUTPUT_FORMAT;

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
    public String[] getMarkTaskMessage(Task task) {
        assert task != null : "Null task in Mark message";

        String message = String.format("""
                Bleh! I've masked this task as done!
                %s
                Are you happy?""",
                task);

        String[] messageLines = message.split("\n");

        return messageLines;
    }

    /**
     * Generates task unmarking confirmation message.
     *
     * @param task The task that was unmarked
     */
    public String[] getUnmarkTaskMessage(Task task) {
        assert task != null : "Null task in Unmark message";

        String message = String.format("""
                So you have not done this task yet?
                %s
                That's sad. I've masked it as such.""",
                task);

        String[] messageLines = message.split("\n");

        return messageLines;
    }

    /**
     * Generates confirmation message for deleted tasks.
     *
     * @param task The task that was removed
     * @return Formatted deletion confirmation message
     */
    public String[] getDeleteTaskMessage(Task task) {
        assert task != null : "Null task in Delete message";

        String message = String.format("""
                Ok sure, I've removed this task
                %s
                What else do you want?""",
                task);

        String[] messageLines = message.split("\n");

        return messageLines;
    }

    /**
     * Generates task addition confirmation message.
     *
     * @param task      The newly added task
     * @param taskCount The new total number of tasks
     * @return message
     */
    public String[] getAddTaskMessage(Task task, int taskCount) {
        assert task != null : "Null task in AddTask message";

        String message = String.format("""
                Ok sure, I've added this task:
                %s
                Now you have %s tasks in the list. What else do you want?""",
                task,
                taskCount);

        String[] messageLines = message.split("\n");

        return messageLines;
    }

    /**
     * Generates message for unrecognized commands.
     * Provides guidance on help command usage.
     *
     * @return
     */
    public String[] getUnsupportedCommandMessage() {
        String message = """
                Ooookay? Just what are you trying to do?
                Can you ask something else?
                If you don't know what to ask you can use the 'help' command""";

        String[] messageLines = message.split("\n");

        return messageLines;
    }

    /**
     * Generates help information with available commands and formats.
     *
     * @return
     */
    public String[] getHelpMessage() {
        String message = """
                You seriously need help? Fine.
                I'll show you what I can do..""";

        String[] messageLines = message.split("\n");

        return messageLines;
    }

    /**
     * Generates search results message.
     *
     * @param matches List of matching tasks
     * @param keyword Search term used
     * @return Formatted results message or 'no matches' message
     */
    public String[] getFindMessage(ArrayList<Task> matches, String keyword) {
        assert keyword != null : "Null search keyword";
        assert matches != null : "Null matches list";

        if (matches.isEmpty()) {
            return new String[]{"No tasks found containing: " + keyword};
        }

        StringBuilder message = new StringBuilder("Here are the matching tasks in your list:\n\n");

        String matchesString = matches.stream()
                .map(task -> (matches.indexOf(task) + 1) + "." + task)
                .collect(Collectors.joining("\n"));

        message.append(matchesString);

        String[] messageLines = new String[]{message.toString()};

        return messageLines;

    }

    /**
     * Generates upcoming tasks message for specified date.
     *
     * @param upcomingTasks List of tasks occurring on target date
     * @param dateToCheck   Date being checked for upcoming tasks
     * @return Formatted list of upcoming tasks or empty state message
     */
    public String[] getUpcomingTasksOnMessage(ArrayList<Task> upcomingTasks, LocalDateTime dateToCheck) {
        assert dateToCheck != null : "Null date in upcoming tasks";

        StringBuilder message = new StringBuilder();

        String formattedDate = dateToCheck.format(DateTimeFormatter.ofPattern(DATETIME_OUTPUT_FORMAT));



        if (upcomingTasks.isEmpty()) {
            message.append(String.format("You have no upcoming tasks on %s, dummy.", formattedDate));
        } else {
            message.append("Your upcoming tasks on ")
                    .append(formattedDate)
                    .append(" is:\n\n");

            String upcomingTasksString = upcomingTasks.stream()
                                        .map(Task::toString)
                                        .collect(Collectors.joining("\n"));

            message.append(upcomingTasksString)
                    .append("\n\n")
                    .append("You have ")
                    .append(upcomingTasks.size())
                    .append(" upcoming task(s). It's coming soon. Like your doom.");
        }

        String[] messageLines = new String[]{message.toString()};

        return messageLines;
    }

    /**
     * Generates application welcome message and logo.
     *
     * @return
     */
    public String[] getWelcomeMessage() {
        String message = """
                I'm Hokmah
                What do you want?
                """;

        String[] messageLines = message.split("\n");
        return messageLines;
    }

    /**
     * Generates exit message when closing application.
     *
     * @return
     */
    public String[] getExitMessage() {
        String message = """
                Goodbye!
                I hope you don't come back soon!
                ヾ(＾ ∇ ＾).""";

        String[] messageLines = message.split("\n");

        return messageLines;
    }


}
