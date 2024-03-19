package core;

import java.util.HashSet;
import java.util.Set;
public class ChildAccount extends Account {

    private Set<String> parentAccountIds; // 存储家长账户ID的集合

    public ChildAccount(String accountId, String name, String password) {
        super(accountId, name, password);
        this.parentAccountIds = new HashSet<>();
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

}