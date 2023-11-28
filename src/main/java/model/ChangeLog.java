package model;

import java.util.ArrayList;

public class ChangeLog {
  private ArrayList<String> log;

  /**
   * Constructs a ChangeLog object
   */
  public ChangeLog() {
    log = new ArrayList<String>();
  }

  /**
   * Adds a change to the changelog
   * 
   * @param user   User making change
   * @param change Description of change being made
   */
  public void addChange(User user, String change) {
    log.add("User: " + user.getFirstName() + " " + user.getLastName() + "\nchanged: " + change + "\n");
  }

  /**
   * A string representation of the ChangeLog
   * 
   * @return ChangeLog as string
   */
  public String toString() {
    return log.toString();
  }

  /**
   * Clears the ChangeLog
   */
  public void clear() {
    log.clear();
  }

}
