package core;

import utils.TransactionHistory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Represents a child's account with specific attributes and methods.
 * This class extends the Account class and includes features such as balance, savings,
 * transaction history, saving goals, and tasks.
 *
 * @author Siyuan Lu
 */
public class ChildAccount extends Account {
    private Set<String> parentAccountIds; // A set to store parent account IDs
    private double balance;
    private double savings;
    private List<TransactionHistory> transactionHistory; // A list to store transaction history records
    private List<SavingGoal> savingGoals;
    private List<Task> tasks;

    /**
     * Constructs a new ChildAccount with the specified accountId, name, and password.
     *
     * @param accountId the unique identifier for this child account
     * @param name the username associated with this child account
     * @param password the password associated with this child account
     */
    public ChildAccount(String accountId, String name, String password) {
        super(accountId, name, password);
        this.accountType = "Kid";
        this.parentAccountIds = new HashSet<>();
        this.transactionHistory = new ArrayList<>();
        this.savingGoals = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    /**
     * Deposits a specified amount into the account balance.
     *
     * @param amount the amount to deposit
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new TransactionHistory("Deposit", amount, "Balance"));
        }
    }

    /**
     * Withdraws a specified amount from the account balance.
     *
     * @param amount the amount to withdraw
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add(new TransactionHistory("Withdraw", -amount, "Balance"));
        }
    }

    /**
     * Returns the current balance of the account.
     *
     * @return the current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the account balance to a specified amount.
     *
     * @param balance the new balance amount
     */
    public void setBalance(double balance) {
        double diff = balance - this.balance;
        this.balance = balance;
        transactionHistory.add(new TransactionHistory("Set Balance", diff, "Balance"));
    }

    /**
     * Saves a specified amount into the savings.
     *
     * @param amount the amount to save
     */
    public void saveMoney(double amount) {
        if (amount > 0) {
            savings += amount;
            transactionHistory.add(new TransactionHistory("Save Money", amount, "Savings"));

            Iterator<SavingGoal> it = savingGoals.iterator();
            while (it.hasNext()) {
                SavingGoal goal = it.next();
                if (goal.isGoalReached(savings)) {
                    double rewardAmount = goal.getReward();
                    balance += goal.getTargetAmount() + rewardAmount;
                    savings -= goal.getTargetAmount();
                    transactionHistory.add(new TransactionHistory("Goal Reached", goal.getTargetAmount(), "Balance"));
                    transactionHistory.add(new TransactionHistory("Goal Reward", rewardAmount, "Balance"));
                    it.remove();
                }
            }
        }
    }

    /**
     * Returns the current savings amount.
     *
     * @return the current savings
     */
    public double getSavings() {
        return savings;
    }

    /**
     * Sets the savings amount to a specified value.
     *
     * @param savings the new savings amount
     */
    public void setSavings(double savings) {
        double diff = savings - this.savings;
        this.savings = savings;
        transactionHistory.add(new TransactionHistory("Set Savings", diff, "Savings"));
    }

    /**
     * Adds a parent account ID to the set of parent accounts.
     *
     * @param parentAccountId the parent account ID to add
     */
    public void addParentAccount(String parentAccountId) {
        parentAccountIds.add(parentAccountId);
    }

    /**
     * Removes a parent account ID from the set of parent accounts.
     *
     * @param parentAccountId the parent account ID to remove
     */
    public void removeParentAccount(String parentAccountId) {
        parentAccountIds.remove(parentAccountId);
    }

    /**
     * Returns a set of all parent account IDs associated with this child account.
     *
     * @return a set of parent account IDs
     */
    public Set<String> getParentAccountIds() {
        return new HashSet<>(parentAccountIds);
    }

    /**
     * Returns the transaction history of the account.
     *
     * @return a list of transaction history records
     */
    public List<TransactionHistory> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    /**
     * Adds a saving goal to the list of saving goals.
     *
     * @param goal the saving goal to add
     */
    public void addSavingGoal(SavingGoal goal) {
        savingGoals.add(goal);
    }

    /**
     * Removes a saving goal from the list of saving goals.
     *
     * @param goal the saving goal to remove
     */
    public void removeSavingGoal(SavingGoal goal) {
        savingGoals.remove(goal);
    }

    /**
     * Returns the list of saving goals.
     *
     * @return a list of saving goals
     */
    public List<SavingGoal> getSavingGoals() {
        return new ArrayList<>(savingGoals);
    }

    /**
     * Adds a task to the list of tasks.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the list of tasks.
     *
     * @param task the task to remove
     */
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    /**
     * Returns the list of tasks.
     *
     * @return a list of tasks
     */
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Marks a task as completed and rewards the account with the task's reward.
     *
     * @param task the task to complete
     */
    public void completeTask(Task task) {
        if (tasks.contains(task)) {
            task.setCompleted(true);
            double reward = task.getReward();
            deposit(reward);
            transactionHistory.add(new TransactionHistory("Task Reward", reward, "Balance"));
        }
    }
}
