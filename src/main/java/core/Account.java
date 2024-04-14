package core;

public abstract class Account {
    protected String accountId;

    protected String username;
    protected String password;

    protected String accountType;

    public Account(String accountId, String username, String password) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
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

    public String getAccountType() {return this.accountType;}

    // 可能还有其他共通的方法
}