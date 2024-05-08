package core;

import java.util.Date;

public class Task {
    private String name;
    private String description;
    private double reward;
    private boolean isCompleted;
    private Date dueDate;

    public Task(String name, String description, double reward, Date dueDate) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.isCompleted = false;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isTaskCompleted() {
        return isCompleted;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}