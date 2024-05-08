package core;

import utils.TransactionHistory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChildAccount extends Account {
    private Set<String> parentAccountIds; // 存储家长账户ID的集合
    private double balance;
    private double savings;
    private List<TransactionHistory> transactionHistory; // 存储交易历史记录
    private List<SavingGoal> savingGoals;
    private List<Task> tasks;

    public ChildAccount(String accountId, String name, String password) {
        super(accountId, name, password);
        this.accountType = "Kid";
        this.parentAccountIds = new HashSet<>();
        this.transactionHistory = new ArrayList<>();
        this.savingGoals = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new TransactionHistory("Deposit", amount, "Balance"));
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add(new TransactionHistory("Withdraw", -amount, "Balance"));
        }
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        double diff = balance - this.balance;
        this.balance = balance;
        transactionHistory.add(new TransactionHistory("Set Balance", diff, "Balance"));
    }

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

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        double diff = savings - this.savings;
        this.savings = savings;
        transactionHistory.add(new TransactionHistory("Set Savings", diff, "Savings"));
    }

    // 孩子账户特有的方法或属性
    public void addParentAccount(String parentAccountId) {
        parentAccountIds.add(parentAccountId);
    }

    public void removeParentAccount(String parentAccountId) {
        parentAccountIds.remove(parentAccountId);
    }

    // 获取与孩子账户关联的所有家长账户的ID
    public Set<String> getParentAccountIds() {
        return new HashSet<>(parentAccountIds);
    }

    // 获取交易历史记录
    public List<TransactionHistory> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public void addSavingGoal(SavingGoal goal) {
        savingGoals.add(goal);
    }

    public void removeSavingGoal(SavingGoal goal) {
        savingGoals.remove(goal);
    }

    public List<SavingGoal> getSavingGoals() {
        return new ArrayList<>(savingGoals);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void completeTask(Task task) {
        if (tasks.contains(task)) {
            task.setCompleted(true);
            double reward = task.getReward();
            deposit(reward);
            transactionHistory.add(new TransactionHistory("Task Reward", reward, "Balance"));
        }
    }
}