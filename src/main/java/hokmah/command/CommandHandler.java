package hokmah.command;

import hokmah.exception.HokmahException;
import hokmah.SaveHandler;
import hokmah.task.*;
import hokmah.ui.UiHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CommandHandler {
    static final String DATE_TIME_FORMAT = "yyyy-MM-dd HHmm";
    TaskList tasks;
    SaveHandler storage;
    UiHandler ui;

    public CommandHandler(TaskList tasks, SaveHandler storage, UiHandler ui) {
        this.tasks = tasks;
        this.storage = storage;
        this.ui = ui;
    }

    public void showList() {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTaskArrayList().get(i);
            if (task != null) System.out.println(i + 1 + "." + task);
        }
    }

    public Task getTask(int id) throws HokmahException {
        int index = id - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new HokmahException(HokmahException.ExceptionType.TASK_NOT_FOUND);
        }
        return tasks.getTaskArrayList().get(index);
    }

    public void markTask(int id) throws HokmahException {
        Task task = getTask(id);
        task.markDone();
        System.out.println("Nice! I've masked this task as done:");
        System.out.println(task);

    }

    public void unmarkTask(int id) throws HokmahException {
        Task task = getTask(id);
        task.unmarkDone();
        System.out.println("Nice! I've masked this task as not done yet:");
        System.out.println(task);
        storage.saveToFile(tasks.getTaskArrayList());
    }

    public void deleteTask(int id) throws HokmahException {
        Task task = getTask(id);
        tasks.delete(task);
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        storage.saveToFile(tasks.getTaskArrayList());
    }

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

    public void unsupportedCommand() {
        System.out.println("""
                That's not right
                Just what are you trying to do? Can you ask something else?
                If you don't know what to ask you can use the 'help' command""");

    }

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
                "bye\n\t(Only if you want to leave. It's not like I wanted you to be here.)";
        System.out.println(taskList);
    }

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

    public void exit() {
        ui.showExitMessage();
        System.exit(0);
    }

}
