package hokmah.task;

import java.util.ArrayList;


public class TaskList {
    private ArrayList<Task> taskArrayList;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {  // constructor
        taskArrayList = new ArrayList<>();
    }

    /**
     * Gets the underlying task collection.
     * @return ArrayList containing tasks
     */
    public ArrayList<Task> getTaskArrayList(){
        return taskArrayList;
    }

    /**
     * Replaces the current task list with a new collection.
     * @param newTaskArrayList The new collection of tasks
     */
    public void setTaskArrayList(ArrayList<Task> newTaskArrayList){
        taskArrayList = newTaskArrayList;
    }

    /**
     * Adds a task to the list.
     * @param task The task to add
     */
    public void add(Task task) {
        taskArrayList.add(task);
    }


    /**
     * Removes a task from the list.
     * @param task The task to remove
     */
    public void delete(Task task) {
        taskArrayList.remove(task);
    }

    /**
     * Gets the number of tasks in the list.
     * @return Current task count
     */
    public int size() {
        return taskArrayList.size();
    }

    /**
     * Prints all tasks in the list to standard output.
     */
    public void printTasks() {
        for (Task task : taskArrayList) {
            if(task != null) System.out.println(task);
        }
    }
}
