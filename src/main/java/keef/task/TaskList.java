package keef.task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> tasks;

    public TaskList(){
        tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks){
        this.tasks = new ArrayList<>(tasks);
    }

    public void deleteTask(Task task){
        tasks.remove(task);
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public Task getTask(int taskIndex){
        return tasks.get(taskIndex);
    }

    public int getSize() {
        return tasks.size();
    }

    public void markTask(Task task){
        task.markAsDone();
    }

    public void unmarkTask(Task task){
        task.markAsUndone();
    }

    public List<Task> getAllTasks(){
        return tasks;
    }

}
