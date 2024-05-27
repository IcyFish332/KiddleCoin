package core;

import java.util.Date;

/**
 * Represents a task with a name, description, reward, completion status, and due date.
 * This class provides methods to get and set these attributes.
 *
 * @author Siyuan Lu
 */
public class Task {
    private String name;
    private String description;
    private double reward;
    private boolean isCompleted;
    private Date dueDate;

    /**
     * Constructs a new Task with the specified name, description, reward, and due date.
     *
     * @param name the name of the task
     * @param description the description of the task
     * @param reward the reward for completing the task
     * @param dueDate the due date of the task
     */
    public Task(String name, String description, double reward, Date dueDate) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.isCompleted = false;
        this.dueDate = dueDate;
    }

    /**
     * Returns the name of the task.
     *
     * @return the name of the task
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task.
     *
     * @param name the new name of the task
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the task.
     *
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description the new description of the task
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the reward for completing the task.
     *
     * @return the reward
     */
    public double getReward() {
        return reward;
    }

    /**
     * Sets the reward for completing the task.
     *
     * @param reward the new reward
     */
    public void setReward(double reward) {
        this.reward = reward;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param completed the new completion status
     */
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    /**
     * Returns whether the task is completed.
     *
     * @return true if the task is completed, false otherwise
     */
    public boolean isTaskCompleted() {
        return isCompleted;
    }

    /**
     * Returns the due date of the task.
     *
     * @return the due date
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the task.
     *
     * @param dueDate the new due date
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
