public class HokmahException extends Exception{
    public enum ExceptionType {
        NO_NAME,
        DEADLINE_NO_TIME_END,
        EVENT_NO_TIME_START,
        EVENT_NO_TIME_END,
        TASK_NOT_FOUND,
        NO_UPCOMING_ON_DATE

    }
    static final String DATE_TIME_FORMAT = "yyyy-MM-dd HHmm";

    public HokmahException(ExceptionType type) throws HokmahException {
        String message = "";
        if(type == ExceptionType.NO_NAME){
            message = "Just what are you going to do? Can you at least tell me the name?";
        }

        else if(type == ExceptionType.DEADLINE_NO_TIME_END){
            message = "Ok so? When is the deadline by? Try again but by adding /by " + Hokmah.DATE_TIME_FORMAT;

        }
        else if(type == ExceptionType.EVENT_NO_TIME_START){
            message = "I don't know when your event will start and end? Can you try again but by adding /from " + Hokmah.DATE_TIME_FORMAT + " /to " + Hokmah.DATE_TIME_FORMAT;
        }
        else if(type == ExceptionType.EVENT_NO_TIME_END){
            message = "I don't know when your event will end? Can you try again but by adding /to " + Hokmah.DATE_TIME_FORMAT;
        }
        else if(type == ExceptionType.TASK_NOT_FOUND){
            message = "The task you are trying to perform an action on cannot be found. Are you sure you put the right task id?";
        }

        else if(type == ExceptionType.NO_UPCOMING_ON_DATE){
            message = "I don't know when you want to see the upcoming tasks. Try again by adding /on " + Hokmah.DATE_TIME_FORMAT;
        }
        else {
            message = "Something went wrong.. This should not happen!";
        }
        throw new HokmahException(message);

    }

    public HokmahException(String message){
        super(message);
    }
}
