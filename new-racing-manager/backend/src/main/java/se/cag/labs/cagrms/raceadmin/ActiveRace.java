package se.cag.labs.cagrms.raceadmin;

public class ActiveRace {
  @Id
  private String id;
  private User active;

  public ActiveRace() {
  }

  public ActiveRace(User active) {
    this.active = active;
  }

  public String getId() {
    return id;
  }

  public User getActive() {
    return active;
  }

  public void setActive(User active) {
    this.active = active;
  }
}
