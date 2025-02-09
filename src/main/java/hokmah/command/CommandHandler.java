package hokmah.command;

import static hokmah.Hokmah.DATE_TIME_FORMAT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import hokmah.data.SaveHandler;
import hokmah.exception.HokmahException;
import hokmah.task.Deadline;
import hokmah.task.Event;
import hokmah.task.Task;
import hokmah.task.TaskList;
import hokmah.task.ToDo;

/**
 * Executes concrete operations based on parsed commands.
 * Contains business logic for task manipulation and system operations.
 */
public class CommandHandler {
    private TaskList tasks;
    private SaveHandler storage;
    private MessageHandler messageHandler;

    /**
     * Initializes command handler with dependencies.
     *
     * @param tasks          Task collection to operate on
     * @param storage        Persistent storage handler
     * @param messageHandler User interface handler
     */
    public CommandHandler(TaskList tasks, SaveHandler storage, MessageHandler messageHandler) {
        this.tasks = tasks;
        this.storage = storage;
        this.messageHandler = messageHandler;
    }

    /**
     * Returns a formatted list of all tasks.
     *
     * @return String containing numbered list of tasks
     */
    protected String showList() {
        StringBuilder message = new StringBuilder("You have these tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTaskArrayList().get(i);
            assert task != null : "Task list contains null entries";
            if (task != null) {
                message.append((i + 1)).append(".").append(task).append("\n");
            }
        }
        return message.toString();
    }

    /**
     * Retrieves task by ID.
     *
     * @param id Task index (1-based)
     * @return Requested Task object
     * @throws HokmahException For invalid task IDs
     */
    protected Task getTask(int id) throws HokmahException {
        int index = id - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new HokmahException(HokmahException.ExceptionType.TASK_NOT_FOUND);
        }

        assert index >= 0 && index < tasks.size(): "Task index out of bounds";

        return tasks.getTaskArrayList().get(index);
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param id 1-based index of the task to mark
     * @return Confirmation message with marked task details
     */
    protected String markTask(int id) {
        try {
            Task task = getTask(id);
            task.markDone();

            return messageHandler.getMarkTaskMessage(task);
        } catch (HokmahException e) {
            return e.getMessage();
        }
    }

    /**
     * Removes the completion status from the specified task.
     *
     * @param id 1-based index of the task to unmark
     * @return Confirmation message with unmarked task details
     */
    protected String unmarkTask(int id) {
        try {
            Task task = getTask(id);
            task.unmarkDone();

            return messageHandler.getUnmarkTaskMessage(task);
        } catch (HokmahException e) {
            return e.getMessage();
        }
    }

    /**
     * Deletes a task from the list and persists changes.
     *
     * @param id 1-based index of the task to delete
     * @return Confirmation message with deleted task details
     */
    protected String deleteTask(int id) {
        try {
            Task task = getTask(id);
            tasks.delete(task);

            storage.saveToFile(tasks.getTaskArrayList());
            return messageHandler.getDeleteTaskMessage(task);
        } catch (HokmahException e) {
            return e.getMessage();
        }

    }

    /**
     * Adds a new Todo task to the list.
     *
     * @param inputArray Parsed command components
     * @return Confirmation message with new task details
     * @throws HokmahException If task name is missing
     */
    protected String addTodo(String[] inputArray) throws HokmahException {
        assert inputArray != null : "Null command input";

        if (inputArray.length == 1) {
            return new HokmahException(HokmahException.ExceptionType.NO_NAME).getMessage();
        }
        String taskName = inputArray[1];
        ToDo newTodo = new ToDo(taskName);

        tasks.add(newTodo);
        storage.saveToFile(tasks.getTaskArrayList());


        return messageHandler.getAddTaskMessage(newTodo, tasks.size());
    }

