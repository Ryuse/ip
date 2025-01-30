package hokmah;

import hokmah.task.Deadline;
import hokmah.task.Event;
import hokmah.task.Task;
import hokmah.task.ToDo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles persistent storage operations for tasks.
 * Manages loading/saving tasks to/from files in pipe-separated format.
 */
public class SaveHandler {

    private String filePath;

    /**
     * Initializes storage handler with file path.
     * @param filePath Storage file location
     */
    public SaveHandler(String filePath){
        this.filePath = filePath;
    }

    /**
     * Saves tasks to specified file path.
     * @param tasks List of tasks to save
     * @param path Custom save location
     */
    public void saveToFile(ArrayList<Task> tasks, String path){
        File file = new File(path);
        try {
            file.getParentFile().mkdirs();

            file.createNewFile();
            FileWriter writer = new FileWriter(file);

            for (Task task : tasks) {
                if(task != null) writer.write(task.getSaveText() + "\n");
            }

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves tasks to default file path.
     * @param tasks List of tasks to save
     */
    public void saveToFile(ArrayList<Task> tasks){
        saveToFile(tasks, filePath);
    }

    /**
     * Loads tasks from specified file path.
     * @param path Custom load location
     * @return List of loaded tasks
     */
    public ArrayList<Task> loadFromFile(String path){
        ArrayList<Task> tasks = new ArrayList<Task>();
        File file = new File(path);

        try {
            file.getParentFile().mkdirs();

            file.createNewFile();
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                String[] taskInfo = scanner.next().split("\\|");
                String taskType = taskInfo[0];
                String taskName = taskInfo[2];
                Task task = null;
                switch (taskType){
                    case "T":
                        task = new ToDo(taskName);
                        break;
                    case "D":
                        try{
                            System.out.println(taskInfo[3]);
                            LocalDateTime DeadlineEndTime = LocalDateTime.parse(taskInfo[3], DateTimeFormatter.ofPattern(Hokmah.DATE_TIME_FORMAT));
                            task = new Deadline(taskName, DeadlineEndTime);

                        }
                        catch (DateTimeParseException e){
                            System.out.println(taskName+ " is not a valid date time format");
                        }

                        break;
                    case "E":
                        try{
                            LocalDateTime EventStartTime = LocalDateTime.parse(taskInfo[3], DateTimeFormatter.ofPattern(Hokmah.DATE_TIME_FORMAT));
                            LocalDateTime eventEndTime = LocalDateTime.parse(taskInfo[4], DateTimeFormatter.ofPattern(Hokmah.DATE_TIME_FORMAT));
                            task = new Event(taskName, EventStartTime, eventEndTime);
                        }
                        catch (DateTimeParseException e){
                            System.out.println(taskName + " is not a valid date time format");
                        }
                        break;
                }

                if(taskInfo[1].equals("1")){
                    task.markDone();
                }
                tasks.add(task);
            }
            scanner.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Loads tasks from default file path.
     * @return List of loaded tasks
     */
    public ArrayList<Task> loadFromFile() {
        return loadFromFile(filePath);
    }
}
