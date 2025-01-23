import java.util.ArrayList;
import java.util.Scanner;

public class Hokmah {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Task> taskArrayList = new ArrayList<>();
    public static void main(String[] args) {
        greet();
        messageHandler();
    }

    public static void greet(){
        String logo = ",--.  ,--.         ,--.                         ,--.     \n"
                    + "|  '--'  |  ,---.  |  |,-.  ,--,--,--.  ,--,--. |  ,---. \n"
                    + "|  .--.  | | .-. | |     /  |        | ' ,-.  | |  .-.  |\n"
                    + "|  |  |  | ' '-' ' |  \\  \\  |  |  |  | \\ '-'  | |  | |  |\n"
                    + "`--'  `--'  `---'  `--'`--' `--`--`--'  `--`--' `--' `--' \n";

        String message = "Hello, I am\n " +
                        logo + "\n" +
                        "What can I do for you?";
        System.out.println(message);
    }

    public static void inputHandler(String input) throws HokmahException {
        String[] inputArray = input.split(" ", 2);
        String command = inputArray[0];

        switch (command){
            case "bye":
                exit();
                break;
            case "list":
                showList();
                break;
            case "mark":
                int mark_id = Integer.parseInt(inputArray[1]);
                markTask(mark_id);
                break;
            case "unmark":
                int unmark_id = Integer.parseInt(inputArray[1]);
                unmarkTask(unmark_id);
                break;
            case "delete":
                int delete_id = Integer.parseInt(inputArray[1]);
                deleteTask(delete_id);
                break;
            case "todo":
                addTodo(inputArray);
                break;
            case "deadline":
                addDeadline(inputArray);
                break;
            case "event":
                addEvent(inputArray);
                break;
            case "help":
                help();
                break;
            default:
                unsupportedCommand();
                break;

        }

    }

    public static void showList(){
        for(int i = 0; i < taskArrayList.size(); i++){
            System.out.println(i+1 + "." + taskArrayList.get(i).toString());
        }
    }

    public static Task getTask(int id) throws HokmahException {
        if(id - 1 < 0 || id >= taskArrayList.size()){
            throw new HokmahException(HokmahException.ExceptionType.TASK_NOT_FOUND);
        }
        return taskArrayList.get(id - 1);
    }
    public static void markTask(int id) throws HokmahException {
        Task task = getTask(id);
        task.markDone();
        System.out.println("Nice! I've masked this task as done:");
        System.out.println(task);
    }

    public static void unmarkTask(int id) throws HokmahException {
        Task task = getTask(id);
        task.unmarkDone();
        System.out.println("Nice! I've masked this task as not done yet:");
        System.out.println(task);
    }

    public static void deleteTask(int id) throws HokmahException {
        Task task = getTask(id);
        taskArrayList.remove(task);
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
    }

    public static void addTodo(String[] inputArray) throws HokmahException{
        if(inputArray.length == 1){
            throw new HokmahException(HokmahException.ExceptionType.NO_NAME);
        }
        String taskName = inputArray[1];
        ToDo newTodo = new ToDo(taskName);
        taskArrayList.add(newTodo);
        System.out.println("Got it. I've added this task:\n" + newTodo.toString());
        System.out.println("Now you have " + taskArrayList.size() + " tasks in the list.");
    }

    public static void addDeadline(String[] inputArray) throws HokmahException {
        if(inputArray.length == 1){
            throw new HokmahException(HokmahException.ExceptionType.NO_NAME);
        }
        String[] taskDetails = inputArray[1].split("/by");
        if(taskDetails.length == 1){
            throw new HokmahException(HokmahException.ExceptionType.DEADLINE_NO_TIME_END);
        }

        String taskName = taskDetails[0].trim();
        String deadline = taskDetails[1].trim();
        Deadline newDeadline = new Deadline(taskName, deadline);
        taskArrayList.add(newDeadline);
        System.out.println("Got it. I've added this task:\n" + newDeadline.toString());
        System.out.println("Now you have " + taskArrayList.size() + " tasks in the list.");
    }

    public static void addEvent(String[] inputArray) throws HokmahException {
        if(inputArray.length == 1){
            throw new HokmahException(HokmahException.ExceptionType.NO_NAME);
        }

        String[] taskDetails = inputArray[1].split("/from");
        if(taskDetails.length == 1){
            throw new HokmahException(HokmahException.ExceptionType.EVENT_NO_TIME_START);
        }

        String taskName = taskDetails[0].trim();
        String[] eventTimeDetails = taskDetails[1].split("/to");
        if(eventTimeDetails.length == 1){
            throw new HokmahException(HokmahException.ExceptionType.EVENT_NO_TIME_END);
        }
        String eventStartTime = eventTimeDetails[0].trim();
        String eventEndTime = eventTimeDetails[1].trim();

        Event newEvent = new Event(taskName, eventStartTime, eventEndTime);
        taskArrayList.add(newEvent);
        System.out.println("Got it. I've added this task:\n" + newEvent.toString());
        System.out.println("Now you have " + taskArrayList.size() + " tasks in the list.");
    }

    public static void unsupportedCommand(){
        System.out.println("""
                That's not right
                Just what are you trying to do? Can you ask something else?
                If you don't know what to ask you can use the 'help' command""");

    }

    public static void help(){
        System.out.println("Here is what I can do:");
        String taskList = "list\n\t(Shows all the tasks in the list)\n" +
                "todo [name]\n\t(Adds a todo task to the task list)\n" +
                "deadline [name] /by [time]\n\t(Adds a deadline task to the task list)\n" +
                "event [name] /from [start time] /to [end time]\n\t(Adds a event task to the task list)\n" +
                "mark [task number]\n\t (Marks the task at [task number] in the task list as completed)" +
                "unmark [task number]\n\t (Marks the task at [task number] in the task list as incomplete)" +
                "delete [task number]\n\t (Deletes the task at [task number] in the task list)" +
                "bye\n\t(Only if you want to leave. It's not like I wanted you to be here.)";
        System.out.println(taskList);
    }

    public static void messageHandler(){

        while (true) {
            String input = scanner.nextLine();
            try{
                inputHandler(input);
            }
            catch(HokmahException e){
                System.out.println(e.getMessage());
            }


        }
    }

    public static void exit(){
        String message = "Goodbye! I hope you come back soon!";
        System.out.println(message);
        System.exit(0);
    }
}
