import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class SaveHandler {
    static final String TASKS_FILE_PATH = "data/tasks.txt";

    public static void saveToFile(ArrayList<Task> tasks, String path){
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

    public static ArrayList<Task> loadFromFile(String path){
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
                Task task = null;
                switch (taskType){
                    case "T":
                        task = new ToDo(taskInfo[2]);
                        break;
                    case "D":
                        try{
                            LocalDateTime DeadlineEndTime = LocalDateTime.parse(taskInfo[3], DateTimeFormatter.ofPattern(Hokmah.DATE_TIME_FORMAT));
                            task = new Deadline(taskInfo[2], DeadlineEndTime);

                        }
                        catch (DateTimeParseException e){
                            System.out.println(taskInfo[2] + " is not a valid date time format");
                        }

                        break;
                    case "E":
                        try{
                            LocalDateTime EventStartTime = LocalDateTime.parse(taskInfo[3], DateTimeFormatter.ofPattern(Hokmah.DATE_TIME_FORMAT));
                            LocalDateTime eventEndTime = LocalDateTime.parse(taskInfo[4], DateTimeFormatter.ofPattern(Hokmah.DATE_TIME_FORMAT));
                            task = new Event(taskInfo[2], EventStartTime, eventEndTime);
                        }
                        catch (DateTimeParseException e){
                            System.out.println(taskInfo[2] + " is not a valid date time format");
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
}
