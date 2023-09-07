package io.reflectoring.buckpal.account.application.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

  private final Long id;
  private final Money baselineBalance;
  private final ActivityWindow activityWindow;

  public static Account withoutId(
      Money baselineBalance,
      ActivityWindow activityWindow) {
    return new Account(null, baselineBalance, activityWindow);
  }

  public static Account withId(
      Long accountId,
      Money baselineBalance,
      ActivityWindow activityWindow) {
    return new Account(accountId, baselineBalance, activityWindow);
  }

  public Optional<Long> getId() {
    return Optional.ofNullable(this.id);
  }

  public Money calculateBalance() {
    return Money.add(
        this.baselineBalance,
        this.activityWindow.calculateBalance(this));
  }

  public boolean withdraw(Money money, Account targetAccount) {

    if (!mayWithdraw(money)) {
      return false;
    }

    Activity withdrawal = new Activity(
        this,
        this,
        targetAccount,
        LocalDateTime.now(),
        money);
    this.activityWindow.addActivity(withdrawal);
    return true;
  }

  public boolean deposit(Money money, Account sourceAccount) {
    Activity deposit = new Activity(
        this,
        sourceAccount,
        this,
        LocalDateTime.now(),
        money);
    this.activityWindow.addActivity(deposit);
    return true;
  }

  private boolean mayWithdraw(Money money) {
    return Money.add(
            this.calculateBalance(),
            money.negate())
        .isPositiveOrZero();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Account account = (Account) o;

    return Objects.equals(id, account.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
