package model;

import java.util.ArrayList;

public class ProjectList {
  private ArrayList<Project> projects;
  private static ProjectList projectList;

  /**
   * Constructs a ProjectList from the DataReader
   */
  private ProjectList() {
    projects = DataReader.getProjects();
  }

  /**
   * Creates an instance of projectList if it doesn't already exist
   * 
   * @return the instance of projectList if it didn't already exist
   */
  public static ProjectList getInstance() {
    if (projectList == null) {
      projectList = new ProjectList();
    }
    return projectList;
  }

  /**
   * Retrieves all the projects
   * 
   * @return ArrayList<Project> projects
   */
  public ArrayList<Project> getProjects() {
    return projects;
  }

  /**
   * Adds a project to projectList
   * 
   * @param projectName name of the project being added
   */
  public void addProject(String projectName) {
    Project project = new Project(projectName, null, 0, null, null);
    projects.add(project);
  }

  /**
   * Retrieves projects based on user
   * 
   * @param user user to find project based on
   * @return projects applicable to a user as an ArrayList
   */
  public ArrayList<Project> getProjectsByUser(User user) {
    ArrayList<Project> projectsByUser = new ArrayList<Project>();
    for (Project project : projects) {
      for (User userInProject : project.getTeamMembers()) {
        if (user == userInProject) {
          projectsByUser.add(project);
        }
      }
    }
    return projectsByUser;
  }

  /**
   * Saves all projects using DataWriter
   */
  public void saveProjects() {
    DataWriter.saveProjects();
  }

}