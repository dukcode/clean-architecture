package io.reflectoring.buckpal.account.application.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Activity {


  private final Long id;

  private final Account ownerAccount;

  private final Account sourceAccount;

  private final Account targetAccount;

  private final LocalDateTime timestamp;

  private final Money money;

  public Activity(
      Account ownerAccount,
      Account sourceAccount,
      Account targetAccount,
      LocalDateTime timestamp,
      Money money) {
    this.id = null;
    this.ownerAccount = ownerAccount;
    this.sourceAccount = sourceAccount;
    this.targetAccount = targetAccount;
    this.timestamp = timestamp;
    this.money = money;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Activity activity = (Activity) o;

    return Objects.equals(id, activity.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
