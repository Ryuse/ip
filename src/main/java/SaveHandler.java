import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
                writer.write(task.getSaveText() + "\n");
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
                        task = new Deadline(taskInfo[2], taskInfo[3]);
                        break;
                    case "E":
                        task = new Event(taskInfo[2], taskInfo[3], taskInfo[4]);
                        break;
                }

                if(taskInfo[1].equals("1")){
                    task.markDone();
                }
                tasks.add(task);
                System.out.println(task.toString());
            }
            scanner.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
