package io.reflectoring.buckpal.account.application.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActivityWindow {

  private final List<Activity> activities;


  public ActivityWindow(List<Activity> activities) {
    this.activities = activities;
  }

  public ActivityWindow(Activity... activities) {
    this.activities = new ArrayList<>(Arrays.asList(activities));
  }

  public LocalDateTime getStartTimestamp() {
    return activities.stream()
        .min(Comparator.comparing(Activity::getTimestamp))
        .orElseThrow(IllegalStateException::new)
        .getTimestamp();
  }

  public LocalDateTime getEndTimestamp() {
    return activities.stream()
        .max(Comparator.comparing(Activity::getTimestamp))
        .orElseThrow(IllegalStateException::new)
        .getTimestamp();
  }

  public Money calculateBalance(Account account) {
    Money depositBalance = activities.stream()
        .filter(a -> a.getTargetAccount().equals(account))
        .map(Activity::getMoney)
        .reduce(Money.ZERO, Money::add);

    Money withdrawalBalance = activities.stream()
        .filter(a -> a.getSourceAccount().equals(account))
        .map(Activity::getMoney)
        .reduce(Money.ZERO, Money::add);

    return Money.add(depositBalance, withdrawalBalance.negate());
  }

  public List<Activity> getActivities() {
    return Collections.unmodifiableList(this.activities);
  }

  public void addActivity(Activity activity) {
    this.activities.add(activity);
  }
}
