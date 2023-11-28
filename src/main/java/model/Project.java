package model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A class that will create a project object. A project object will have a name,
 * description, sprint time, sprint units, scrum master, team members, columns,
 * and comments.
 */
public class Project {
  private UUID uuid;
  private String projectName;
  private String projectDescription;
  private int sprintTime;
  private String sprintUnits;
  private User scrumMaster;
  private ArrayList<User> teamMembers;
  private ArrayList<Column> columns;
  private ArrayList<Comment> comments;

  /**
   * Creates a Project object and generates a random UUID from it
   * (not to be used with the DataReader class).
   * 
   * @param projectName        the name of the project
   * @param projectDescription the description of the project
   * @param sprintTime         the sprint time of the project (int value)
   * @param sprintUnits        the sprint units of the project (String value)
   * @param scrumMaster        the User who is the scrum master of the project
   * @param teamMembers        an ArrayList of User objects representing the team
   *                           members
   */
  public Project(String projectName, String projectDescription, int sprintTime,
      String sprintUnits, User scrumMaster, ArrayList<User> teamMembers) {
    this.uuid = UUID.randomUUID();
    this.projectName = projectName;
    this.projectDescription = projectDescription;
    if (sprintTime <= 0) {
      this.sprintTime = 1;
    } else {
      this.sprintTime = sprintTime;
    }
    if (!sprintUnits.equalsIgnoreCase("Weeks") && !sprintUnits.equalsIgnoreCase("Days")
        && !sprintUnits.equalsIgnoreCase("Months") && !sprintUnits.equalsIgnoreCase("Years")) {
      this.sprintUnits = "Weeks";
    } else {
      this.sprintUnits = sprintUnits;
    }
    this.scrumMaster = scrumMaster;
    this.teamMembers = teamMembers;
    this.comments = new ArrayList<Comment>();
    buildColumns();
    if (!teamMembers.contains(scrumMaster)) {
      teamMembers.add(scrumMaster);
    }
  }

  /**
   * Creates a Project object and generates a random UUID from it (not to be
   * used with the DataReader class). This constructor is used when creating
   * a project without a list of team members yet.
   *
   * @param projectName        the name of the project
   * @param projectDescription the description of the project
   * @param sprintTime         the sprint time of the project (int value)
   * @param sprintUnits        the sprint units of the project (String value)
   * @param scrumMaster        the User who is the scrum master of the project
   */
  public Project(String projectName, String projectDescription, int sprintTime,
      String sprintUnits, User scrumMaster) {
    this.uuid = UUID.randomUUID();
    this.projectName = projectName;
    this.projectDescription = projectDescription;
    if (sprintTime <= 0) {
      this.sprintTime = 1;
    } else {
      this.sprintTime = sprintTime;
    }
    if (!sprintUnits.equalsIgnoreCase("Weeks") && !sprintUnits.equalsIgnoreCase("Days")
        && !sprintUnits.equalsIgnoreCase("Months") && !sprintUnits.equalsIgnoreCase("Years")) {
      this.sprintUnits = "Weeks";
    } else {
      this.sprintUnits = sprintUnits;
    }
    this.scrumMaster = scrumMaster;
    this.teamMembers = new ArrayList<User>();
    this.comments = new ArrayList<Comment>();
    teamMembers.add(scrumMaster);
    buildColumns();
  }

