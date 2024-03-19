package core;

// 这是一个抽象基类，因为我们不会直接实例化 `Account` 对象
public abstract class Account {
    protected String accountId;
    protected double balance;
    protected String username;
    protected String password;

    public Account(String accountId, String username, String password) {
        this.accountId = accountId;
        this.balance = 0.0;
        this.username = username;
        this.password = password;
    }

    // 共通的方法：存款和取款
    public void deposit(double amount) {
        if(amount > 0) {
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if(amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }

    // 共通的 getter 和 setter 方法
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    // getter 和 setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // 可能还有其他共通的方法
}