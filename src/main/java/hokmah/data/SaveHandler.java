package hokmah.data;


import static hokmah.exception.HokmahException.ExceptionType.NO_SAVE_FILE;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import hokmah.Hokmah;
import hokmah.exception.HokmahException;
import hokmah.task.Deadline;
import hokmah.task.Event;
import hokmah.task.Task;
import hokmah.task.ToDo;

/**
 * Handles persistent storage operations for tasks.
 * Manages loading/saving tasks to/from files in pipe-separated format.
 */
public class SaveHandler {

    private String filePath;

    /**
     * Initializes storage handler with file path.
     *
     * @param filePath Storage file location
     */
    public SaveHandler(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves tasks to specified file path.
     *
     * @param tasks List of tasks to save
     * @param path  Custom save location
     */
    public void saveToFile(ArrayList<Task> tasks, String path) {
        File file = new File(path);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);

            for (Task task : tasks) {
                if (task != null) {
                    writer.write(task.getSaveText() + "\n");
                }
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Saves tasks to default file path.
     *
     * @param tasks List of tasks to save
     */
    public void saveToFile(ArrayList<Task> tasks) {
        saveToFile(tasks, filePath);
    }

    /**
     * Loads tasks from specified file path.
     *
     * @param path Custom load location
     * @return List of loaded tasks
     */
    public ArrayList<Task> loadFromFile(String path) throws HokmahException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        File file = new File(path);

        Scanner scanner;
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();

            scanner = new Scanner(file);
            scanner.useDelimiter("\n");
        } catch (Exception e) {
            throw new HokmahException(NO_SAVE_FILE);
        }

        while (scanner.hasNext()) {
            String[] taskInfo = scanner.next().split("\\|");
            String taskType = taskInfo[0];

            Task task = null;

            switch (taskType) {
            case "T" -> task = loadTodoSave(taskInfo);
            case "D" -> task = loadDeadlineSave(taskInfo);
            case "E" -> task = loadEventSave(taskInfo);
            default -> System.out.println("Invalid task type");
            }

            if (task == null) {
                continue;
            }

            tasks.add(task);
        }

        scanner.close();

        return tasks;
    }

    /**
     * Loads tasks from default file path.
     *
     * @return List of loaded tasks
     */
    public ArrayList<Task> loadFromFile() throws HokmahException {
        return loadFromFile(filePath);
    }

    /**
     * Loads a Todo task from save text.
     *
     * @param taskInfo - String array containing information about the task
     * @return Task object of the loaded Todo task
     */
    private Task loadTodoSave(String[] taskInfo) {
        String taskName = taskInfo[2];
        ToDo task = new ToDo(taskName);

        if (taskInfo[1].equals("1")) {
            task.markDone();
        }

        return task;
    }

    /**
     * Loads a Deadline task from save text.
     *
     * @param taskInfo - String array containing information about the task
     * @return Task object of the loaded Deadline task
     */
    private Task loadDeadlineSave(String[] taskInfo) {
        String taskName = taskInfo[2];
        Deadline task = null;

        try {
            LocalDateTime DeadlineEndTime = LocalDateTime.parse(taskInfo[3],
                    DateTimeFormatter.ofPattern(Hokmah.DATETIME_INPUT_FORMAT));
            task = new Deadline(taskName, DeadlineEndTime);

            if (taskInfo[1].equals("1")) {
                task.markDone();
            }
        } catch (DateTimeParseException e) {
            System.out.println(taskName + " is not a valid date time format");
        }

        return task;
    }

    /**
     * Loads an Event task from save text.
     *
     * @param taskInfo - String array containing information about the task
     * @return Task object of the loaded Event task
     */
    private Task loadEventSave(String[] taskInfo) {
        String taskName = taskInfo[2];
        Event task = null;

        try {
            LocalDateTime EventStartTime = LocalDateTime.parse(taskInfo[3],
                    DateTimeFormatter.ofPattern(Hokmah.DATETIME_INPUT_FORMAT));
            LocalDateTime eventEndTime = LocalDateTime.parse(taskInfo[4],
                    DateTimeFormatter.ofPattern(Hokmah.DATETIME_INPUT_FORMAT));
            task = new Event(taskName, EventStartTime, eventEndTime);

            if (taskInfo[1].equals("1")) {
                task.markDone();
            }
        } catch (DateTimeParseException e) {
            System.out.println(taskName + " is not a valid date time format");
        }

        return task;
    }



}