  /**
   * Creates a Project object using the information that is read
   * from the project.json file. All of the information ready via
   * the DataReader class from the JSON file will be assigned to
   * the project object.
   *
   * @param uuid               the UUID of the project
   * @param projectName        the name of the project
   * @param projectDescription the description of the project
   * @param sprintTime         the sprint time of the project (int value)
   * @param sprintUnits        the sprint units of the project (String value)
   * @param scrumMaster        The User who is the scrum master of the project
   * @param teamMembers        an ArrayList of User objects representing the team
   *                           members
   * @param comments           an ArrayList of Comment objects representing the
   *                           comments
   */
  public Project(UUID uuid, String projectName, String projectDescription,
      int sprintTime, String sprintUnits, User scrumMaster, ArrayList<User> teamMembers, ArrayList<Comment> comments) {
    this.uuid = uuid;
    this.projectName = projectName;
    this.projectDescription = projectDescription;
    this.sprintTime = sprintTime;
    if (sprintTime <= 0) {
      this.sprintTime = 1;
    } else {
      this.sprintTime = sprintTime;
    }
    if (!sprintUnits.equalsIgnoreCase("Weeks") && !sprintUnits.equalsIgnoreCase("Days")
        && !sprintUnits.equalsIgnoreCase("Months") && !sprintUnits.equalsIgnoreCase("Years")) {
      this.sprintUnits = "Weeks";
    } else {
      this.sprintUnits = sprintUnits;
    }
    this.scrumMaster = scrumMaster;
    this.teamMembers = teamMembers;
    if (!teamMembers.contains(scrumMaster)) {
      teamMembers.add(scrumMaster);
    }
    this.comments = comments;
    buildColumns();
  }

  /**
   * Helper method used to add the four main types of columns to the
   * project. These columns are: New Task, In Progress, Completed, and
   * Archived.
   */
  private void buildColumns() {
    columns = new ArrayList<Column>();
    columns.add(new Column(ColumnType.NEWTASK));
    columns.add(new Column(ColumnType.INPROGRESS));
    columns.add(new Column(ColumnType.COMPLETED));
    columns.add(new Column(ColumnType.ARCHIVE));
  }

  /**
   * Gets the project leaderboard. The leaderboard is generated by
   * calling the Leaderboard class.
   *
   * @return a String representation of the project leaderboard
   *         (explaining who has the most tasks completed).
   */
  public String getLeaderboard() {
    Leaderboard leaderboard = new Leaderboard(teamMembers);
    return leaderboard.toString();
  }

  /**
   * Adds a user to the ArrayList of team members on the project.
   * If the user is already in the ArrayList, it will return false
   * and won't add the user.
   *
   * @param user the User to add to the team members ArrayList
   * @return a boolean representing whether the user was added or not
   */
  public boolean addUser(User user) {
    if (teamMembers.contains(user)) {
      return false;
    } else {
      teamMembers.add(user);
      return true;
    }
  }

