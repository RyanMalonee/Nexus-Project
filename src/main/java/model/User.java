package model;

import java.util.UUID;

/**
 * The User class represents a user in the system.
 */
public class User {
  private UUID uuid;
  private String firstName;
  private String lastName;
  private String userName;
  private String email;
  private String password;
  private int permissionLevel;
  private int tasksCompleted;
  private UserType userType;

  /**
   * Constructor used for User when reading from json
   * 
   * @param uuid            uuid of user
   * @param firstName       first name of user as a string
   * @param lastName        last name of user as a string
   * @param userName        username of user as a string
   * @param email           email of user as a string
   * @param password        password of user as a string
   * @param permissionLevel permission level of user as an integer
   * @param tasksCompleted  number of tasks completed by user as an integer
   */
  public User(UUID uuid, String firstName,
      String lastName, String userName, String email, String password,
      int permissionLevel, int tasksCompleted) {
    this.uuid = uuid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.permissionLevel = permissionLevel;
    this.tasksCompleted = tasksCompleted;
    if (permissionLevel == 2) {
      this.userType = UserType.SCRUMMASTER;
    } else {
      this.userType = UserType.BASICUSER;
    }
  }

  /**
   * Constructor for creating a new user via the system
   * 
   * @param firstName       first name of user as a string
   * @param lastName        last name of user as a string
   * @param userName        username of user as a string
   * @param email           email of user as a string
   * @param password        password of user as a string
   * @param permissionLevel permission level of user as an integer
   * @param tasksCompleted  number of tasks completed by user as an integer
   */
  public User(String firstName, String lastName,
      String userName, String email, String password,
      int permissionLevel, int tasksCompleted) {
    this.uuid = UUID.randomUUID();
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.email = email;
    this.password = password;
    if (permissionLevel < 0 || permissionLevel > 3) {
      this.permissionLevel = 0;
    } else {
      this.permissionLevel = permissionLevel;
    }
    this.tasksCompleted = tasksCompleted;
    if (permissionLevel == 2) {
      this.userType = UserType.SCRUMMASTER;
    } else {
      this.userType = UserType.BASICUSER;
    }
  }

  /**
   * Retrieves the UUID of the object.
   *
   * @return the UUID of the object
   */
  public UUID getUUID() {
    return this.uuid;
  }

  /**
   * Gets the first name.
   *
   * @return the first name
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Returns the last name of the object.
   *
   * @return the last name of the object
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * Gets the user name.
   *
   * @return the user name
   */
  public String getUserName() {
    return this.userName;
  }

  /**
   * Get the email of the object.
   *
   * @return the email of the object.
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Get the password.
   *
   * @return the password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Retrieves the permission level of the object.
   *
   * @return the permission level of the object
   */
  public int getPermissionLevel() {
    return this.permissionLevel;
  }

  /**
   * Returns the number of tasks completed by the object.
   *
   * @return the number of tasks completed
   */
  public int getTaskCompletion() {
    return this.tasksCompleted;
  }

  /**
   * Retrieves the user type.
   *
   * @return the user type
   */
  public UserType getUserType() {
    return this.userType;
  }

  /**
   * Sets the first name of an object.
   *
   * @param firstName the first name to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Sets the last name of the object.
   *
   * @param lastName the last name to be set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Sets the user name.
   *
   * @param userName the new user name
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * Sets the email address.
   *
   * @param email the new email address to be set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Sets the password for the object.
   *
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Sets the permission level for the object.
   *
   * @param permissionLevel the permission level to be set
   */
  public void setPermissionLevel(int permissionLevel) {
    if (permissionLevel < 0 || permissionLevel > 3) {
      this.permissionLevel = 0;
    } else {
      this.permissionLevel = permissionLevel;
    }
  }

  /**
   * A description of the entire Java function.
   *
   * @param paramName description of parameter
   * @return description of return value
   */
  public void addTaskCompletion() {
    this.tasksCompleted = this.tasksCompleted++;
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object
   */
  public String toString() {
    return this.firstName + " " + this.lastName;
  }
}