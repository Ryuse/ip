package hokmah.command;

import static hokmah.Hokmah.DATETIME_INPUT_FORMAT;
import static hokmah.exception.HokmahException.ExceptionType;

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
    private final TaskList tasks;
    private final SaveHandler storage;
    private final MessageHandler messageHandler;

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
     * @return String[] containing numbered list of tasks
     */
    protected String[] showList() {
        if (tasks.size() == 0) {
            return new String[]{"You have no tasks! Don't disturb me!"};
        }


        StringBuilder tasksMessage = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTaskArrayList().get(i);
            assert task != null : "Task list contains null entries";

            tasksMessage.append((i + 1)).append(".").append(task).append("\n");

        }

        String[] messageLines = {"You have these tasks",
                tasksMessage.toString()};

        return messageLines;
    }


    /**
     * Marks the task at the specified index as done.
     *
     * @param inputArray Parsed command components
     * @return Confirmation message with marked task details
     */
    protected String[] markTask(String[] inputArray) throws HokmahException {
        Task task = getTask(inputArray);
        assert task != null : "Task is null";
        task.markDone();

        storage.saveToFile(tasks.getTaskArrayList());
        return messageHandler.getMarkTaskMessage(task);
    }

    /**
     * Removes the completion status from the specified task.
     *
     * @param inputArray Parsed command components
     * @return Confirmation message with unmarked task details
     */
    protected String[] unmarkTask(String[] inputArray) throws HokmahException {
        Task task = getTask(inputArray);
        assert task != null : "Task is null";
        task.unmarkDone();

        storage.saveToFile(tasks.getTaskArrayList());
        return messageHandler.getUnmarkTaskMessage(task);
    }

    /**
     * Deletes a task from the list and persists changes.
     *
     * @param inputArray Parsed command components
     * @return Confirmation message with deleted task details
     */
    protected String[] deleteTask(String[] inputArray) throws HokmahException {
        Task task = getTask(inputArray);
        assert task != null : "Task is null";
        tasks.delete(task);

        storage.saveToFile(tasks.getTaskArrayList());
        return messageHandler.getDeleteTaskMessage(task);

    }

    /**
     * Adds a new Todo task to the list.
     *
     * @param inputArray Parsed command components
     * @return Confirmation message with new task details
     * @throws HokmahException If task name is missing
     */
    protected String[] addTodo(String[] inputArray) throws HokmahException {
        assert inputArray != null : "Null command input";

        validateInputArray(inputArray, 2, ExceptionType.NO_NAME);

        String taskName = inputArray[1].trim();
        validateTaskName(taskName);
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
    protected String[] addDeadline(String[] inputArray) throws HokmahException {
        assert inputArray != null : "Null command input";


        validateInputArray(inputArray, 2, ExceptionType.NO_NAME);

        String[] taskDetails = inputArray[1].split(" /by ");
        validateTaskDetails(taskDetails, 2, ExceptionType.INVALID_DEADLINE_FORMAT);

        String taskName = taskDetails[0].trim();
        validateTaskName(taskName);

        String deadline = taskDetails[1].trim();

        LocalDateTime deadlineDate = parseDateTime(deadline, ExceptionType.INVALID_DEADLINE_FORMAT);
        Deadline newDeadline = new Deadline(taskName, deadlineDate);

        tasks.add(newDeadline);
        storage.saveToFile(tasks.getTaskArrayList());

        return messageHandler.getAddTaskMessage(newDeadline, tasks.size());
    }

    /**
     * Adds a new Event task to the list and saves the updated list.
     *
     * @param inputArray The parsed command input
     * @return Confirmation message with new task details
     * @throws HokmahException If the format is invalid or datetime parsing fails
     */
    protected String[] addEvent(String[] inputArray) throws HokmahException {
        assert inputArray != null : "Null command input";

        validateInputArray(inputArray, 2, ExceptionType.NO_NAME);

        String[] taskDetails = inputArray[1].split(" /from ");
        validateTaskDetails(taskDetails, 2, ExceptionType.EVENT_NO_TIME_START);

        String taskName = taskDetails[0].trim();
        validateTaskName(taskName);

        String[] eventTimeDetails = taskDetails[1].split(" /to ");
        validateTaskDetails(eventTimeDetails, 2, ExceptionType.EVENT_NO_TIME_END);

        String eventStartTime = eventTimeDetails[0].trim();
        LocalDateTime eventStartTimeDate = parseDateTime(eventStartTime, ExceptionType.EVENT_NO_TIME_START);

        String eventEndTime = eventTimeDetails[1].trim();
        LocalDateTime eventEndTimeDate = parseDateTime(eventEndTime, ExceptionType.EVENT_NO_TIME_END);


        if (!eventEndTimeDate.isAfter(eventStartTimeDate)) {
            HokmahException hokmahException = new HokmahException(ExceptionType.EVENT_END_BEFORE_START);
            return hokmahException.getMessageLines();
        }

        Event newEvent = new Event(taskName, eventStartTimeDate, eventEndTimeDate);

        tasks.add(newEvent);
        storage.saveToFile(tasks.getTaskArrayList());

        return messageHandler.getAddTaskMessage(newEvent, tasks.size());
    }

    /**
     * Returns a message for unsupported commands.
     */
    protected String[] unsupportedCommand() {
        return messageHandler.getUnsupportedCommandMessage();
    }

    /**
     * Finds tasks containing the specified keyword in their description
     *
     * @param inputArray The parsed command input
     */
    protected String[] findCommand(String[] inputArray) throws HokmahException {
        assert inputArray != null : "Null command input";

        if (inputArray.length == 1) {
            HokmahException hokmahException = new HokmahException(ExceptionType.NO_NAME);
            return hokmahException.getMessageLines();
        }

        String keyword = inputArray[1];
        ArrayList<Task> matches = new ArrayList<>();

        for (Task task : tasks.getTaskArrayList()) {
            if (task == null) {
                continue;
            }

            if (!task.getName().contains(keyword)) {
                continue;
            }

            matches.add(task);
        }

        return messageHandler.getFindMessage(matches, keyword);

    }

    /**
     * Displays help information with available commands and formats.
     */
    protected String[] help() {
        return messageHandler.getHelpMessage();
    }

    /**
     * Shows tasks occurring on a specific date and saves the updated list.
     *
     * @param inputArray The parsed command input
     * @return a message indicating the tasks occurring on the specified date
     * @throws HokmahException If the date format is invalid
     */
    protected String[] upcomingTasksOn(String[] inputArray) throws HokmahException {
        assert inputArray != null : "Null command input";

        if (inputArray.length == 1) {
            HokmahException hokmahException = new HokmahException(ExceptionType.NO_UPCOMING_ON_DATE);
            return hokmahException.getMessageLines();
        }

        String date = inputArray[1].trim();
        LocalDateTime dateToCheck = parseDateTime(date, ExceptionType.NO_UPCOMING_ON_DATE);

        ArrayList<Task> upcomingTasks = new ArrayList<>();
        for (Task task : tasks.getTaskArrayList()) {
            if (task == null) {
                continue;
            }

            if (task.getTimeEnd() == null) {
                continue;
            }

            if (!task.getTimeEnd().equals(dateToCheck)) {
                continue;
            }

            upcomingTasks.add(task);
        }

        return messageHandler.getUpcomingTasksOnMessage(upcomingTasks, dateToCheck);


    }

    /**
     * Initiates application shutdown sequence.
     */
    protected String[] exit() {
        return messageHandler.getExitMessage();
    }


    /* Helper functions */
    private Task getTask(String[] inputArray) throws HokmahException {
        if (inputArray.length == 1) {
            throw new HokmahException(ExceptionType.NO_INDEX);
        }

        int index = parseTaskIndex(inputArray[1]);
        return tasks.getTaskArrayList().get(index);
    }

    private int parseTaskIndex(String indexString) throws HokmahException {
        int index;
        try {
            int id = Integer.parseInt(indexString);
            index = id - 1;
        } catch (NumberFormatException e) {
            throw new HokmahException(ExceptionType.TASK_NOT_FOUND);
        }

        if (index < 0 || index >= tasks.size()) {
            throw new HokmahException(ExceptionType.TASK_NOT_FOUND);
        }

        return index;
    }

    private void validateInputArray(String[] inputArray, int expectedLength,
                                    ExceptionType exceptionType) throws HokmahException {
        if (inputArray == null || inputArray.length < expectedLength) {
            throw new HokmahException(exceptionType);
        }
    }

    private void validateTaskDetails(String[] taskDetails, int expectedLength,
                                     ExceptionType exceptionType) throws HokmahException {
        if (taskDetails == null || taskDetails.length < expectedLength) {
            throw new HokmahException(exceptionType);
        }
    }

    private void validateTaskName(String taskName) throws HokmahException {
        if (taskName.isEmpty()) {
            throw new HokmahException(ExceptionType.NO_NAME);
        }
    }

    private LocalDateTime parseDateTime(String dateTime, ExceptionType exceptionType) throws HokmahException {
        try {
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATETIME_INPUT_FORMAT));
        } catch (DateTimeParseException e) {
            throw new HokmahException(exceptionType);
        }
    }

    private void saveTask(Task task) {
        tasks.add(task);
        storage.saveToFile(tasks.getTaskArrayList());
    }

}