    /**
     * Adds a new Deadline task to the list and saves the updated list.
     *
     * @param inputArray The parsed command input
     * @return Confirmation message with new task details
     * @throws HokmahException If the format is invalid or datetime parsing fails
     */
    protected String addDeadline(String[] inputArray) throws HokmahException {
        assert inputArray != null : "Null command input";

        if (inputArray.length == 1) {
            return new HokmahException(HokmahException.ExceptionType.NO_NAME).getMessage();
        }
        assert inputArray.length > 1;

        String[] taskDetails = inputArray[1].split("/by");
        if (taskDetails.length == 1) {
            return new HokmahException(HokmahException.ExceptionType.DEADLINE_NO_TIME_END).getMessage();
        }
        assert taskDetails.length > 1;

        try {
            String taskName = taskDetails[0].trim();
            String deadline = taskDetails[1].trim();

            LocalDateTime deadlineDate = LocalDateTime.parse(deadline,
                    DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
            Deadline newDeadline = new Deadline(taskName, deadlineDate);
            tasks.add(newDeadline);

            storage.saveToFile(tasks.getTaskArrayList());
            return messageHandler.getAddTaskMessage(newDeadline, tasks.size());

        } catch (DateTimeParseException e) {
            return new HokmahException(HokmahException.ExceptionType.DEADLINE_NO_TIME_END).getMessage();
        }
    }

    /**
     * Adds a new Event task to the list and saves the updated list.
     *
     * @param inputArray The parsed command input
     * @return Confirmation message with new task details
     * @throws HokmahException If the format is invalid or datetime parsing fails
     */
    protected String addEvent(String[] inputArray) throws HokmahException {
        assert inputArray != null : "Null command input";

        if (inputArray.length == 1) {
            return new HokmahException(HokmahException.ExceptionType.NO_NAME).getMessage();
        }

        String[] taskDetails = inputArray[1].split("/from");
        if (taskDetails.length == 1) {
            return new HokmahException(HokmahException.ExceptionType.EVENT_NO_TIME_START).getMessage();
        }

        String taskName = taskDetails[0].trim();
        String[] eventTimeDetails = taskDetails[1].split("/to");
        if (eventTimeDetails.length == 1) {
            return new HokmahException(HokmahException.ExceptionType.EVENT_NO_TIME_END).getMessage();
        }

        try {
            String eventStartTime = eventTimeDetails[0].trim();
            LocalDateTime eventStartTimeDate = LocalDateTime.parse(eventStartTime,
                    DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

            String eventEndTime = eventTimeDetails[1].trim();
            LocalDateTime eventEndTimeDate = LocalDateTime.parse(eventEndTime,
                    DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

            Event newEvent = new Event(taskName, eventStartTimeDate, eventEndTimeDate);

            tasks.add(newEvent);
            storage.saveToFile(tasks.getTaskArrayList());
            return messageHandler.getAddTaskMessage(newEvent, tasks.size());

        } catch (DateTimeParseException e) {
            return new HokmahException(HokmahException.ExceptionType.EVENT_NO_TIME_START).getMessage();
        }
    }

    /**
     * Returns a message for unsupported commands.
     */
    protected String unsupportedCommand() {
        return messageHandler.getUnsupportedCommandMessage();
    }

    /**
     * Finds tasks containing the specified keyword in their description
     *
     * @param inputArray The parsed command input
     */
    protected String findCommand(String[] inputArray) throws HokmahException {
        assert inputArray != null : "Null command input";

        if (inputArray.length == 1) {
            throw new HokmahException(HokmahException.ExceptionType.NO_NAME);
        }

        //Processing
        String keyword = inputArray[1];
        ArrayList<Task> matches = new ArrayList<>();

        for (Task task : tasks.getTaskArrayList()) {
            if (task == null) {
                continue;
            }

            if (task.getName().contains(keyword)) {
                matches.add(task);
            }
        }

        return messageHandler.getFindMessage(matches, keyword);

    }

    /**
     * Displays help information with available commands and formats.
     */
    protected String help() {
        return messageHandler.getHelpMessage();
    }

    /**
     * Shows tasks occurring on a specific date and saves the updated list.
     *
     * @param inputArray The parsed command input
     * @return a message indicating the tasks occurring on the specified date
     * @throws HokmahException If the date format is invalid
     */
    protected String upcomingTasksOn(String[] inputArray) throws HokmahException {
        assert inputArray != null : "Null command input";

        if (inputArray.length == 1) {
            return new HokmahException(HokmahException.ExceptionType.NO_UPCOMING_ON_DATE).toString();
        }

        String[] taskDetails = inputArray[1].split("/on");
        if (taskDetails.length < 2) {
            return new HokmahException(HokmahException.ExceptionType.NO_UPCOMING_ON_DATE).getMessage();
        }


        try {
            String date = taskDetails[1].trim();
            LocalDateTime dateToCheck = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
            assert dateToCheck != null : "Failed to parse date";

            ArrayList<Task> upcomingTasks = new ArrayList<>();


            for (Task task : tasks.getTaskArrayList()) {
                if (task.getTimeEnd() != null && task.getTimeEnd().equals(dateToCheck)) {
                    upcomingTasks.add(task);

                }
            }

            return messageHandler.getUpcomingTasksOnMessage(upcomingTasks, dateToCheck);


        } catch (DateTimeParseException e) {
            return new HokmahException(HokmahException.ExceptionType.NO_UPCOMING_ON_DATE).toString();
        }
    }

    /**
     * Initiates application shutdown sequence.
     */
    protected String exit() {
        return messageHandler.getExitMessage();
    }

}
