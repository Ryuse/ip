public class Task {
    private boolean isDone = false;
//    private static int lastId = 0;
//    private final int id;
    private final String name;

    public Task(String name) {
        this.name = name;
//        this.id = ++Task.lastId;
    }

    public void markDone() {
        isDone = true;
    }

    public void unmarkDone() {
        isDone = false;
    }

    public boolean getDone() {
        return isDone;
    }

    public String getName() {
        return name;
    }

//    public int getId() {
//        return id;
//    }

    public String toString() {
        String output = "";
        if (isDone) {
            output += "[X] ";
        }
        else{
            output += "[ ] ";
        }
        output += name;
        return output;
    }
}
