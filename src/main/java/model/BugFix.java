package model;

/**
 * BugFix class represents a task for fixing a software bug.
 * It extends the Task class and adds specific attributes related to bug fixes.
 */
public class BugFix extends Task {

  // Private fields to store bug description and reproduction steps
  private String bugDescription;
  private String bugReproductionSteps;

  /**
   * Constructor for BugFix class.
   *
   * @param taskName        The name of the bug fix task.
   * @param taskDescription A description of the bug fix task.
   * @param taskPriority    The priority of the bug fix task.
   * @param assignee        The user assigned to the bug fix task.
   * @param bugDescription  A description of the software bug.
   * @param bugReproSteps   The steps to reproduce the software bug.
   */
  public BugFix(String taskName, String taskDescription, int taskPriority, User assignee, String bugDescription,
      String bugReproSteps) {
    super(taskName, taskDescription, Math.max(1, taskPriority), assignee);
    if (bugDescription == null) {
      this.bugDescription = "";
    } else {
      this.bugDescription = bugDescription;
    }
    if (bugReproSteps == null) {
      this.bugReproductionSteps = "";
    } else {
      this.bugReproductionSteps = bugReproSteps;
    }
  }

  /**
   * Get the description of the software bug.
   *
   * @return The bug description.
   */
  public String getBugDescription() {
    return this.bugDescription;
  }

  /**
   * Set the description of the software bug.
   *
   * @param bugDescription The new bug description to set.
   */
  public void setBugDescription(String bugDescription) {
    if (bugDescription != null) {
      this.bugDescription = bugDescription;
    }
  }

  /**
   * Get the steps to reproduce the software bug.
   *
   * @return The bug reproduction steps.
   */
  public String getBugReproductionSteps() {
    return this.bugReproductionSteps;
  }

  /**
   * Set the steps to reproduce the software bug.
   *
   * @param bugReproductionSteps The new bug reproduction steps to set.
   */
  public void setBugReproductionSteps(String bugReproductionSteps) {
    if (bugReproductionSteps != null) {
      this.bugReproductionSteps = bugReproductionSteps;
    }
  }

  public String toString() {
    String result = super.toString();
    result += "\n  - Bug Description: " + this.bugDescription + "\n";
    result += "  - Bug Reproduction Steps: " + this.bugReproductionSteps + "\n";
    return result;
  }
}
