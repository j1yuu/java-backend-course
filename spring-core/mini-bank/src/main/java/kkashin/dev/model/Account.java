package kkashin.dev.model;

import java.util.Objects;

public class Account {
    private final int id;
    private final int userId;
    private int moneyAmount;

    public Account (
            int id,
            int userId,
            int moneyAmount
    ) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Account o = (Account) obj;
        return id == o.getId();
    }

    @Override
    public String toString() {
        return "Account{id=" + id +
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount + "}";
    }
}
