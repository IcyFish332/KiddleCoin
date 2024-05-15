package core;

public class SavingGoal {
    private String name;
    private String description;
    private double targetAmount;
    private double reward;

    public SavingGoal(String Name, String description, double targetAmount, double reward) {
        this.name = Name;
        this.description = description;
        this.targetAmount = targetAmount;
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isGoalReached(double savings) {
        return savings >= targetAmount;
    }
}