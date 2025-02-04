package hokmah;

import hokmah.command.CommandHandler;
import hokmah.command.InputHandler;
import hokmah.exception.HokmahException;
import hokmah.task.TaskList;
import hokmah.ui.UiHandler;

import java.util.Scanner;

/**
 * Main application class for the task management system.
 * Initializes core components and manages the program lifecycle.
 */
public class Hokmah {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HHmm";

    private static final Scanner scanner = new Scanner(System.in);
    protected TaskList tasks;
    protected UiHandler ui;
    protected SaveHandler storage;
    protected InputHandler inputHandler;
    protected CommandHandler commandHandler;

    /**
     * Initializes application components.
     *
     * @param filePath Path for task data storage
     */
    public Hokmah(String filePath) {
        tasks = new TaskList();
        ui = new UiHandler();
        storage = new SaveHandler(filePath);
        tasks.setTaskArrayList(storage.loadFromFile());

        commandHandler = new CommandHandler(tasks, storage, ui);
        inputHandler = new InputHandler(commandHandler);

        ui.showWelcomeMessage();
    }


    /**
     * Starts main application loop.
     */
    public void run() {
        messageHandler();
    }

    /**
     * Handles continuous user input processing.
     */
    public void messageHandler() {
        while (true) {

            String input = scanner.nextLine();
            ui.showLine();
            try {
                inputHandler.process(input);
            } catch (HokmahException e) {
                System.out.println(e.getMessage());
            }
            ui.showLine();
        }
    }

    public static void main(String[] args) {
        new Hokmah("data/tasks.txt").run();
    }

}
