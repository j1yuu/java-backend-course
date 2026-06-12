package kkashin.dev.model;

import java.util.List;
import java.util.Objects;

public class User {
    private final int id;
    private String login;
    private List<Integer> accountIdList;

    public User (
            int id,
            String login,
            List<Integer> accountIdList
    ) {
        this.id = id;
        this.login = login;
        this.accountIdList = List.copyOf(accountIdList);
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Integer> getAccountIdList() {
        return accountIdList;
    }

    public void setAccountList(List<Integer> accountIdList) {
        this.accountIdList = List.copyOf(accountIdList);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", " +
                "login=" + login + ", " +
                "accountIds=" + accountIdList.toString() + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        User o = (User) obj;
        return id == o.getId();
    }
}
