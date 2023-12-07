package model;

import java.util.ArrayList;

/**
 * The ProjectManagerFacade class provides methods for managing users, projects,
 * and tasks.
 */
public class ProjectManagerFacade {

  private static ProjectManagerFacade projectManagerFacade;
  private User currentUser;
  private Project currentProject;

  public ProjectManagerFacade() {
    currentUser = null;
  }

  /**
   * Constructs a new singleton ProjectManagerFacade object.
   */
  public static ProjectManagerFacade getInstance() {
    if (projectManagerFacade == null) {
      projectManagerFacade = new ProjectManagerFacade();
    }
    return projectManagerFacade;
  }

  /**
   * Login a user with the given username and password.
   *
   * @param userName the username of the user
   * @param password the password of the user
   * @return true if the login is successful, false otherwise
   */
  public boolean login(String userName, String password) {
    UserList users = UserList.getInstance();
    ArrayList<User> userList = users.getUsers();

    for (int i = 0; i < userList.size(); i++) {
      if (userList.get(i).getUserName().equals(userName) && userList.get(i).getPassword().equals(password)) {
        currentUser = userList.get(i);
        return true;
      }
    }
    return false;
  }

  /**
   * Creates a new user account with the given information.
   *
   * @param firstName       the first name of the user
   * @param lastName        the last name of the user
   * @param userName        the desired username for the user
   * @param email           the email address of the user
   * @param password        the password for the user account
   * @param permissionLevel the permission level of the user
   * @param tasksCompleted  the number of tasks completed by the user
   * @return true if the account was successfully created, false otherwise
   */
  public boolean signup(String firstName, String lastName, String userName, String email, String password,
      int permissionLevel, int tasksCompleted) {
    UserList users = UserList.getInstance();
    ArrayList<User> userList = users.getUsers();

    if (login(userName, password)) {
      return false;
    }
    userList.add(new User(firstName, lastName, userName, email, password, permissionLevel, tasksCompleted));
    return true;
  }

  /**
   * Retrieves all projects from the project list.
   *
   * @return an ArrayList of Project objects representing all projects
   */
  public ArrayList<Project> getAllProjects() {
    ArrayList<Project> projects = ProjectList.getInstance().getProjects();
    return projects;
  }

  /**
   * Adds a user to a project.
   *
   * @param project the project to add the user to
   * @param user    the user to be added
   */
  public void addUserToProject(Project project, User user) {
    project.addUser(user);
  }

  /**
   * Removes a user from a project.
   *
   * @param project the project to remove the user from
   * @param user    the user to be removed from the project
   */
  public void removeUserFromProject(Project project, User user) {
    project.removeUser(user);
  }

  public User getUserFromProject(String userName, Project project) {
    UserList users = UserList.getInstance();
    ArrayList<User> userList = users.getUsers();

    for (int i = 0; i < userList.size(); i++) {
      if (userList.get(i).getUserName().equals(userName) && project.getUsers().contains(userList.get(i))) {
        return userList.get(i);
      }
    }
    return null;
  }

  /**
   * Creates a new project with the given details.
   *
   * @param projectName        the name of the project
   * @param projectDescription the description of the project
   * @param sprintTime         the duration of each sprint
   * @param sprintUnits        the units of the sprint duration
   * @param scrumMaster        the scrum master for the project
   * @param teamMembers        the list of team members for the project
   * @return the newly created project
   */
  public Project createNewProject(String projectName, String projectDescription, int sprintTime, String sprintUnits,
      User scrumMaster, ArrayList<User> teamMembers) {
    Project project = new Project(projectName, projectDescription, sprintTime, sprintUnits, scrumMaster, teamMembers);
    ProjectList.getInstance().getProjects().add(project);
    return project;
  }

  /**
   * Adds a task to a project at a specific position.
   *
   * @param project  the project to add the task to
   * @param position the position in the project's column to add the task to
   * @param task     the task to add
   */
  public void addTask(Project project, int position, Task task) {
    project.getColumns().get(position - 1).addNewTask(task);
  }

  /**
   * Moves a task between columns in a project.
   *
   * @param project  the project containing the task
   * @param position the new position of the task in the project
   * @param task     the task to be moved
   */
  public void moveTaskBetweenColumns(Project project, String position, Task task) {
    project.moveTask(position, task);
  }

  /**
   * Assigns a task to a user for a given project at a specific position.
   *
   * @param project  the project to assign the task to
   * @param position the position in the project to assign the task to
   * @param task     the task to be assigned
   * @param user     the user to assign the task to
   */
  public void assignTaskToUser(Project project, Task task, User user) {
    task.setAssignee(user);
  }

  /**
   * Retrieves the change log for a given task.
   *
   * @param task the task object
   * @return a string representation of the change log
   */
  public String getChangeLog(Task task) {
    return task.getChangeLog().toString();
  }

  public User getCurrentUser() {
    return currentUser;
  }

  public void logout() {
    currentUser = null;
  }

  public void setProject(Project project) {
    currentProject = project;
  }

  public Project getProject() {
    return currentProject;
  }

  public void addColumn(String name) {
    currentProject.addColumn(name);
  }
  
}
