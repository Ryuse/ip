package hokmah.command;

import hokmah.exception.HokmahException;
import hokmah.SaveHandler;
import hokmah.task.*;
import hokmah.ui.UiHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Executes concrete operations based on parsed commands.
 * Contains business logic for task manipulation and system operations.
 */
public class CommandHandler {
    static final String DATE_TIME_FORMAT = "yyyy-MM-dd HHmm";
    TaskList tasks;
    SaveHandler storage;
    UiHandler ui;

    /**
     * Initializes command handler with dependencies.
     * @param tasks Task collection to operate on
     * @param storage Persistent storage handler
     * @param ui User interface handler
     */
    public CommandHandler(TaskList tasks, SaveHandler storage, UiHandler ui) {
        this.tasks = tasks;
        this.storage = storage;
        this.ui = ui;
    }

    /**
     * Displays all tasks in formatted list.
     */
    public void showList() {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTaskArrayList().get(i);
            if (task != null) System.out.println(i + 1 + "." + task);
        }
    }

    /**
     * Retrieves task by ID.
     * @param id Task index (1-based)
     * @return Requested Task object
     * @throws HokmahException For invalid task IDs
     */
    public Task getTask(int id) throws HokmahException {
        int index = id - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new HokmahException(HokmahException.ExceptionType.TASK_NOT_FOUND);
        }
        return tasks.getTaskArrayList().get(index);
    }

    /**
     * Marks task as completed.
     * @param id Task index to mark
     * @throws HokmahException For invalid task IDs
     */
    public void markTask(int id) throws HokmahException {
        Task task = getTask(id);
        task.markDone();
        System.out.println("Nice! I've masked this task as done:");
        System.out.println(task);

    }

    /**
     * Marks a task as incomplete.
     * @param id The 1-based index of the task to unmark
     * @throws HokmahException If the index is invalid
     */
    public void unmarkTask(int id) throws HokmahException {
        Task task = getTask(id);
        task.unmarkDone();
        System.out.println("Nice! I've masked this task as not done yet:");
        System.out.println(task);
    }

    /**
     * Deletes a task from the list and saves the updated list.
     * @param id The 1-based index of the task to delete
     * @throws HokmahException If the index is invalid
     */
    public void deleteTask(int id) throws HokmahException {
        Task task = getTask(id);
        tasks.delete(task);
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        storage.saveToFile(tasks.getTaskArrayList());
    }

    /**
     * Adds a new Todo task to the list and saves the updated list.
     * @param inputArray The parsed command input
     * @throws HokmahException If the task name is missing
     */
    public void addTodo(String[] inputArray) throws HokmahException {
        if (inputArray.length == 1) {
            throw new HokmahException(HokmahException.ExceptionType.NO_NAME);
        }
        String taskName = inputArray[1];
        ToDo newTodo = new ToDo(taskName);
        tasks.add(newTodo);
        System.out.println("Got it. I've added this task:\n" + newTodo);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        storage.saveToFile(tasks.getTaskArrayList());
    }

    /**
     * Adds a new Deadline task to the list and saves the updated list.
     * @param inputArray The parsed command input
     * @throws HokmahException If the format is invalid or datetime parsing fails
     */
    public void addDeadline(String[] inputArray) throws HokmahException {
        if (inputArray.length == 1) {
            throw new HokmahException(HokmahException.ExceptionType.NO_NAME);
        }
        String[] taskDetails = inputArray[1].split("/by");
        if (taskDetails.length == 1) {
            throw new HokmahException(HokmahException.ExceptionType.DEADLINE_NO_TIME_END);
        }


        try {
            String taskName = taskDetails[0].trim();
            String deadline = taskDetails[1].trim();

            LocalDateTime deadlineDate = LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
            Deadline newDeadline = new Deadline(taskName, deadlineDate);
            tasks.add(newDeadline);

            System.out.println("Got it. I've added this task:\n" + newDeadline);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");

            storage.saveToFile(tasks.getTaskArrayList());

        } catch (DateTimeParseException e) {
            throw new HokmahException(HokmahException.ExceptionType.DEADLINE_NO_TIME_END);
        }
    }

    /**
     * Adds a new Event task to the list and saves the updated list.
     * @param inputArray The parsed command input
     * @throws HokmahException If the format is invalid or datetime parsing fails
     */
    public void addEvent(String[] inputArray) throws HokmahException {
        if (inputArray.length == 1) {
            throw new HokmahException(HokmahException.ExceptionType.NO_NAME);
        }

        String[] taskDetails = inputArray[1].split("/from");
        if (taskDetails.length == 1) {
            throw new HokmahException(HokmahException.ExceptionType.EVENT_NO_TIME_START);
        }

        String taskName = taskDetails[0].trim();
        String[] eventTimeDetails = taskDetails[1].split("/to");
        if (eventTimeDetails.length == 1) {
            throw new HokmahException(HokmahException.ExceptionType.EVENT_NO_TIME_END);
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
            System.out.println("Got it. I've added this task:\n" + newEvent);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            storage.saveToFile(tasks.getTaskArrayList());
        } catch (DateTimeParseException e) {
            throw new HokmahException(HokmahException.ExceptionType.EVENT_NO_TIME_START);
        }
    }

    /**
     * Displays a message for unsupported commands.
     */
    public void unsupportedCommand() {
        System.out.println("""
                That's not right
                Just what are you trying to do? Can you ask something else?
                If you don't know what to ask you can use the 'help' command""");

    }

    /**
     * Finds tasks containing the specified keyword in their description
     * @param inputArray The parsed command input
     */
    public void findCommand(String[] inputArray) throws HokmahException {
        if (inputArray.length == 1) {
            throw new HokmahException(HokmahException.ExceptionType.NO_NAME);
        }

        String keyword = inputArray[1];
        ArrayList<Task> matches = new ArrayList<>();

        for (Task task : tasks.getTaskArrayList()) {
            if(task == null){
                continue;
            }

            if (task.getName().contains(keyword)) {
                matches.add(task);
            }
        }

        if (matches.isEmpty()) {
            System.out.println("No tasks found containing: " + keyword);
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println((i + 1) + "." + matches.get(i));
            }
        }
    }

    /**
     * Displays help information with available commands and formats.
     */
    public void help() {
        System.out.println("Here is what I can do:");
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
     * Shows tasks occurring on a specific date and saves the updated list.
     * @param inputArray The parsed command input
     * @throws HokmahException If the date format is invalid
     */
    public void upcomingTasksOn(String[] inputArray) throws HokmahException {
        if (inputArray.length == 1) {
            throw new HokmahException(HokmahException.ExceptionType.NO_UPCOMING_ON_DATE);
        }

        try {
            String[] taskDetails = inputArray[1].split("/on");

            String date = taskDetails[1].trim();
            LocalDateTime dateToCheck = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));


            int upcomingTasks = 0;
            System.out.println("Upcoming tasks on " + dateToCheck.format(DateTimeFormatter.ofPattern(Task.DATE_STRING_OUTPUT_FORMAT)) + ":");
            for (Task task : tasks.getTaskArrayList()) {
                if (task.getTimeEnd().equals(dateToCheck)) {
                    System.out.println(task);
                    upcomingTasks += 1;
                }
            }

            if (upcomingTasks == 0) {
                System.out.println("You have no upcoming tasks");
            } else {
                System.out.println("You have " + upcomingTasks + " upcoming tasks");
            }
        } catch (DateTimeParseException e) {
            throw new HokmahException(HokmahException.ExceptionType.NO_UPCOMING_ON_DATE);
        }
    }

    /**
     * Initiates application shutdown sequence.
     */
    public void exit() {
        ui.showExitMessage();
        System.exit(0);
    }

}
