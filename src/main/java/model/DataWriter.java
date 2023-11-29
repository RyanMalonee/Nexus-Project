package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The DataWriter class provides methods for saving data to JSON files.
 */
public class DataWriter extends DataConstants {
    /**
     * Saves the users to a JSON file.
     *
     * @return true if the users are successfully saved, false otherwise
     */
    public static boolean saveUsers() {
        UserList users = UserList.getInstance();
        ArrayList<User> userList = users.getUsers();
        JSONArray usersJSON = new JSONArray();

        /*
         * Creates the JSON object for each user in the userList
         * to be written to the JSON file
         */
        for (int i = 0; i < userList.size(); i++) {
            // Get User Information:
            User user = userList.get(i);
            JSONObject userInfo = new JSONObject();
            userInfo.put(USER_ID, user.getUUID().toString());
            userInfo.put(USERNAME, user.getUserName());
            userInfo.put(FIRST_NAME, user.getFirstName());
            userInfo.put(LAST_NAME, user.getLastName());
            userInfo.put(EMAIL, user.getEmail());
            userInfo.put(PASSWORD, user.getPassword());
            userInfo.put(PERMISSION_LEVEL, user.getPermissionLevel());
            userInfo.put(TASKS_COMPLETED, user.getTaskCompletion());
            usersJSON.add(userInfo);
        }

        try (FileWriter file = new FileWriter(USER_FILE)) {
            file.write(usersJSON.toJSONString());
            file.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Saves the projects to a JSON file.
     *
     * @return true if the projects are successfully saved, false otherwise
     */
    public static boolean saveProjects() {
        ProjectList projects = ProjectList.getInstance();
        ArrayList<Project> projectList = projects.getProjects();
        JSONArray projectsJSON = new JSONArray();

        for (int i = 0; i < projectList.size(); i++) {
            // Get Project object
            Project project = projectList.get(i);
            // Create JSON object to store project data
            JSONObject projectInfo = new JSONObject();
            // Add project information to JSON object
            projectInfo.put(PROJECT_ID, project.getUUID().toString());
            projectInfo.put(PROJECT_NAME, project.getProjectName());
            projectInfo.put(PROJECT_DESCRIPTION, project.getProjectDescription());
            projectInfo.put(SPRINT_TIME, project.getSprintTime());
            projectInfo.put(SPRINT_UNITS, project.getSprintUnits());
            projectInfo.put(SCRUM_MASTER, project.getScrumMaster().getUUID().toString());

            // Handle creating the Array of team members that is stored
            // in each project object in the json file
            JSONArray teamMembers = new JSONArray();
            for (User user : project.getTeamMembers()) {
                teamMembers.add(user.getUUID().toString());
            }
            projectInfo.put(TEAM_MEMBERS, teamMembers);

            /*
             * Handles the columns in the project and the tasks within
             * each of those columns.
             */
            JSONArray projectColumns = new JSONArray();
            for (Column column : project.getColumns()) {
                JSONObject columnInfo = new JSONObject();
                columnInfo.put(COLUMN_ID, column.getName().toLowerCase());
                JSONArray taskList = new JSONArray();
                for (Task task : column.getTasks()) {
                    taskList.add(task.getUUID().toString());
                }
                columnInfo.put(TASK_LIST, taskList);
                projectColumns.add(columnInfo);
            }
            projectInfo.put(PROJECT_COLUMNS, projectColumns);
            projectsJSON.add(projectInfo);

            JSONArray commentsJSON = new JSONArray();
            for (Comment comment : project.getComments()) {
                JSONObject commentInfo = saveCommentRecursion(comment);
                commentsJSON.add(commentInfo);
            }
            projectInfo.put(COMMENTS, commentsJSON);
            saveTasks();
        }
        try (FileWriter file = new FileWriter(PROJECT_FILE)) {
            file.write(projectsJSON.toJSONString());
            file.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Saves the tasks to a JSON file.
     *
     * @return Returns true if the tasks are successfully saved, false otherwise.
     */
    public static boolean saveTasks() {
        ProjectList projects = ProjectList.getInstance();
        ArrayList<Project> projectList = projects.getProjects();
        JSONArray tasksJSON = new JSONArray();
        /*
         * Iterates through each project, each column within each project, and
         * each task within each column within each project to accurately store
         * all of the tasks in the JSON file.
         */
        for (Project project : projectList) {
            for (Column column : project.getColumns()) {
                for (Task task : column.getTasks()) {
                    JSONObject taskInfo = new JSONObject();
                    taskInfo.put(TASK_ID, task.getUUID().toString());
                    taskInfo.put(TASK_NAME, task.getTaskName());
                    taskInfo.put(TASK_DESCRIPTION, task.getTaskDescription());
                    taskInfo.put(TASK_PRIORITY, task.getTaskPriority());
                    if (task.getAssignee() != null) {
                        taskInfo.put(TASK_ASSIGNEE, task.getAssignee().getUUID().toString());
                    }

                    /*
                     * Handles the comments in the task and utilizes a
                     * recursive method to save each comment and its replies
                     */
                    JSONArray commentsJSON = new JSONArray();
                    for (Comment comment : task.getComments()) {
                        JSONObject commentInfo = saveCommentRecursion(comment);
                        commentsJSON.add(commentInfo);
                    }
                    taskInfo.put(COMMENTS, commentsJSON);
                    tasksJSON.add(taskInfo);
                }
            }
        }
        try (FileWriter file = new FileWriter(TASK_FILE)) {
            file.write(tasksJSON.toJSONString());
            file.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Saves the given comment and its replies recursively as a JSON object.
     *
     * @param comment the comment to be saved
     * @return a JSON object containing the saved comment and its replies
     */
    public static JSONObject saveCommentRecursion(Comment comment) {
        JSONObject commentInfo = new JSONObject();
        commentInfo.put(COMMENTER, comment.getCommenter().getUUID().toString());
        commentInfo.put(COMMENT_DESCRIPTION, comment.getCommentDescription());
        commentInfo.put(COMMENT_DATETIME, comment.getDateTime().toString());
        commentInfo.put(COMMENT_PRIORITY, comment.getPriority());

        /*
         * Handle the replies to the comment recursively until there are
         * none left to save
         */
        JSONArray repliesJSON = new JSONArray();
        if (comment.getReplies() != null && !comment.getReplies().isEmpty()) {
            for (Comment reply : comment.getReplies()) {
                JSONObject replyInfo = saveCommentRecursion(reply);
                repliesJSON.add(replyInfo);
            }
        }
        commentInfo.put(COMMENT_COMMENTS, repliesJSON);
        return commentInfo;
    }
}