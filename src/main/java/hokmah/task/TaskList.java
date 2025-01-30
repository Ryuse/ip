package hokmah.task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> taskArrayList;

    public TaskList() {  // constructor
        taskArrayList = new ArrayList<>();
    }

    public ArrayList<Task> getTaskArrayList(){
        return taskArrayList;
    }

    public void setTaskArrayList(ArrayList<Task> newTaskArrayList){
        taskArrayList = newTaskArrayList;
    }

    public void add(Task task) {
        taskArrayList.add(task);
    }

    public void delete(Task task) {
        taskArrayList.remove(task);
    }

    public int size() {
        return taskArrayList.size();
    }


    public void printTasks() {
        for (Task task : taskArrayList) {
            if(task != null) System.out.println(task);
        }
    }
}
