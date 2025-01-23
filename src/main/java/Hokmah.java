import java.util.ArrayList;
import java.util.Scanner;

public class Hokmah {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<String> messageLog = new ArrayList<>();
    public static void main(String[] args) {
        greet();
        message();
        exit();


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

    public static void inputHandler(String input){
        if(input.equals("bye")){
            exit();
            System.exit(0);
        }

        else if(input.equals("list")){
            showLog();
        }

        else{
            messageLog.add(input);
            System.out.println("added: " + input);
        }
    }

    public static void showLog(){
        for(int i = 0; i < messageLog.size(); i++){
            System.out.println(i+1 +". " + messageLog.get(i));
        }
    }

    public static void message(){
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            inputHandler(input);
            input = scanner.nextLine();
        }
    }

    public static void exit(){
        String message = "Goodbye! I hope you come back soon!";
        System.out.println(message);
    }
}
