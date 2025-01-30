import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Hokmah {
    static final String DATE_TIME_FORMAT = "yyyy-MM-dd HHmm";

    static Scanner scanner = new Scanner(System.in);
    private TaskList tasks;
    private UiHandler ui;
    private SaveHandler storage;
    private InputHandler inputHandler;
    private CommandHandler commandHandler;

    public Hokmah(String filePath){
        tasks = new TaskList();
        ui = new UiHandler();
        storage = new SaveHandler(filePath);
        tasks.setTaskArrayList(storage.loadFromFile());

        commandHandler = new CommandHandler(tasks, storage, ui);
        inputHandler = new InputHandler(commandHandler);

        ui.showWelcomeMessage();
    }

    public void run(){
        messageHandler();
    }


    public static void main(String[] args) {
        new Hokmah("data/tasks.txt").run();
    }

    public void messageHandler(){
        while(true) {
            String input = scanner.nextLine();
            ui.showLine();
            try{
                inputHandler.process(input);
            }
            catch(HokmahException e){
                System.out.println(e.getMessage());
            }
            ui.showLine();
        }
    }

}
