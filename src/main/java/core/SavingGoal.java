package core;

/**
 * Represents a saving goal with a target amount and reward.
 * This class includes attributes such as name, description, target amount, and reward.
 * It also provides methods to check if the goal has been reached.
 *
 * @author Siyuan Lu
 */
public class SavingGoal {
    private String name;
    private String description;
    private double targetAmount;
    private double reward;

    /**
     * Constructs a new SavingGoal with the specified name, description, target amount, and reward.
     *
     * @param name the name of the saving goal
     * @param description the description of the saving goal
     * @param targetAmount the target amount to achieve the saving goal
     * @param reward the reward for achieving the saving goal
     */
    public SavingGoal(String name, String description, double targetAmount, double reward) {
        this.name = name;
        this.description = description;
        this.targetAmount = targetAmount;
        this.reward = reward;
    }

    /**
     * Returns the name of the saving goal.
     *
     * @return the name of the saving goal
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the saving goal.
     *
     * @param name the new name of the saving goal
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the target amount to achieve the saving goal.
     *
     * @return the target amount
     */
    public double getTargetAmount() {
        return targetAmount;
    }

    /**
     * Sets the target amount to achieve the saving goal.
     *
     * @param targetAmount the new target amount
     */
    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    /**
     * Returns the reward for achieving the saving goal.
     *
     * @return the reward
     */
    public double getReward() {
        return reward;
    }

    /**
     * Sets the reward for achieving the saving goal.
     *
     * @param reward the new reward
     */
    public void setReward(double reward) {
        this.reward = reward;
    }

    /**
     * Returns the description of the saving goal.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the saving goal.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Checks if the saving goal has been reached based on the current savings.
     *
     * @param savings the current savings
     * @return true if the savings are greater than or equal to the target amount, false otherwise
     */
    public boolean isGoalReached(double savings) {
        return savings >= targetAmount;
    }
}
