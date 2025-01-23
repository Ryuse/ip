import java.util.Scanner;

public class Hokmah {
    static Scanner scanner = new Scanner(System.in);

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

    public static void message(){
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println(input);
            input = scanner.nextLine();
        }

    }

    public static void exit(){
        String message = "Goodbye! I hope you come back soon!";
        System.out.println(message);
    }
}
