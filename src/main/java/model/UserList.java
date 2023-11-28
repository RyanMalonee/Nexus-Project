package model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The UserList class manages a list of users in the system.
 */
public class UserList {
  private ArrayList<User> users;
  private static UserList userList;

  /**
   * Constructs an ArrayList<User> from the DataReader
   */
  private UserList() {
    users = DataReader.getUsers();
  }

  /**
   * Retrieves an instance of a userList if it doesn't already exists
   * 
   * @return returns userList
   */
  public static UserList getInstance() {
    if (userList == null) {
      userList = new UserList();
    }
    return userList;
  }

  /**
   * Adds a user to the userList
   * 
   * @param uuid            uuid of user being added
   * @param firstName       first name of user being added as a string
   * @param lastName        last name of user being added as a string
   * @param userName        username of user being added as a string
   * @param password        password of user being added as a string
   * @param permissionLevel permissionlevel of user being added as an integer
   */
  public void addUser(UUID uuid, String firstName, String lastName, String userName, String email, String password,
      int permissionLevel) {
    User user = new User(uuid, firstName, lastName, userName, email, password, permissionLevel, 0);
    users.add(user);
  }

  /**
   * Returns a user based on give username
   * 
   * @param userName username of user being returned
   * @return user that has the username specified
   */
  public User getUser(String userName) {
    for (User user : users) {
      if (user.getUserName().equalsIgnoreCase(userName)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Returns all users
   * 
   * @return ArrayList of all users
   */
  public ArrayList<User> getUsers() {
    return users;
  }

  /**
   * Authenticates user
   * 
   * @param userName username of user being authenticated
   * @param password password of user being authenticated
   * @return User that has been authenticated, null if not authenticated
   */
  public User authenticateUser(String userName, String password) {
    for (User user : users) {
      if (user.getUserName().equalsIgnoreCase(userName) && user.getPassword().equals(password)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Saves all users using DataWriter
   */
  public void saveUsers() {
    DataWriter.saveUsers();
  }

  /**
   * Gets user based on UUID
   * 
   * @param id uuid of user being returned
   * @return User with specified UUID
   */
  public User getUserByUUID(UUID id) {
    for (User user : users) {
      if (user.getUUID().equals(id)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Gets the user with the given username
   * 
   * @param username the username of the user trying to be found
   * @return the user with the given username
   */
  public User getUserByUsername(String username) {
    for (User user : users) {
      if (user.getUserName().equalsIgnoreCase(username)) {
        return user;
      }
    }
    return null;
  }
}
