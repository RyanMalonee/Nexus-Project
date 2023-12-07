package model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A class that will create a task object. A task object will have a name,
 * description, priority, assignee, comments, and a change log.
 */
public class Task {
  private UUID uuid;
  private String taskName;
  private String taskDescription;
  private int taskPriority;
  private User assignee;
  private ArrayList<Comment> comments;
  private ChangeLog changeLog;

  /**
   * Constructor for Task object (not to be used by the DataReader class).
   * The UUID will be randomly generated for saving the object.
   * 
   * @param taskName        the name of the task
   * @param taskDescription the description of the task
   * @param taskPriority    the priority of the task
   * @param assignee        the assignee of the task
   */
  public Task(String taskName, String taskDescription, int taskPriority, User assignee) {
    this.uuid = UUID.randomUUID();
    this.taskName = taskName;
    this.taskDescription = taskDescription;
    this.taskPriority = taskPriority;
    this.assignee = assignee;
    this.comments = new ArrayList<>();

  }

  /**
   * Constructor for Task object (not to be used by the DataReader class).
   * The UUID will be randomly generated for saving the object.
   * 
   * @param taskName        the name of the task
   * @param taskDescription the description of the task
   * @param taskPriority    the priority of the task
   */
  public Task(String taskName, String taskDescription, int taskPriority) {
    this.uuid = UUID.randomUUID();
    this.taskName = taskName;
    this.taskDescription = taskDescription;
    this.taskPriority = taskPriority;
    this.assignee = null;
    this.comments = new ArrayList<>();
    this.changeLog = new ChangeLog();

  }

  /**
   * `Constructor for Task object (not to be used by the DataReader class).
   * The UUID will be randomly generated for saving the object.
   *
   * @param taskName        the name of the task
   * @param taskDescription the description of the task
   * @param column          the column the task belongs to
   * @param taskPriority    the priority of the task
   */
  public Task(String taskName, String taskDescription, Column column, int taskPriority) {
    this.uuid = UUID.randomUUID();
    this.taskName = taskName;
    this.taskDescription = taskDescription;
    this.taskPriority = taskPriority;
    this.assignee = null;
    this.comments = new ArrayList<>();
    this.changeLog = new ChangeLog();

  }

  /**
   * Constructor for Task object (To be used by the DataReader class).
   * 
   * @param uuid            the UUID of the task
   * @param taskName        the name of the task
   * @param taskDescription the description of the task
   * @param taskPriority    the priority of the task
   * @param assignee        the assignee of the task
   * @param comments        the comments of the task
   */
  public Task(UUID uuid, String taskName, String taskDescription, int taskPriority, User assignee,
      ArrayList<Comment> comments) {
    this.uuid = uuid;
    this.taskName = taskName;
    this.taskDescription = taskDescription;
    this.taskPriority = taskPriority;
    this.assignee = assignee;
    this.comments = comments;
    this.changeLog = new ChangeLog();

  }

  /**
   * Gets the UUID of the task.
   * 
   * @return the UUID of the task
   */
  public UUID getUUID() {
    return this.uuid;
  }

  /**
   * Gets the assignee of the task.
   * 
   * @return the User assignee of the task
   * 
   */
  public User getAssignee() {
    return this.assignee;
  }

  /**
   * Sets the assignee of the task.
   * 
   * @param assignee the User assignee of the task
   */
  public void setAssignee(User assignee) {
    this.assignee = assignee;
  }

  /**
   * Gets the assignee of the task as a string (first and last name).
   * 
   * @return the assignee of the task as a string.
   */
  public String getAssigneeString() {
    return this.assignee.getFirstName() + " " + this.assignee.getLastName();
  }

  /**
   * Adds a comment to the comments ArrayList in the task.
   *
   * @param comment the comment to add to the task
   */
  public void addComment(Comment comment) {
    comments.add(comment);
  }

  /**
   * Adds a comment to a comment in the comments ArrayList of the task.
   * 
   * @param parentComment the Comment that the comment will be added to
   * @param comment       the comment to add to the parent comment
   */
  public void addCommentReply(Comment parentComment, Comment comment) {
    for (Comment commentObject : comments) {
      if (commentObject == parentComment) {
        commentObject.addReply(comment);
        break;
      }
    }
  }

  /**
   * Deletes a comment from the comments ArrayList in the task.
   * 
   * @param comment the comment to delete
   */
  public void deleteComment(Comment comment) {
    comments.remove(comment);
  }

  /**
   * Gets the ArrayList of Comment objects that the task has.
   * 
   * @return an ArrayList of Comment objects
   */
  public ArrayList<Comment> getComments() {
    return this.comments;
  }

  /**
   * Returns a string representation of the task.
   * 
   * @return a string representation of the task (name and description)
   */
  public String toString() {
    String result = "";
    result += "Task Name: " + this.taskName + "\n";
    result += "  - Task Description: " + this.taskDescription + "\n";
    if (this.assignee != null) {
      result += "  - assignee: " + this.assignee.getFirstName() + " " + this.assignee.getLastName() +
          "\n";
    }
    if (!this.comments.isEmpty()) {
      result += "  - Comments: \n\n";
      for (Comment comment : this.comments) {
        result += comment.toString();
      }
    }
    result += "\n *********  \n";
    return result;
  }

  /**
   * Gets the change log of the task.
   * 
   * @return the change log of the task
   */
  public String getChangeLog() {
    return this.changeLog.toString();
  }

  /**
   * Gets the name of the task.
   * 
   * @return the name of the task
   */
  public String getTaskName() {
    return this.taskName;
  }

  /**
   * Sets the name of the task.
   * 
   * @param taskName the name of the task
   */
  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  /**
   * Gets the description of the task.
   * 
   * @return the description of the task
   */
  public String getTaskDescription() {
    return this.taskDescription;
  }

  /**
   * Sets the description of the task.
   * 
   * @param taskDescription the description of the task
   */
  public void setTaskDescription(String taskDescription) {
    this.taskDescription = taskDescription;
  }

  /**
   * Gets the priority of the task.
   * 
   * @return the priority of the task
   */
  public int getTaskPriority() {
    return this.taskPriority;
  }

  /**
   * Sets the priority of the task.
   * 
   * @param taskPriority the priority of the task
   */
  public void setTaskPriority(int taskPriority) {
    this.taskPriority = taskPriority;
  }

}