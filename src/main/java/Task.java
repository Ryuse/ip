public class Task {
    private boolean isDone = false;
    private final String name;

    public Task(String name) {
        this.name = name;
    }

    public void markDone() {
        isDone = true;
    }

    public void unmarkDone() {
        isDone = false;
    }

    public String getType(){
        return this.getClass().getName();
    }

    public boolean isDone() {
        return isDone;
    }

    public String getName() {
        return name;
    }

    public String getTimeStart() {
        return null;
    }

    public String getTimeEnd() {
        return null;
    }

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

    public String getSaveText() {
        String output = "";
        output += getType() + "|";
        if (isDone) {
            output += "1";
        }
        else{
            output += "0";
        }
        output += "|" + name;

        return output;
    }


}