  /**
   * Removes a user from the team members ArrayList. If the user
   * is not in the ArrayList, it will return false, otherwise when
   * the user is removed it will return true.
   *
   * @param user the User to remove from the team members ArrayList
   * @return a boolean representing whether the user was removed or not
   */
  public boolean removeUser(User user) {
    if (teamMembers.contains(user)) {
      teamMembers.remove(user);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Gets the UUID of the project.
   *
   * @return the UUID of the project
   */
  public UUID getUUID() {
    return this.uuid;
  }

  /**
   * Gets the name of the project.
   *
   * @return a String representing the name of the project
   */
  public String getProjectName() {
    return this.projectName;
  }

  /**
   * Sets the name of the project.
   *
   * @param name the name of the project
   */
  public void setProjectName(String name) {
    this.projectName = name;
  }

  /**
   * Gets the description of the project.
   * 
   * @return a String representing the description of the project
   */
  public String getProjectDescription() {
    return this.projectDescription;
  }

  /**
   * Sets the description of the project.
   *
   * @param description the description of the project
   */
  public void setProjectDescription(String description) {
    this.projectDescription = description;
  }

  /**
   * Gets the sprint time of the project.
   *
   * @return the sprint time of the project (int value)
   */
  public int getSprintTime() {
    return this.sprintTime;
  }

  /**
   * Sets the sprint time of the project.
   *
   * @param time an int representing the number of units of time the sprint will
   *             take
   */
  public void setSprintTime(int time) {
    if (time <= 0) {
      this.sprintTime = 1;
    } else {
      this.sprintTime = time;
    }
  }

  /**
   * Gets the sprint units of the project.
   *
   * @return a String representing the sprint units of the project
   */
  public String getSprintUnits() {
    return this.sprintUnits;
  }

  /**
   * Sets the sprint units of the project.
   *
   * @param units a String representing the sprint units of the project
   */
  public void setSprintUnits(String units) {
    if (!units.equalsIgnoreCase("Weeks") && !units.equalsIgnoreCase("Days")
        && !units.equalsIgnoreCase("Months") && !units.equalsIgnoreCase("Years")) {
      this.sprintUnits = "Weeks";
    } else {
      this.sprintUnits = units;
    }
  }

  /**
   * Gets the User who is the scrum master of the project.
   *
   * @return the User who is the scrum master of the project
   */
  public User getScrumMaster() {
    return this.scrumMaster;
  }

  /**
   * Sets the User who is the scrum master of the project.
   *
   * @param user the User who is the scrum master of the project
   */
  public void setScrumMaster(User user) {
    this.scrumMaster = user;
  }

  /**
   * Gets the ArrayList of User objects representing the team members.
   *
   * @return an ArrayList of User objects who are the team members
   */
  public ArrayList<User> getTeamMembers() {
    return this.teamMembers;
  }

  /**
   * Sets the ArrayList of User objects representing the team members.
   *
   * @param teamMembers an ArrayList of User objects who are the team members
   */
  public void setTeamMembers(ArrayList<User> teamMembers) {
    this.teamMembers = teamMembers;
  }

  /**
   * Gets the ArrayList of Column objects that the project has.
   *
   * @return an ArrayList of Column objects
   */
  public ArrayList<Column> getColumns() {
    return this.columns;
  }

  /**
   * Sets the ArrayList of Column objects that the project has.
   *
   * @param columns an ArrayList of Column objects
   */
  public void setColumns(ArrayList<Column> columns) {
    this.columns = columns;
  }

  /**
   * Moves a task from one column to another.
   * If the task is moved to completed the assignee of the task
   * will be updated to reflect another task completed.
   * 
   * @param newPosition the new position (column) of the task
   * @param task        the task to move
   * @return a boolean representing whether the task was successfully moved or not
   */
  public boolean moveTask(String newPosition, Task task) {
    boolean result = false;
    for (Column column : columns) {
      if (column.getName().equalsIgnoreCase(newPosition)) {
        column.addNewTask(task);
        if (newPosition.equalsIgnoreCase("Completed")) {
          task.getAssignee().addTaskCompletion();
        }
        for (Column columnObject : columns) {
          if (columnObject.getTasks().contains(task)) {
            columnObject.getTasks().remove(task);
            break;
          }
        }
        result = true;
        break;
      } else {
        result = false;
      }
    }
    return result;
  }

  /**
   * Moves a task from a column to the Archive column.
   * 
   * @param task the task to move to the archive column
   * @return a boolean representing whether the task was successfully moved or not
   */
  public boolean archiveTask(Task task) {
    return moveTask("Archive", task);
  }

  /**
   * Adds a column to the project.
   * 
   * @param name the name of the new column
   */
  public void addColumn(String name) {
    columns.add(new Column(name));
  }

  /**
   * Gets the ArrayList of Comment objects that the project has.
   *
   * @return an ArrayList of Comment objects
   */
  public ArrayList<Comment> getComments() {
    return this.comments;
  }

  /**
   * Adds a comment to the comments ArrayList in the project.
   * 
   * @param comment the comment to add to the project
   */
  public void addComment(Comment comment) {
    comments.add(comment);
  }

  /**
   * Adds a comment to a comment in the comments ArrayList in the project.
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
   * Returns a String representation of the project object.
   * 
   * @return a String representation of the project
   */
  public String toString() {
    String result = "";
    result += "Project Name: " + this.projectName + "\n\n";
    result += "Project Description: " + this.projectDescription + "\n\n";
    result += "Sprint Length: " + this.sprintTime + " " + this.sprintUnits + "\n\n";
    result += "Scrum Master: " + this.scrumMaster.toString() + "\n\n";
    result += "Team Members: \n";
    for (User user : this.teamMembers) {
      result += "  - " + user.toString() + "\n";
    }
    result += "\n";

    result += "Columns: \n***********************\n";
    for (Column column : this.columns) {
      result += column.toString();
    }

    result += "Comments: \n\n";
    for (Comment comment : this.comments) {
      result += comment.toString();
    }
    return result;
  }

}
