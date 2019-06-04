package co.za.appic.teammanager.models;

import co.za.appic.teammanager.enums.PriorityLevel;
import co.za.appic.teammanager.enums.TaskStatus;

public class TaskModel {
    private String id;
    private String descriptions;
    private PriorityLevel priority;
    private TaskStatus taskStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public PriorityLevel getPriority() {
        return priority;
    }

    public void setPriority(PriorityLevel priority) {
        this.priority = priority;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}